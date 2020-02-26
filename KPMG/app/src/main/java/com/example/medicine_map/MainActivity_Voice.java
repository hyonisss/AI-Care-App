package com.example.medicine_map;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity_Voice extends AppCompatActivity {
    ImageView speechButton;
    EditText speechText;
    private static final int RECOGNIZER_RESULT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_voice);
        //뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //음성인식
        speechButton = findViewById(R.id.voice_Button);
           speechText = findViewById(R.id.textView);
        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text");
                startActivityForResult(speechIntent, RECOGNIZER_RESULT);


            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            speechText.setText(matches.get(0));
            String result = matches.get(0);
            if (result.equals("이소티논")) {
                Intent intent = new Intent(MainActivity_Voice.this, MainActivity_Fragment.class);
                startActivity(intent);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

