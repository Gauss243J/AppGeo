package com.gauss243j.appgeo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.gauss243j.appgeo.api.APIService;
import com.gauss243j.appgeo.api.APIUrl;
import com.gauss243j.appgeo.helper.SharedPrefManager;
import com.gauss243j.appgeo.models.Citoyen;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class Signup extends AppCompatActivity {

    Button btnsenregistrer;

    TextView connTextview;

    EditText editNom;
    EditText editTextAdressMail;
    EditText editTextTextPassword;
    EditText editTextNumeroPhone;
    EditText editTextcarte;
    private int EXTERNAL_PERMISSION=23;
    List<Citoyen> resulte;

    private AwesomeValidation awesomeValidation;
    private ProgressDialog progressDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        btnsenregistrer=(Button)findViewById(R.id.btnsenregistrer);
        editNom=(EditText)findViewById(R.id.editNom);
        editTextAdressMail=(EditText)findViewById(R.id.editTextAdressMail);
        editTextTextPassword=(EditText)findViewById(R.id.editTextTextPassword);
        editTextNumeroPhone=(EditText)findViewById(R.id.editTextNumeroPhone);
        editTextcarte=(EditText)findViewById(R.id.editTextcarte);

        ActivityCompat.requestPermissions(Signup.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_PERMISSION);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        connTextview=(TextView)findViewById(R.id.conn) ;
        connTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.editNom, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextAdressMail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.editTextNumeroPhone, Patterns.PHONE, R.string.emailerror);

        btnsenregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {

                    Toast.makeText(Signup.this, "Validation Successfull", Toast.LENGTH_LONG).show();
                    Validataion ();
                    //process the data further
                }

            }
        });
    }

    private void Validataion (){

        String name = editNom.getText().toString();
        String email = editTextAdressMail.getText().toString();
        String password = editTextTextPassword.getText().toString();
        String numCarte = editTextcarte.getText().toString();
        String numPhone = editTextNumeroPhone.getText().toString();

        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Login");
        progressDialog1.setMessage("Please wait ....");
        progressDialog1.show();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(Signup.this, " Name is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }else if(TextUtils.isEmpty(email)) {
            Toast.makeText(Signup.this, "email is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }else if(TextUtils.isEmpty(password)) {
            Toast.makeText(Signup.this, "password is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }
        else if(TextUtils.isEmpty(numCarte)) {
            Toast.makeText(Signup.this, "Numero Carte is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }
        else if(TextUtils.isEmpty(numPhone)) {
            Toast.makeText(Signup.this, "Numero phone is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }
        else{
            Toast.makeText(Signup.this, "bon", Toast.LENGTH_LONG).show();
            try {
                userSignUp(name, email,numPhone, password,numCarte);
            }catch (Exception ex){
                Toast.makeText(Signup.this, ex.getMessage(),Toast.LENGTH_LONG).show();
            }

        }

    }

   private void userSignUp(String name,String email,String numerophone,String password, String numCarte) {
       Toast.makeText(getApplicationContext(), "R1", Toast.LENGTH_LONG).show();
        APIService Service1 = APIUrl.getClient().create(APIService.class);
       Toast.makeText(getApplicationContext(), "R2", Toast.LENGTH_LONG).show();
        Call<List<Citoyen>> call = Service1.createCitoyen(name, email,numerophone,password,numCarte);
       Toast.makeText(getApplicationContext(), "R3", Toast.LENGTH_LONG).show();
       call.enqueue(new Callback<List<Citoyen>>() {
           @Override
           public void onResponse(Call<List<Citoyen>> call, Response<List<Citoyen>> response) {
                resulte = response.body();
                if (resulte!=null) {
                    Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG).show();
                    Citoyen citoyen = new Citoyen(
                            resulte.get(0).getIdCitoyen(),
                            resulte.get(0).getNom(),
                            resulte.get(0).getAdresseMail(),
                            resulte.get(0).getNumeroPhone(),
                            resulte.get(0).getNumeroCarte());
                    SharedPrefManager.getInstance(getApplicationContext()).citoyenLogin(citoyen);
                    startActivity(new Intent(getApplicationContext(), AutresInfo.class));
                    progressDialog1.dismiss();
                } else {
                    Toast.makeText(Signup.this, "error fatal", Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                }
           }

           @Override
           public void onFailure(Call<List<Citoyen>> call, Throwable t) {
               Toast.makeText(Signup.this, t.getMessage(), Toast.LENGTH_SHORT).show();

               progressDialog1.dismiss();
           }
       });
   }
}
