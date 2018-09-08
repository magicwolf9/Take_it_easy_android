package company.com.takeiteasy.Managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yandex.mapkit.geometry.Point;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServerManager {

    private String siteUrl = "http://46.21.249.26:8000";
    private String cafeUrl = "/cafes/get_cafe_by_id";
    private RequestQueue requestQueue;

    public ServerManager(Context context){
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void getCafeById(final VolleyCallback callback){
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, siteUrl + cafeUrl + "?cafe_id=1", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.toString());
            }
        });

        this.requestQueue.add(stringRequest);
    }

    public void getCafeByUserCoord(final VolleyCallback callback, Point userLocation){
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, siteUrl + "cafe/get_cafe_by_coord?lat=" + userLocation.getLatitude() + "&lon=" + userLocation.getLongitude() + "&r=300", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.toString());
            }
        });

        this.requestQueue.add(stringRequest);
    }

    public void getAllCafes(final VolleyCallback callback){
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, siteUrl + "/cafes/", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.toString());
            }
        });

        this.requestQueue.add(stringRequest);
    }
}
