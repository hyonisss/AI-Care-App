package com.example.medicine_map;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity_Login extends AppCompatActivity {
    EditText Edit_id, Edit_password;

    Button Button_login, Button_signup;
    String emailOK = "123@gmail.com";
    String passwordOK = "1234";

    String inputEmail = "";
    String inputPassword = "";
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        Edit_id = findViewById(R.id.Edit_id);
        Edit_password = findViewById(R.id.Edit_password);
        Button_login = findViewById(R.id.Button_login);
        Button_signup= findViewById(R.id.Button_signup);
        firebaseAuth = FirebaseAuth.getInstance();

        //LinearLayout_id = findViewById(R.id.LinearLayout_id);

        //1.값을 가져온다 - 검사 (123@gmail.com / 1234)
        //2.클릭을 감지한다
        //3. 1번의 값을 다음 액티비티로 넘긴다
        Button_login.setClickable(false);
        //LinearLayout_id.setClickable(false);
        Edit_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //           Log.d("SENTI", s + "," + count);
                if (s != null) {
                    inputEmail = s.toString();
                    //LinearLayout_id.setClickable(validation());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d("SENTI", s + "," + count);
                if (s != null) {
                    inputPassword = s.toString();
                    //LinearLayout_id.setClickable(validation());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //     LinearLayout_login.setClickable(true);
        Button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Login.this, MainActivity_Sign.class);
                startActivity(intent);
            }
        });
        Button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Edit_id.getText().toString();
                final String password = Edit_password.getText().toString();
                //Acitivy 넘기기

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity_Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity_Login.this, MainActivity_Home.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", password);
                                    Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(MainActivity_Login.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                //Acitivy 넘기기
            }
        });
    }




}




