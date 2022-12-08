package com.gauss243j.appgeo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.gauss243j.appgeo.api.APIService;
import com.gauss243j.appgeo.api.APIUrl;
import com.gauss243j.appgeo.helper.SharedPrefManager;
import com.gauss243j.appgeo.models.Citoyen;
import com.gauss243j.appgeo.models.MyResponse;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Button btnseconnecter;
    EditText editLogName;
    EditText editLogPassword;
    TextView buttonSignup;
    private AwesomeValidation awesomeValidation;
    private ProgressDialog progressDialog1;
    MyResponse responses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnseconnecter=(Button)findViewById(R.id.btnseconnecter);
        buttonSignup=(TextView) findViewById(R.id.buttonSignup);
        editLogPassword = (EditText) findViewById(R.id.editLogPassword);
        editLogName = (EditText) findViewById(R.id.editLogName);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.editLogName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
       // awesomeValidation.addValidation(this, R.id.editLogPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });

        progressDialog1 = new ProgressDialog(this);
        progressDialog1.setTitle("Login");
        progressDialog1.setMessage("Please wait ....");
        btnseconnecter = (Button) findViewById(R.id.btnseconnecter);
        btnseconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {

                    Toast.makeText(Login.this, "Validation Successfull", Toast.LENGTH_LONG).show();
                    validation();
                    //process the data further
                }


            }
        });

    }


    private void validation(){

        String name = editLogName.getText().toString();
        String password = editLogPassword.getText().toString();
        progressDialog1.show();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(Login.this, " Name is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }else if(TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "password is required", Toast.LENGTH_SHORT).show();
            progressDialog1.dismiss();
        }else{
            userLogin(name, password);
            progressDialog1.dismiss();
        }

    }




    private void userLogin(String name,String password) {
        APIService Service1 = APIUrl.getClient().create(APIService.class);
        Call<MyResponse> call = Service1.loginCitoyen(name,password);
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                responses = response.body();
                if (responses.getError()==false) {
                    Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
                    SharedPrefManager.getInstance(getApplicationContext()).connect(1);
                    startActivity(new Intent(getApplicationContext(), MainA.class));
                    finish();
                    progressDialog1.dismiss();
                } else {
                    Toast.makeText(Login.this, "error fatal", Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog1.dismiss();
            }
        });
    }



}