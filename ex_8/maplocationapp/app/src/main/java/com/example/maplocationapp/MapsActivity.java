package com.example.maplocationapp;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
private GoogleMap mMap;

FusedLocationProviderClient fusedLocationProviderClient;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_container);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map_container, mapFragment)
                    .commit();
        }
        mapFragment.getMapAsync(this);
fusedLocationProviderClient =
LocationServices.getFusedLocationProviderClient(this);
}
@Override
public void onMapReady(GoogleMap googleMap) {
mMap = googleMap;
if (ActivityCompat.checkSelfPermission(this,
Manifest.permission.ACCESS_FINE_LOCATION) !=
PackageManager.PERMISSION_GRANTED) {
ActivityCompat.requestPermissions(this,
new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
return;
}
mMap.setMyLocationEnabled(true);
fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location
-> {
if (location != null) {
LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
mMap.addMarker(new MarkerOptions().position(current).title("You are here"));
mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 18));
} else {
android.widget.Toast.makeText(MapsActivity.this, "Current location not found. Showing default Sydney.", android.widget.Toast.LENGTH_LONG).show();
LatLng defaultLoc = new LatLng(-34.0, 151.0); // Sydney
mMap.addMarker(new MarkerOptions().position(defaultLoc).title("Default Marker in Sydney"));
mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 10));
}
});
}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) onMapReady(mMap);
            }
        }
    }
}
