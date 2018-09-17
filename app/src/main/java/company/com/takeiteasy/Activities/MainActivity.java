package company.com.takeiteasy.Activities;


import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.GeoObject;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import company.com.takeiteasy.Activities.CafeActivity;
import company.com.takeiteasy.Fragments.CommentsList;
import company.com.takeiteasy.Managers.ServerManager;
import company.com.takeiteasy.Managers.VolleyCallback;
import company.com.takeiteasy.R;
import company.com.takeiteasy.Serializables.Cafe;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private MapView mapView;

    protected boolean inSystem=false;

    private static final int PERMISSIONS_CODE = 109;

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
                inSystem = sPref.getBoolean("INSYSTEM", false);
                Log.d("log1", "Есть сохранение" + sPref.getBoolean("INSYSTEM", false));
        }
        catch (Exception e)
        {
            Log.d("log1", "нет сохранения");
        }
        if(!inSystem) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            inSystem=true;
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean("INSYSTEM",inSystem);
            Log.d("log1", "сохраняем");
            ed.commit();

        }

        MapKitFactory.setApiKey("86c7a54b-e523-4eb5-ba85-ffa74bdf875e");
        MapKitFactory.initialize(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        mapView = findViewById(R.id.map_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        checkGPSLocationPermission();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                // Code go to layout from menu item

                return true;
            }
        });
        LinearLayout mapSearchLayout = findViewById(R.id.map_search_layout);
        mapSearchLayout.bringToFront();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();

        CameraPosition userPositionOnMap = mapView.getMap().getUserLocationLayer().cameraPosition();
        if (userPositionOnMap != null) {
            mapView.getMap().move(userPositionOnMap,
                    new Animation(Animation.Type.SMOOTH, 0),
                    null);

            ServerManager serverManager = new ServerManager(this);
            serverManager.getCafeByUserCoord(new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {

                }

                @Override
                public void onSuccess(JSONArray result) {
                    try {
                        Log.i("Cafes", result.get(0).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final Gson gson = new Gson();
                    Type cafeListType = new TypeToken<List<Cafe>>() {
                    }.getType();

                    List<Cafe> cafeList = gson.fromJson(result.toString(), cafeListType);

                    showCafesOnMap(cafeList);
                }
            }, userPositionOnMap.getTarget());
        } else {
            Log.i("Map", "User position = ??");
        }

        // ----start---- let it be there until yandex maps api key won`t be fixed
        ServerManager serverManager = new ServerManager(this);
        serverManager.getAllCafes(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result){

            }

            @Override
            public void onSuccess(JSONArray result) {
                try {
                    Log.i("Cafes", result.get(0).toString());
                    final Gson gson = new Gson();
                    Type cafeListType = new TypeToken<List<Cafe>>() {
                    }.getType();

                    List<Cafe> cafeList = gson.fromJson(result.toString(), cafeListType);

                    showCafesOnMap(cafeList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // ----end----
    }


    private void showCafesOnMap(List<Cafe> cafeList) {
        MapObjectCollection cafesMapObjects = mapView.getMap().getMapObjects().addCollection();


        // ----start---- for test only, till yandex api key is bad! Adds a very strange behavior to button near search
        final Intent i = new Intent(MainActivity.this, CafeActivity.class);
        i.putExtra("cafe", cafeList.get(0));

        Button btnNavbar = findViewById(R.id.btn_navbar);
        btnNavbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(i, 0);
            }
        });
        // ----end----


        for (Cafe cafe : cafeList) {
            final Intent intent = new Intent(MainActivity.this, CafeActivity.class);
            intent.putExtra("cafe", cafe);

            PlacemarkMapObject cafePlacemark = cafesMapObjects.addPlacemark(new Point(cafe.cafeCoordinates.lat, cafe.cafeCoordinates.lon));
            cafePlacemark.addTapListener(new MapObjectTapListener() {
                @Override
                public boolean onMapObjectTap(MapObject mapObject, Point point) {
                    startActivityForResult(intent, 0);
                    return false;
                }
            });
        }
    }

    private void checkGPSLocationPermission() {
        int permACL = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permAFL = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permACL != PackageManager.PERMISSION_GRANTED ||
                permAFL != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // all good
                } else {
                    // Show notification like "Включите отслеживание местоположения в настройках плез"
                }
            }
        }
    }
    public void logOut(View view){
        inSystem=false;

    }

    @Override
    protected void onResume() {
        sPref = getPreferences(MODE_PRIVATE);
        if(sPref.contains("INSYSTEM")) {
            inSystem = sPref.getBoolean("INSYSTEM", false);
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("INSYSTEM",inSystem);
        ed.commit();
        super.onDestroy();
    }
}
