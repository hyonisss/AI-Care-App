package com.example.medicine_map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@SuppressLint("NewAPi")
public class MainActivity_Home extends AppCompatActivity {
    TextView Login_Info;
    ImageButton photofinder_Button,Calendar_Button,Sort_Button,Prescription_Button,Voice_Button;
    Document doc = null;
    int i=0;
    String pharname, telPhone,a,b,time, time1,pharAddr;
    int time2;
    String v1[] = new String[100];  // 약국
    String v2[] = new String[100];  // 전화번호
    double v3[] = new double[100];  // x 좌표
    double v4[] = new double[100];  // y
    int v5[] = new int[100];  // 공휴일날 여는지 안여는지 여부
    int v6[] = new int[100];  // 심야 약국인지 아닌지 여부
    String v7[] = new String[100];  // 약국 주소
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        photofinder_Button = findViewById(R.id.photofinder_Button);
        Calendar_Button = findViewById(R.id.Calendar_Button);
        Sort_Button = findViewById(R.id.Sort_Button);
        Prescription_Button = findViewById(R.id.Prescription_Button);
        Voice_Button = findViewById(R.id.Voice_Button);

        // TODO: 2020-02-09 지우기
        //Activity 받기 (ID password 확인하는창, 지워도됨)
        /*Login_Info = findViewById(R.id.Login_Info);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");
        String password = bundle.getString("password");
        Login_Info.setText(email + "/" + password);
*/

        //Camera로 이동
        photofinder_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Home.this, MainActivity_Camera.class);
                startActivity(intent);
            }
        });

        //Calender로 이동

        Calendar_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Home.this, MainActivity_Calendar.class);
                startActivity(intent);
            }
        });
        //Pharmacy로 이동

        /*Sort_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Home.this, MainActivity_Pharmacy.class);
                startActivity(intent);
            }
        });*/
        //Prescription로 이동

        Prescription_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Home.this, MainActivity_Prescription.class);
                startActivity(intent);
            }
        });

        //Voice로 이동

        Voice_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Home.this, MainActivity_Voice.class);
                startActivity(intent);
            }
        });
    }

/*    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem
                                                 item) {

        switch (item.getItemId()) {

            case R.id.action_setting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_mypage:
                Toast.makeText(this, "mypage", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void Listener4(View view){
        GetXMLTask task = new GetXMLTask(this);
        task.execute("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=88G0%2Ft488vBqKUN6ZFSu7NVjUpf8jZ9F3croTiMnbohG2hxfM38jg0ZaHXsBM2f2MRk7BVwmLZxSDqKe8zzh%2Bg%3D%3D&Q0=%EC%84%9C%EC%9A%B8%ED%8A%B9%EB%B3%84%EC%8B%9C&Q1=%EC%A2%85%EB%A1%9C%EA%B5%AC&ORD=ADDR&pageNo=1&numOfRows=35");
        // 어떤것이 중랑구, 종로구인지를 분간할 수 없음
    }
    // private inner class extending AsyncTask
    @SuppressLint("NewApi")
    private class GetXMLTask extends AsyncTask<String,Void,Document>{
        private Activity context;
        public GetXMLTask(Activity context){
            this.context = context;
        }
        @Override
        protected Document doInBackground(String... urls){
            URL url;
            try{
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db;

                db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
            }catch (Exception e){
                Toast.makeText(getBaseContext(),"Parsing Error",Toast.LENGTH_SHORT).show();
            }
            return doc;
        }
        @Override
        protected void onPostExecute(Document doc){
            String s = "";
            NodeList nodeList_zero = doc.getElementsByTagName("body");
            // 0. 약국 총 갯수
            Node node_zero = nodeList_zero.item(0);
            Element zeroElmnt = (Element) node_zero;
            NodeList zeroName = zeroElmnt.getElementsByTagName("totalCount");
            Element zeroElement = (Element)zeroName.item(0);
            zeroName = zeroElement.getChildNodes();
            int num = Integer.parseInt(((Node) zeroName.item(0)).getNodeValue() + "");
            Log.d("Num은",num+": 약국 갯수");


            NodeList nodeList = doc.getElementsByTagName("item");

            for (i=0; i<nodeList.getLength();i++){
                Node node = nodeList.item(i);
                // 1. 약국이름
                Element fstElmnt = (Element) node;

                NodeList fstName = fstElmnt.getElementsByTagName("dutyName");
                Element fstElement = (Element)fstName.item(0);
                fstName = fstElement.getChildNodes();
                pharname = ((Node) fstName.item(0)).getNodeValue() + "";
                v1[i] = pharname;
                // 2. 전화번호
                NodeList secName = fstElmnt.getElementsByTagName("dutyTel1");
                Element secElement = (Element)secName.item(0);
                secName = secElement.getChildNodes();
                telPhone = ((Node) secName.item(0)).getNodeValue() + "";
                v2[i] = telPhone;
                // 3. X 좌표
                NodeList tidName = fstElmnt.getElementsByTagName("wgs84Lat");
                Element tidElement = (Element)tidName.item(0);
                tidName = tidElement.getChildNodes();
                a = ((Node) tidName.item(0)).getNodeValue() + "";
                v3[i] = Double.parseDouble(a);
                // 4. Y 좌표
                NodeList forName = fstElmnt.getElementsByTagName("wgs84Lon");
                Element forElement = (Element)forName.item(0);
                forName = forElement.getChildNodes();
                b = ((Node) forName.item(0)).getNodeValue() + "";
                v4[i] = Double.parseDouble(b);

                // 5. 심야약국인지 아닌지 기준 : (( 보통 약국이 가장 오래 여는 시간인 월요일을 기준으로 9시 이상이 약국은 심야약국으로 정함 ))
                NodeList sixName = fstElmnt.getElementsByTagName("dutyTime1c");
                Element sixElement = (Element)sixName.item(0);
                sixName = sixElement.getChildNodes();
                time1 = ((Node) sixName.item(0)).getNodeValue() + "";
                time2 = Integer.parseInt(time1);
                if (time2>=2100){
                    Log.d("심야약국 : ",pharname+"시간은"+time2);
                    v6[i] = 1;
                }

                // 6. 공휴일인지 아닌지 여부
                NodeList fifName = fstElmnt.getElementsByTagName("dutyTime8s");
                if ((Element)fifName.item(0)!=null){
                    Element fifElement = (Element)fifName.item(0);
                    fifName = fifElement.getChildNodes();
                    time = ((Node) fifName.item(0)).getNodeValue() + "";
                    v5[i] = 1;
                }

                // 7. 주소를 받아오기
                NodeList sevName = fstElmnt.getElementsByTagName("dutyAddr");
                Element sevElement = (Element)sevName.item(0);
                sevName = sevElement.getChildNodes();
                pharAddr = ((Node) sevName.item(0)).getNodeValue() + "";
                v7[i] = pharAddr;
                Log.d("여기 주소 있는뎅뎅뎅뎅뎅", pharAddr);
            }
            v3[i]=-100;
            v4[i]=-100;
            Intent intent = new Intent(getBaseContext(), MainActivity_Maps.class);
            intent.putExtra("PharName",v1);
            intent.putExtra("Tel",v2);
            intent.putExtra("PosX",v3);
            intent.putExtra("PosY",v4);
            intent.putExtra("Holiday",v5);
            intent.putExtra("Nightday",v6);
            intent.putExtra("PharAddr", v7);
            startActivity(intent);

        }
    }

}