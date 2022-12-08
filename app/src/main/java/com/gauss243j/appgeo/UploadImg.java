package com.gauss243j.appgeo;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.gauss243j.appgeo.api.APIService;
import com.gauss243j.appgeo.api.APIUrl;
import com.gauss243j.appgeo.helper.SharedPrefManager;
import com.gauss243j.appgeo.models.CitoyenImg;
import com.gauss243j.appgeo.models.MyResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadImg extends AppCompatActivity {
        private Button button1;
        private ImageView imageView1;
        private int EXTERNAL_PERMISSION=23;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.uploadimg);
               button1 = (Button) findViewById(R.id.btnPhoto12);
                imageView1 = (ImageView) findViewById(R.id.image);

                button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                startActivity(intent);

                        }
                });




                //adding click listener to button
                imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                //opening file chooser
                              //  ActivityCompat.requestPermissions(UploadImg.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                             //           EXTERNAL_PERMISSION);
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, 100);
                        }
                });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                        //the image URI
                        Uri selectedImage = data.getData();
                        uploadFile(selectedImage , "photo");

                }
        }


        private void uploadFile(Uri fileUri, String desc) {

                //creating a file
                File file = new File(getRealPathFromURI(fileUri));

                //creating request body for file
                RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
                RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);

                //The gson builder
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();


                //creating retrofit object
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                //creating our api
                APIService api = retrofit.create(APIService.class);

                //creating a call and calling the upload image method
                Call<List<CitoyenImg>> call = api.uploadImage(requestFile, descBody, SharedPrefManager.getInstance(getApplicationContext()).getCitoyen().getIdCitoyen());

                //finally performing the call
                call.enqueue(new Callback<List<CitoyenImg>>() {
                        @Override
                        public void onResponse(Call<List<CitoyenImg>> call, Response<List<CitoyenImg>> response) {
                                if (response.body()!=null) {
                                        Toast.makeText(getApplicationContext(),"Download Successfully", Toast.LENGTH_LONG).show();
                                        Toast.makeText(getApplicationContext(),response.body().get(0).getImage().toString(), Toast.LENGTH_LONG).show();

                                       //  startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                                       // finish();

                                        // userSignUp();
                                } else {
                                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
                                }
                        }

                        @Override
                        public void onFailure(Call<List<CitoyenImg>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                });
        }





        /*
         * This method is fetching the absolute path of the image file
         * if you want to upload other kind of files like .pdf, .docx
         * you need to make changes on this method only
         * Rest part will be the same
         * */
        private String getRealPathFromURI(Uri contentUri) {
                String[] proj = {MediaStore.Images.Media.DATA};
                CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
                Cursor cursor = loader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String result = cursor.getString(column_index);
                cursor.close();
                return result;
        }


 /*public class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
                super(context);
       }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
                if (source == null) return null;

                int size = Math.min(source.getWidth(), source.getHeight());
                int x = (source.getWidth() - size) / 2;
                int y = (source.getHeight() - size) / 2;

                // TODO this could be acquired from the pool too
                Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

                Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
                if (result == null) {
                        result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                }

                Canvas canvas = new Canvas(result);
                Paint paint = new Paint();
                paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                paint.setAntiAlias(true);
                float r = size / 2f;
                canvas.drawCircle(r, r, r, paint);
                return result;
        }
        @Override public String getId() {
                return getClass().getName();

        }*/
}