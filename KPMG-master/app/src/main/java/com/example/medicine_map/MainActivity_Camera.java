package com.example.medicine_map;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

//밑에 조민주

import edmt.dev.edmtdevcognitivevision.Contract.AnalysisResult;
import edmt.dev.edmtdevcognitivevision.Contract.Caption;
import edmt.dev.edmtdevcognitivevision.Rest.VisionServiceException;
import edmt.dev.edmtdevcognitivevision.VisionServiceClient;
import edmt.dev.edmtdevcognitivevision.VisionServiceRestClient;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity_Camera extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public Bitmap bitmap;
    int ccon =0;
    String id;
    Button more_button;
    ImageView image1;
    TextView texts1,texts2,texts3,texts4,texts5;

    Button btnProcess;TextView txtResult;
    private final String API_KEY = "f908dc54decf402fa57a5db9baa0977b";  //조민주
    private final String API_LINK =
            "https://koreacentral.api.cognitive.microsoft.com/vision/v1.0"; //조민주
    VisionServiceClient visionServiceClient = new VisionServiceRestClient(API_KEY,API_LINK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera);

        TedPermission.with(getApplicationContext()).setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();



        image1 = (ImageView)findViewById(R.id.Medicine_image);//썸네일뷰
        more_button = findViewById(R.id.more_button);//약검색 버튼###




        //약 검색결과로 이동
        more_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Camera.this, MainActivity_Fragment.class);
                startActivity(intent);

                //뒤로가기 버튼 생성
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });
        txtResult = (TextView)findViewById(R.id.Medicine_name);


    }
    public void showCameraBtn(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ccon=1;
        startActivityForResult(intent,1);
    }
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            //촬영한 사진을 가져온다.
            bitmap = (Bitmap)data.getParcelableExtra("data");
            image1.setImageBitmap(bitmap);
            ///////조민주
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,outputStream);
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            AsyncTask<InputStream,String,String> visionTask = new AsyncTask<InputStream, String, String>() {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity_Camera.this);

                @Override
                protected void onPreExecute() {
                    progressDialog.show();
                }

                @Override
                protected String doInBackground(InputStream... inputStreams) {
                    try
                    {
                        publishProgress("Reconizing...");
                        String[] features = {"Description"};
                        String[] details = {};
                        //InputStream stream = inputStreams[0];
                        //AnalysisResult result = visionServiceClient.;
                        AnalysisResult result = visionServiceClient.describe(inputStreams[0],1);
                        //원본 AnalysisResult result = visionServiceClient.analyzeImage(inputStreams[0],features,details);
                        //FileInputStream oo = new (openFileInput(outputStream.toString()));
                        //AnalysisResult result = visionServiceClient.recognizeText(inputStreams[0],"ENG",true);
                        //OCR result = visionServiceClient.recognizeText(stream,"en",true);

                        String jsonResult = new Gson().toJson(result);
                        return jsonResult;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (VisionServiceException e) {
                        e.printStackTrace();
                    }
                    return "";
                }

                @Override
                protected void onPostExecute(String s){

                    if(TextUtils.isEmpty(s)){

                        Toast.makeText(MainActivity_Camera.this,"API Return Empty Result",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        AnalysisResult result = new Gson().fromJson(s, AnalysisResult.class);
                        StringBuilder result_Text = new StringBuilder();
                        for (Caption caption : result.description.captions)
                            result_Text.append(caption.text);
                        String results = result_Text.toString(); //최종 수정
                        if (results.equals("a close up of a egg")||results.equals("a close up of a white wall")||results.equals("a close up of a ball")||results.equals("a large white ball"))
                            id = "이소티논";
                        else id = "없음";
                        //txtResult.setText(id);
                        final TextView texts1 = findViewById(R.id.Name_View);
                        final TextView texts2 = findViewById(R.id.Ingredient_View);
                        final TextView texts3 = findViewById(R.id.ATC_View);
                        final TextView texts4 = findViewById(R.id.Inject_View);
                        final TextView texts5 = findViewById(R.id.Insurance_View);

                        DatabaseReference myRef = database.getReference().child("Drug Database").child(id);
                        texts1.setText(id);
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot Database : dataSnapshot.getChildren()) {
                                    if (Database.getKey().equals("Ingredients")) {
                                        texts2.setText(Database.getValue().toString());
                                    }
                                    if (Database.getKey().equals("ATC")) {
                                        texts3.setText(Database.getValue().toString());
                                    }
                                    if (Database.getKey().equals("Via")){
                                        texts4.setText(Database.getValue().toString());
                                    }
                                    if (Database.getKey().equals("Info")){
                                        texts5.setText(Database.getValue().toString());
                                    }

                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                    }

                }

                @Override
                protected void onProgressUpdate(String... values){
                    progressDialog.setMessage(values[0]);

                }

            };
            visionTask.execute(inputStream);

        }

//////조민주
    }




    PermissionListener permissionListener = new PermissionListener() {

        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT).show();

        }
    };










    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        //뒤로가기 버튼
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

