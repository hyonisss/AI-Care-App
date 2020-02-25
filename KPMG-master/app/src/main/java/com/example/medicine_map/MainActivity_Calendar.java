package com.example.medicine_map;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity_Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseauth;
        firebaseauth = FirebaseAuth.getInstance();
        String id = firebaseauth.getCurrentUser().getEmail().split("@")[0];
        DatabaseReference myRef = database.getReference().child("User Database").child(id).child("PerscriptionID").child("Drug Insurance Code");



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calendar);
        //뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView medicineTIme1 = (TextView) findViewById(R.id.Medicine_time1);
        TextView medicineTIme2 = (TextView) findViewById(R.id.Medicine_time2);
        TextView medicineTIme3 = (TextView) findViewById(R.id.Medicine_time3);
        medicineTIme1.setText("아침");
        medicineTIme2.setText("점심");
        medicineTIme3.setText("저녁");

        //3일이면, pivotDate =
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Database : dataSnapshot.getChildren()) {
                    String i = null;
                    String name = null;
                    for (DataSnapshot Database1 : Database.getChildren()) {
                        if (Database1.getKey().equals("Time")) {
                            i = Database1.getValue().toString();
                        }
                        if (Database1.getKey().equals("Name")) {
                            name = Database1.getValue().toString();
                        }
                    }
                    timeSection(i, name);
                }}

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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



    void timeSection(String j, String name) {
        char [] array = j.toCharArray();
        TextView medicineName1 = (TextView) findViewById(R.id.Medicine_name1);
        TextView medicineName2 = (TextView) findViewById(R.id.Medicine_name2);
        TextView medicineName3 = (TextView) findViewById(R.id.Medicine_name3);
        for( char i : array){
            if (i=='1') {
                if(medicineName1.getText().equals("TextView")){
                    medicineName1.setText(name);
                }
                else{
                    medicineName1.append(" ," + name );
                }
            }
            if (i=='2') {
                if(medicineName2.getText().equals("TextView")){
                    medicineName2.setText(name);
                }
                else{
                    medicineName2.append(" ," + name);
                }
            }
            if (i=='3') {
                if(medicineName3.getText().equals("TextView")){
                    medicineName3.setText(name);
                }
                else{
                    medicineName3.append(" ," + name);
                }
            }
        }
    }


}
