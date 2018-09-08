package company.com.takeiteasy.Managers;

import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;

public class GPSManager {
    private Location currentLocation;

    GPSManager(){
        LocationManager locationManager = new LocationManager() {
            @Override
            public void subscribeForLocationUpdates(double v, long l, double v1, boolean b, LocationListener locationListener) {

            }

            @Override
            public void requestSingleUpdate(LocationListener locationListener) {

            }

            @Override
            public void unsubscribe(LocationListener locationListener) {

            }

            @Override
            public void suspend() {

            }

            @Override
            public void resume() {

            }
        };
    }


}
