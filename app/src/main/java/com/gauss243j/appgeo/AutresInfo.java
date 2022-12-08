package com.gauss243j.appgeo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gauss243j.appgeo.api.APIService;
import com.gauss243j.appgeo.api.APIUrl;
import com.gauss243j.appgeo.helper.SharedPrefManager;
import com.gauss243j.appgeo.models.Adresse;
import com.gauss243j.appgeo.models.Citoyen;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutresInfo extends AppCompatActivity {

    Button btnAutres;
    EditText editTextCommune;
    EditText editTextQuartier;
    EditText editTextAvenue;
    EditText editTextNumero;
    private ProgressDialog progressDialog1;

    List<Adresse> resulte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autresinfo);
        btnAutres=(Button)findViewById(R.id.btnAutres);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        editTextCommune=(EditText)findViewById(R.id.editTextCommune);
        editTextQuartier=(EditText)findViewById(R.id.editTextQuartier);
        editTextAvenue=(EditText)findViewById(R.id.editTextAvenue);
        editTextNumero=(EditText)findViewById(R.id.editTextNumero);


        btnAutres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validtaion();

            }
        });
    }


    private void Validtaion (){

        String commune = editTextCommune.getText().toString();
        String quartier = editTextQuartier.getText().toString();
        String avenue = editTextAvenue.getText().toString();
        String numero = editTextNumero.getText().toString();


        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Login");
        progressDialog1.setMessage("Please wait ....");
        progressDialog1.show();

        if(TextUtils.isEmpty(commune)){
            Toast.makeText(this, " Name is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }else if(TextUtils.isEmpty(quartier)) {
            Toast.makeText(this, "email is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }else if(TextUtils.isEmpty(avenue)) {
            Toast.makeText(this, "password is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }
        else if(TextUtils.isEmpty(numero)) {
            Toast.makeText(this, "Numero Carte is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }
        else{
             userAutres(commune, quartier,avenue, Integer.valueOf(numero));
        }

    }


    private void userAutres(String commune,String quartier,String avenue,int numero) {
        Toast.makeText(getApplicationContext(), "R1", Toast.LENGTH_LONG).show();
        APIService Service1 = APIUrl.getClient().create(APIService.class);
        Toast.makeText(getApplicationContext(), "R2", Toast.LENGTH_LONG).show();
        Call<List<Adresse>> call = Service1.createAdress(SharedPrefManager.getInstance(getApplicationContext()).getCitoyen().getIdCitoyen(),
                                                            commune,quartier,avenue,numero);
        Toast.makeText(getApplicationContext(), "R3", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<List<Adresse>>() {
            @Override
            public void onResponse(Call<List<Adresse>> call, Response<List<Adresse>> response) {


                resulte = response.body();

                if (resulte!=null) {
                    Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG).show();
                    Adresse adresse = new Adresse(
                            resulte.get(0).getIdAdresse(),
                            resulte.get(0).getCommune(),
                            resulte.get(0).getQuartier(),
                            resulte.get(0).getAvenue(),
                            resulte.get(0).getNumero());
                    SharedPrefManager.getInstance(getApplicationContext()).adresseLogin(adresse);
                    Toast.makeText(getApplicationContext(),String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getAdresse().getIdAdresse()) + "  id" , Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), UploadImg.class));
                    progressDialog1.dismiss();
                } else {
                    Toast.makeText(AutresInfo.this, "error fatal", Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Adresse>> call, Throwable t) {
                Toast.makeText(AutresInfo.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog1.dismiss();
            }
        });
    }


}
