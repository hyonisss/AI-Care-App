package com.example.medicine_map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity_Fragment extends AppCompatActivity {
    Button bt1, bt2, bt3, bt4, bt5, bt6, bt7;
    int i=0;
    int a=0;
    Document doc1;
    Intent intent;
    String pregtype,prohibition,pregingr;
    String joinkor, joineng, joinclass, joinpro;
    String k1[] = new String[100]; // joinkor 배열
    String k2[] = new String[100]; // joineng 배열
    String k3[] = new String[100]; // joinclass배열
    String k4[] = new String[100]; // joinpro 배열
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        bt3 = (Button)findViewById(R.id.bt3);
        bt4 = (Button)findViewById(R.id.bt4);
        bt5 = (Button)findViewById(R.id.bt5);
        bt6 = (Button)findViewById(R.id.bt6);
        bt7 = (Button)findViewById(R.id.bt7);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                MainActivity_Result_information activity_main_result_information = new MainActivity_Result_information();
                transaction.replace(R.id.frame, activity_main_result_information);

                transaction.commit();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                MainActivity_Result_Identification activity_main_result_identification = new MainActivity_Result_Identification();
                transaction.replace(R.id.frame, activity_main_result_identification);

                transaction.commit();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                MainActivity_Result_Efficient activity_main_result_efficient = new MainActivity_Result_Efficient();
                transaction.replace(R.id.frame, activity_main_result_efficient);

                transaction.commit();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                MainActivity_Result_Usage activity_main_result_usage = new MainActivity_Result_Usage();
                transaction.replace(R.id.frame, activity_main_result_usage);

                transaction.commit();
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                MainActivity_Result_Caution activity_main_result_caution = new MainActivity_Result_Caution();
                transaction.replace(R.id.frame, activity_main_result_caution);

                transaction.commit();
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                MainActivity_Result_Remedy activity_main_result_remedy = new MainActivity_Result_Remedy();
                transaction.replace(R.id.frame, activity_main_result_remedy);

                transaction.commit();
            }
        });

        /*bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Fragment.this, MainActivity_Result_DUR.class);
                startActivity(intent);
            }
        });*/



        //뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void Listener1(View view){
        // dur1 : 임부금기
        String dur1 = "http://apis.data.go.kr/1470000/DURPrdlstInfoService/getPwnmTabooInfoList?serviceKey=88G0%2Ft488vBqKUN6ZFSu7NVjUpf8jZ9F3croTiMnbohG2hxfM38jg0ZaHXsBM2f2MRk7BVwmLZxSDqKe8zzh%2Bg%3D%3D&typeName=%EC%9E%84%EB%B6%80%EA%B8%88%EA%B8%B0&itemName=%EC%9D%B4%EC%86%8C%ED%8B%B0%EB%85%BC%EC%97%B0%EC%A7%88%EC%BA%A1%EC%8A%90&pageNo=1&numOfRows=3";
        GetXMLTask1 task1 = new GetXMLTask1(this);
        task1.execute(dur1);
        // dur2 : 병용금기
        String dur2 = "http://apis.data.go.kr/1470000/DURPrdlstInfoService/getUsjntTabooInfoList?serviceKey=88G0%2Ft488vBqKUN6ZFSu7NVjUpf8jZ9F3croTiMnbohG2hxfM38jg0ZaHXsBM2f2MRk7BVwmLZxSDqKe8zzh%2Bg%3D%3D&typeName=%EB%B3%91%EC%9A%A9%EA%B8%88%EA%B8%B0&ingrCode=A005758&itemName=%EC%9D%B4%EC%86%8C%ED%8B%B0%EB%85%BC%EC%97%B0%EC%A7%88%EC%BA%A1%EC%8A%90&pageNo=1&numOfRows=40";
        GetXMLTask2 task2 = new GetXMLTask2(this);
        task2.execute(dur2);
        intent = new Intent(getBaseContext(),MainActivity_Result_DUR.class);
        intent.putExtra("Pregtype",pregtype);
        intent.putExtra("Prohibition",prohibition);
        intent.putExtra("Pregingr",pregingr);
        intent.putExtra("Joinkor",k1);
        intent.putExtra("Joineng",k2);
        intent.putExtra("Joinclass",k3);
        intent.putExtra("Joinpro",k4);
        //startActivity(intent);

    }
    private class GetXMLTask1 extends AsyncTask<String, Void, Document>{
        private Activity context;
        public GetXMLTask1(Activity context){
            this.context = context;
        }
        @Override
        protected Document doInBackground(String... urls){
            java.net.URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db;
                db = dbf.newDocumentBuilder();
                doc1 = db.parse(new InputSource(url.openStream()));
                doc1.getDocumentElement().normalize();
            } catch (Exception e){
                Toast.makeText(getBaseContext(),"Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc1;
        }

        @Override
        protected void onPostExecute(Document doc){
            String s = "";
            NodeList nodeList = doc.getElementsByTagName("item");
            Node node = nodeList.item(0);
            Element fstElmnt1 = (Element) node;
            NodeList fstName1 = fstElmnt1.getElementsByTagName("TYPE_NAME");
            Element fstElement1 = (Element)fstName1.item(0);
            fstName1 = fstElement1.getChildNodes();
            pregtype = ((Node) fstName1.item(0)).getNodeValue() + "";

            NodeList secName1 = fstElmnt1.getElementsByTagName("PROHBT_CONTENT");
            Element secElement1 = (Element)secName1.item(0);
            secName1 = secElement1.getChildNodes();
            prohibition = ((Node) secName1.item(0)).getNodeValue() + "";

            NodeList tidName1 = fstElmnt1.getElementsByTagName("INGR_ENG_NAME_FULL");
            Element tidElement1 = (Element)tidName1.item(0);
            tidName1 = tidElement1.getChildNodes();
            pregingr = ((Node) tidName1.item(0)).getNodeValue() + "";

            Log.d("라라라라1",pregtype+"//"+prohibition+"//"+pregingr);
            //Intent intent = new Intent(getBaseContext(),MainActivity_Result_DUR.class);
            //intent.putExtra("Pregtype",pregtype);
            //intent.putExtra("Prohibition",prohibition);
            //intent.putExtra("Pregingr",pregingr);
            //startActivity(intent);

        }
    }
    private class GetXMLTask2 extends AsyncTask<String, Void, Document>{
        private Activity context;
        public GetXMLTask2(Activity context){
            this.context = context;
        }
        @Override
        protected Document doInBackground(String... urls){
            java.net.URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db;
                db = dbf.newDocumentBuilder();
                doc1 = db.parse(new InputSource(url.openStream()));
                doc1.getDocumentElement().normalize();
            } catch (Exception e){
                Toast.makeText(getBaseContext(),"Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc1;
        }

        @Override
        protected void onPostExecute(Document doc){
            String s = "";
            NodeList nodeList = doc.getElementsByTagName("item");
            for (i=0; i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                Element fstElmnt2 = (Element) node;

                NodeList fstName2 = fstElmnt2.getElementsByTagName("MIXTURE_INGR_KOR_NAME");
                Element fstElement2 = (Element)fstName2.item(0);
                fstName2 = fstElement2.getChildNodes();
                joinkor = ((Node) fstName2.item(0)).getNodeValue() + "";

                NodeList secName2 = fstElmnt2.getElementsByTagName("MIXTURE_INGR_ENG_NAME");
                Element secElement2 = (Element)secName2.item(0);
                secName2 = secElement2.getChildNodes();
                joineng = ((Node) secName2.item(0)).getNodeValue() + "";

                NodeList tidName2 = fstElmnt2.getElementsByTagName("MIXTURE_CLASS_NAME");
                Element tidElement2 = (Element)tidName2.item(0);
                tidName2 = tidElement2.getChildNodes();
                joinclass = ((Node) tidName2.item(0)).getNodeValue() + "";

                NodeList forName2 = fstElmnt2.getElementsByTagName("PROHBT_CONTENT");
                Element forElement = (Element)forName2.item(0);
                forName2 = forElement.getChildNodes();
                joinpro = ((Node) forName2.item(0)).getNodeValue() + "";

                if (a==0||(joinkor.equals(k1[a-1])==false)){
                    k1[a]=joinkor;
                    k2[a]=joineng;
                    k3[a]=joinclass;
                    k4[a] = joinpro;
                    Log.d("콩콩콩콩22","i는:"+a+k1[a]+"//"+k2[a]+"//"+k3[a]+"//"+k4[a]);
                    a++;
                }
            }
            k1[a]=Integer.toString(100);  // 끝이라는 의미
            k2[a]=Integer.toString(100);
            k3[a]=Integer.toString(100);
            k4[a] = Integer.toString(100);
            //Intent intent = new Intent(getBaseContext(),MainActivity_Result_DUR.class);
            //intent.putExtra("Joinkor",joinkor);
            //intent.putExtra("Joineng",joineng);
            //intent.putExtra("Joinclass",joinclass);
            //intent.putExtra("Joinpro",joinpro);
            startActivity(intent);
        }
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