package com.example.medicine_map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_Sign extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("User Database");
    FirebaseAuth firebaseAuth;
    Button signup_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_signup);
        signup_button = findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                EditText email = (EditText) findViewById(R.id.email_edit);
                String emailID = email.getText().toString().trim();
                String userID = email.getText().toString().split("@")[0];
                EditText pwd = (EditText) findViewById(R.id.pwd_edit);
                String pwdID = pwd.getText().toString().trim();
                EditText sex = (EditText) findViewById(R.id.sex_edit);
                String sexID = sex.getText().toString().trim();
                EditText birth = (EditText) findViewById(R.id.birth_edit);
                String birthID = birth.getText().toString().trim();
                EditText height = (EditText) findViewById(R.id.height_edit);
              String heightID = height.getText().toString().trim();
              EditText weight = (EditText)findViewById(R.id.weight_edit);
              String weightID = weight.getText().toString().trim();
              EditText disease = (EditText)findViewById(R.id.disease_edit);
              String diseaseID = disease.getText().toString().trim();//User Information입니다. 추가할 사항은, user Information 자바파일도 같이 건들여야 합니다
                userInformation UserInformation = new userInformation(sexID, birthID, heightID, weightID, diseaseID);
                myRef.child(userID).setValue(UserInformation); //이 방식대로, prescriptionID 랑 Drug Insurance code 할것
                firebaseAuth.createUserWithEmailAndPassword(emailID, pwdID)
                        .addOnCompleteListener(MainActivity_Sign.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "가입완료", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity_Sign.this, MainActivity_Login.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity_Sign.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });

            }
        });

    }


}
