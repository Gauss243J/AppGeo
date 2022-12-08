package com.gauss243j.appgeo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gauss243j.appgeo.api.APIService;
import com.gauss243j.appgeo.api.APIUrl;
import com.gauss243j.appgeo.helper.SharedPrefManager;
import com.gauss243j.appgeo.models.Citoyen;
import com.gauss243j.appgeo.models.CoordoGeo;
import com.gauss243j.appgeo.models.MyResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference;
    private EditText editlong;
    private EditText editlati;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private float longitud;
    private float latitud;
    private ProgressDialog progressDialog1;
    ProgressBar progressbar;
    Button button1;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        editlong = (EditText) findViewById(R.id.editTextTextPersonName);
        editlati = (EditText) findViewById(R.id.editTextTextPersonName2);
        progressbar=(ProgressBar)findViewById(R.id.progressId);
        progressbar.setVisibility(View.VISIBLE);
        text1=(TextView) findViewById(R.id.text1);
        button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCoordoGeo(longitud,latitud);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String Databaselatitude = snapshot.child("latitude").getValue().toString().substring(1, snapshot.child("latitude").getValue().toString().length() - 1);
                    String Databaselongitude = snapshot.child("longitude").getValue().toString().substring(1, snapshot.child("latitude").getValue().toString().length() - 1);

                    String[] strlat = Databaselatitude.split(", ");
                    Arrays.sort(strlat);
                    String latitude = strlat[strlat.length - 1].split("''")[1];

                    String[] strlong = Databaselongitude.split(", ");
                    Arrays.sort(strlong);
                    String longitude = strlat[strlat.length - 1].split("''")[1];

                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(latitude + "," + longitude));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                } catch (Exception e) {
                    Toast.makeText(MapsActivity.this, e.getMessage()+" 3 R", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    Toast.makeText(MapsActivity.this, " Afficher R", Toast.LENGTH_LONG).show();
                    editlong.setText(Double.toString(location.getLongitude()));
                    editlati.setText(Double.toString(location.getLatitude()));
                    latitud=(float)location.getLatitude();
                    longitud=(float)location.getLongitude();
                    LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Actual position"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    if(!TextUtils.isEmpty(editlong.getText().toString()) && !TextUtils.isEmpty(editlati.getText().toString())){
                        button1.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                        text1.setText("Deja Recuperer");
                    }
                } catch (Exception e) {
                    Toast.makeText(MapsActivity.this, e.getMessage() + " 1 R", Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            /**
             * Called when the provider is disabled by the user. If requestLocationUpdates
             * is called on an already disabled provider, this method is called
             * immediately.
             *
             * @param provider the name of the location provider that has become disabled
             */
            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
              //public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
        }
        catch (Exception e){
            Toast.makeText(MapsActivity.this,e.getMessage()+" 2 R", Toast.LENGTH_LONG).show();
        }



    }

    public void updatebuttonclick(View view){
        databaseReference.child("latitude").push().setValue(editlati.getText().toString());
        databaseReference.child("longitude").push().setValue(editlong.getText().toString());
    }

    private void sendCoordoGeo(float longitude,float latitude) {
        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Login");
        progressDialog1.setMessage("Please wait ....");
        progressDialog1.show();

        APIService Service1 = APIUrl.getClient().create(APIService.class);
        Toast.makeText(getApplicationContext(),String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getAdresse().getIdAdresse()) + "  id" , Toast.LENGTH_LONG).show();

        Call<List<CoordoGeo>> call = Service1.createCoordoGeo(SharedPrefManager.getInstance(getApplicationContext()).getAdresse().getIdAdresse(),
                                                            longitude, latitude);
        Toast.makeText(getApplicationContext(), "R3", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<List<CoordoGeo>>() {
            @Override
            public void onResponse(Call<List<CoordoGeo>> call, Response<List<CoordoGeo>> response) {

                if (response.body()!=null) {
                    Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG).show();
                    CoordoGeo coordogeo = new CoordoGeo(
                            response.body().get(0).getIdCoordoGeo(),
                            response.body().get(0).getLongitude(),
                            response.body().get(0).getLatutide());

                    SharedPrefManager.getInstance(getApplicationContext()).CoordoGeo(coordogeo);
                    progressDialog1.dismiss();
                    Intent intent = new Intent(getApplicationContext(), MainA.class);
                    startActivity(intent);

                    SharedPrefManager.getInstance(getApplicationContext()).connect(1);


                } else {
                    Toast.makeText(MapsActivity.this, "error fatal", Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<CoordoGeo>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog1.dismiss();
            }
        });
    }

}