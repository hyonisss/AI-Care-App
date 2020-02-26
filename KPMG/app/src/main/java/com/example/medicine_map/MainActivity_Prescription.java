package com.example.medicine_map;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity_Prescription extends AppCompatActivity {

    private ArrayList<prescription2_data> arrayList;
    private com.example.medicine_map.prescription2_adapter prescription2_adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prescription);

        Button cambt = (Button) findViewById(R.id.cambt);
        Button more = (Button) findViewById(R.id.more);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        prescription2_adapter = new prescription2_adapter(arrayList);
        recyclerView.setAdapter(prescription2_adapter);



//        추가버튼 Activity
        Button bt = (Button)findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      prescription2_data prescription2_data = new prescription2_data("2019.12.27", "한사랑의원", "인두염", "남은 복용일: 2일", R.drawable.prescription_icon, R.drawable.clock);
                                      arrayList.add(prescription2_data);
                                      prescription2_adapter.notifyDataSetChanged();

                                  }
                              });
        more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity_Prescription_detail.class);
                startActivity(intent);

            }
        });



        //뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void Event(View v) {
        Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
        startActivity(intent);
    }

}
