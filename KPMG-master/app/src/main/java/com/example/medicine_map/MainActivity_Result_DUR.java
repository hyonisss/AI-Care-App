package com.example.medicine_map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_Result_DUR extends AppCompatActivity {
    String pregtype2,prohibition2,pregingr2;
    String joinkor2, joineng2, joinclass2, joinpro2;
    String p1[] = new String[100]; // joinkor 배열
    String p2[] = new String[100]; // joineng 배열
    String p3[] = new String[100]; // joinclass배열
    String p4[] = new String[100];
    TextView x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main_result_dur);
        Intent intent = getIntent();
        pregtype2 = intent.getStringExtra("Pregtype");
        prohibition2 = intent.getStringExtra("Prohibition");
        pregingr2 = intent.getStringExtra("Pregingr");
        p1 = intent.getStringArrayExtra("Joinkor");
        p2 = intent.getStringArrayExtra("Joineng");
        p3 = intent.getStringArrayExtra("Joinclass");
        p4 = intent.getStringArrayExtra("Joinpro");
        Log.d("얼른 집에 가고 싶다", p1[1]+"//"+p2[1]+"//"+p3[1]+"//"+p4[1]);
        x1 = (TextView)findViewById(R.id.dur_join_1); x2 = (TextView)findViewById(R.id.dur_join_2); x3 = (TextView)findViewById(R.id.dur_join_3);
        x4 = (TextView)findViewById(R.id.dur_joinclass_1); x5 = (TextView)findViewById(R.id.dur_joinclass_2); x6 = (TextView)findViewById(R.id.dur_joinclass_3);
        x7 = (TextView)findViewById(R.id.dur_joininfo_1); x8 = (TextView)findViewById(R.id.dur_joininfo_2); x9 = (TextView)findViewById(R.id.dur_joininfo_3);
        x10 = (TextView)findViewById(R.id.dur_preg_1); x11 = (TextView)findViewById(R.id.dur_pregingr_1); x12 = (TextView)findViewById(R.id.dur_preginfo_1);
        x1.setText(p1[0]); x2.setText(p1[1]); x3.setText(p1[2]);
        x4.setText(p3[0]); x5.setText(p3[1]); x6.setText(p3[2]);
        x7.setText(p4[0]); x8.setText(p4[1]); x9.setText(p4[2]);
        x10.setText("1등급"); x11.setText(pregingr2); x12.setText(prohibition2);





    }

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