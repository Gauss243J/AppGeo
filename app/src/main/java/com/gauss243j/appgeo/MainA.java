package com.gauss243j.appgeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gauss243j.appgeo.api.APIService;
import com.gauss243j.appgeo.api.APIUrl;
import com.gauss243j.appgeo.helper.SharedPrefManager;
import com.gauss243j.appgeo.models.Alerte;
import com.gauss243j.appgeo.models.Citoyen;
import com.skyfishjy.library.RippleBackground;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainA extends AppCompatActivity {

    Timer timer = new Timer();
    RippleBackground rippleBackground;
    List<Alerte> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);


      rippleBackground=(RippleBackground)findViewById(R.id.content);

        TextView imageView=(TextView)findViewById(R.id.centerImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(MainA.this);
                View promptsView = li.inflate(R.layout.dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainA.this);
                alertDialogBuilder.setView(promptsView);


                final TextView textcancel = (TextView) promptsView.findViewById(R.id.cancel);
                final TextView textsend = (TextView) promptsView.findViewById(R.id.send);
                final TextView texttitle = (TextView) promptsView.findViewById(R.id.title);

                Animation animationAlpha = new AlphaAnimation(0.0f, 1.0f);
                animationAlpha.setDuration(1000);
                texttitle.startAnimation(animationAlpha);

                alertDialogBuilder
                        .setCancelable(false);

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                textcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                        Toast.makeText(MainA.this, String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getCitoyen().getIdCitoyen())+ "  id", Toast.LENGTH_SHORT).show();
                        rippleBackground.stopRippleAnimation();
                    }
                });

                textsend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rippleBackground.startRippleAnimation();

                        try{
                            sendAlerte();
                            alertDialog.dismiss();

                           timer.schedule(new Tache(MainA.this), 2000, 2000);

                        }catch(Exception ex){
                            Toast.makeText(MainA.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainA.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }



    private void sendAlerte() {
        Toast.makeText(getApplicationContext(), "R12", Toast.LENGTH_LONG).show();
        APIService Service1 = APIUrl.getClient().create(APIService.class);
        Toast.makeText(getApplicationContext(), "R22", Toast.LENGTH_LONG).show();
        Call<List<Alerte>> call = Service1.createAlerte(SharedPrefManager.getInstance(getApplicationContext()).getCitoyen().getIdCitoyen());
        Toast.makeText(getApplicationContext(), "R32", Toast.LENGTH_LONG).show();

        call.enqueue(new Callback<List<Alerte>>() {
            @Override
            public void onResponse(Call<List<Alerte>> call, Response<List<Alerte>> response) {
                list=response.body();
                if (list!=null) {
                    Toast.makeText(getApplicationContext(), "Registered successfully 2", Toast.LENGTH_LONG).show();
                    Alerte alerte  = new Alerte (
                            list.get(0).getIdAlerte(),
                            list.get(0).isAccuseReception());
                    SharedPrefManager.getInstance(getApplicationContext()).Alerte(alerte);
                    Toast.makeText(MainA.this, String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getIdAlerte())+ "  idAlerte", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainA.this, String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getCitoyen().getIdCitoyen())+ "  idcitoyen", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainA.this, "error fatal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Alerte>> call, Throwable t) {
                Toast.makeText(MainA.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }




    public class Tache extends TimerTask {
        public Tache(Context context) {
            this.context = context;
        }

        Context context;

        @Override
        public void run() {
            MainA.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    APIService Service1 = APIUrl.getClient().create(APIService.class);

                    Toast.makeText(MainA.this, String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getIdAlerte())+ "  idtaskal", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainA.this, String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getCitoyen().getIdCitoyen())+ "  idcito", Toast.LENGTH_SHORT).show();
                    Call<List<Alerte>> call = Service1.getAlerte(SharedPrefManager.getInstance(getApplicationContext()).getCitoyen().getIdCitoyen(),
                            SharedPrefManager.getInstance(getApplicationContext()).getIdAlerte());
                    Toast.makeText(getApplicationContext(), "Task", Toast.LENGTH_LONG).show();
                    call.enqueue(new Callback<List<Alerte>>() {
                        @Override
                        public void onResponse(Call<List<Alerte>> call, Response<List<Alerte>> response) {
                                list=response.body();
                            Toast.makeText(getApplicationContext(), "Registered ", Toast.LENGTH_LONG).show();
                            if (list!=null) {
                                Toast.makeText(getApplicationContext(), "Registered 2", Toast.LENGTH_LONG).show();
                                Alerte alerte1  = new Alerte (
                                        list.get(0).getIdAlerte(),
                                        list.get(0).isAccuseReception());
                                SharedPrefManager.getInstance(getApplicationContext()).Alerte(alerte1);
                                Toast.makeText(MainA.this, String.valueOf(response.body().get(0).isAccuseReception())+ "  bool", Toast.LENGTH_SHORT).show();

                                if( SharedPrefManager.getInstance(getApplicationContext()).getAlerte()==1){
                                    rippleBackground.stopRippleAnimation();
                                    Toast.makeText(MainA.this, "Alerte Recu", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainA.this, "Alerte Recu", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainA.this, "Alerte Recu", Toast.LENGTH_SHORT).show();
                                }
                                else {}

                            } else {
                                Toast.makeText(MainA.this, "error fatal 2", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Alerte>> call, Throwable t) {
                            Toast.makeText(MainA.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        );}}








}


