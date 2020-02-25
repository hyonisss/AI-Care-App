package com.example.medicine_map;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_Prescription_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prescription_detail);
        ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.Medication_day_progressBar);
        progressBar1.setIndeterminate(false);
        progressBar1.setMax(100);
        progressBar1.setProgress(66);
        progressBar1.getIndeterminateDrawable().setColorFilter(Color.parseColor("#345e99"), PorterDuff.Mode.SRC_IN);
        // 뒤로가기 버튼
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

}
