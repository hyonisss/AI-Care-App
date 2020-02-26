package com.example.medicine_map;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;

import java.lang.annotation.Documented;


// 한번 해보기
public class MainActivity_Maps extends FragmentActivity implements OnMapReadyCallback {
    Document doc = null;
    ListView list;
    private GoogleMap mMap;
    String tell;
    String t1[] = new String[100];  // 약국
    String t2[] = new String[100];  // 전화번호
    double t3[] = new double[100];  // x 좌표
    double t4[] = new double[100];  // y 좌표
    int t5[] = new int[100]; // 공휴일 여부
    int t6[] = new int[100]; // 심야약국 여부
    String t7[] = new String[100];  // 전화번호
    int k=0;  // 어떤 버튼인지: 0 = 그냥 모든 약국 | 1 = 심야 약국 | 2 = 공휴일
    LatLng[] PharLoc = new LatLng[100];
    Marker[] PharMark = new Marker[100];
    LatLng[] PharLoc1 = new LatLng[100];
    Marker[] PharMark1 = new Marker[100];
    LatLng[] PharLoc2 = new LatLng[100];
    Marker[] PharMark2 = new Marker[100];
    double pharmapx,pharmapy;
    String pharmapname;
    Button nightBtn;
    Button holiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




        /*//뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        nightBtn = (Button) findViewById(R.id.pharmacy_night);
        holiBtn = (Button) findViewById(R.id.pharmacy_holiday);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();
        t1 = intent.getStringArrayExtra("PharName"); // 약국 이름
        t2 = intent.getStringArrayExtra("Tel"); // 전화번호
        t3 = intent.getDoubleArrayExtra("PosX");
        t4 = intent.getDoubleArrayExtra("PosY");
        t5 = intent.getIntArrayExtra("Holiday");
        t6 = intent.getIntArrayExtra("Nightday");
        t7 = intent.getStringArrayExtra("PharAddr"); // 약국 주소
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void Listener2(View view){
        Intent intent2 = null;
        switch (view.getId()){
            case R.id.phar_first_tel:
                tell = t2[0];
                break;
            case R.id.phar_second_tel:
                tell = t2[1];
                break;
            case R.id.phar_third_tel:
                tell = t2[2];
                break;
            case R.id.phar_fourth_tel:
                tell = t2[3];
                break;
        }
        intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tell));
        if (intent2 !=null){
            startActivity(intent2);
        }
    }
    public void Listener3(View view){
        k=3;
        switch (view.getId()){
            case R.id.phar_first_map:
                pharmapx = t3[0];
                pharmapy = t4[0];
                pharmapname = "혜화약국";
                break;
            case R.id.phar_second_map:
                pharmapx = t3[1];
                pharmapy = t4[1];
                pharmapname = "건강약국";
                break;
            case R.id.phar_third_map:
                pharmapx = t3[2];
                pharmapy = t4[2];
                pharmapname = "글로리아약국";
                break;
            case R.id.phar_fourth_map:
                pharmapx = t3[3];
                pharmapy = t4[3];
                pharmapname = "경일약국";
                break;
        }
        this.onMapReady(mMap);
    }
    public void nightClick(View view){
        k=1;
        this.onMapReady(mMap);
    }

    public void holiClick(View view){
        k=2;
        this.onMapReady(mMap);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(11);
        if (k==0){
            for (int i=0;(t3[i]!=-100);i++){   // 조건부 다시 만들지 말지 고민하기
                PharLoc[i]=new LatLng(t3[i],t4[i]);
                PharMark[i]=mMap.addMarker(new MarkerOptions().position(PharLoc[i]).title(t1[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacy_icon)));
                if (i== (5)){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(PharLoc[i]));
                }
            }
        }
        else if (k==1){
            mMap.clear();
            /*for (int i=0;(t3[i]!=-100);i++){
                PharMark[i].remove();
            }*/
            for (int i=0;(t3[i]!=-100);i++){   // 조건부 다시 만들지 말지 고민하기
                if (t6[i]==1) {
                    Log.d("나는 첫번째인데?","약국이름은"+t1[i]+"i는"+i+"t[6]:"+t6[i]+"");
                    PharLoc1[i] = new LatLng(t3[i], t4[i]);
                    PharMark1[i] = mMap.addMarker(new MarkerOptions().position(PharLoc1[i]).title(t1[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacy_icon)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(PharLoc1[i]));    // 이걸 어떻게 Focus 시킬지 고민해보기
                }
            }
        }
        else if (k==2){
            mMap.clear();
            /*for (int i=0;(t3[i]!=-100);i++){
                PharMark[i].remove();
            }*/
            for (int i=0;(t3[i]!=-100);i++){   // 조건부 다시 만들지 말지 고민하기
                if (t5[i]==1) {
                    Log.d("나는 두번째인데?","약국이름은"+t1[i]+"i는"+i+"t[5]:"+t5[i]+"");
                    PharLoc2[i] = new LatLng(t3[i], t4[i]);
                    PharMark2[i] = mMap.addMarker(new MarkerOptions().position(PharLoc2[i]).title(t1[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacy_icon)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(PharLoc2[i]));    // 이걸 어떻게 Focus 시킬지 고민해보기
                }
            }
        }
        else if (k==3) {
            mMap.clear();
            /*for (int i=0;(t3[i]!=-100);i++){
                PharMark[i].remove();
            }*/
            LatLng PharLoc3 = new LatLng(pharmapx,pharmapy);
            Marker PharMark3 = mMap.addMarker(new MarkerOptions().position(PharLoc3).title(pharmapname).icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacy_icon)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(PharLoc3));    // 이걸 어떻게 Focus 시킬지 고민해보기
        }
    }

    private class Listview {
    }
}