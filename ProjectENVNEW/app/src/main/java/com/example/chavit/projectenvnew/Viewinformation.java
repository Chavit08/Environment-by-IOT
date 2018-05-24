package com.example.chavit.projectenvnew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.itextpdf.text.pdf.PdfPTable;

import java.util.ArrayList;

import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class Viewinformation extends AppCompatActivity {
    private TextView myTextTemp,myTextHum,myTextGas,myTextdust,myRiskofhuman,myDeathtime,myRiskofarea,myStatus,temparea,humiarea,gasarea,dustarea;
    private String information,strtemp,strhum,strgas,strdust,strlat,strlong;
    StringBuffer notify = new StringBuffer("");
    private double latitude,longitude;
    private Microgear microgear = new Microgear(this);
    private String message0 = "ปลอดภัย";
    private String message1 = "เกิดความเสี่ยง";
    private String message2 = "อันตราย";
    private String appid = "PJenv"; //APP_ID
    private String key = "XTvPvQlgiM2aR2N"; //KEY
    private String secret = "BWp2u3UxhISEMi5SZcBSzgDOc"; //SECRET
    private String alias = "Android";
    private ArrayList<String> fourData = new ArrayList<String>();
    MicrogearCallBack callback = new MicrogearCallBack();


    Handler handler = new Handler() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            strtemp = bundle.getString("Temp");
            strhum = bundle.getString("Hum");
            strgas = bundle.getString("Gas");
            strdust = bundle.getString("Dust");
            strlat = bundle.getString("Lat");
            strlat = bundle.getString("Lat");
            strlong = bundle.getString("Long");

            if (strtemp != null) {
                //int t = Double.parseDouble(strtemp);
                myTextTemp.setText(strtemp);
                fourData.add(strtemp);
            }
            if (strhum != null) {
                //int t = Double.parseDouble(strtemp);
                myTextHum.setText(strhum);
                fourData.add(strhum);

            }
            if (strgas != null) {
                //int t = Double.parseDouble(strtemp);
                myTextGas.setText(strgas);
                fourData.add(strgas);
                //AnalyzeText.setText(information);
            }
            if (strdust != null) {
                //int t = Double.parseDouble(strtemp);
                myTextdust.setText(strdust);
                fourData.add(strdust);

            }
            if (strlat != null) {
                latitude = Double.parseDouble(strlat);
                fourData.add(strlat);

            }
            if (strlong != null) {
                longitude = Double.parseDouble(strlong);
                fourData.add(strlong);
            }

            if (fourData.size() == 6) {
                information = Analyze.Analyze(fourData,getApplicationContext());
                Log.i("Message", String.valueOf(information));
                String[] informations = information.toString().split(":");
                myRiskofhuman.setText(informations[0]);
                if (informations[1].equals("999")) {
                    myDeathtime.setText("0");
                }
                else{
                    myDeathtime.setText(informations[1]);
                }



                if (informations[3].equals("g")){
                    notify.append("");
                    temparea.setBackgroundResource(R.color.Green);
                }
                if (informations[4].equals("g")){
                    notify.append("");
                    humiarea.setBackgroundResource(R.color.Green);
                }
                if (informations[5].equals("g")){
                    notify.append("");
                    gasarea.setBackgroundResource(R.color.Green);
                }
                if (informations[6].equals("g")){
                    notify.append("");
                    dustarea.setBackgroundResource(R.color.Green);
                }

                if (informations[3].equals("y")){
                    notify.append("เกิดความเสี่ยงจากความร้อนอยู่ที่ระดับ "+fourData.get(0)+ " °C:");
                    temparea.setBackgroundResource(R.color.Yellow);
                }
                if (informations[4].equals("y")){
                    notify.append("เกิดความเสี่ยงจากความชื้นอยู่ที่ระดับ "+fourData.get(1)+ " %:");
                    humiarea.setBackgroundResource(R.color.Yellow);
                }
                if (informations[5].equals("y")){
                    notify.append("เกิดความเสี่ยงจากแก๊สอยู่ที่ระดับ "+fourData.get(2)+ " Ppm:");
                    gasarea.setBackgroundResource(R.color.Yellow);
                }
                if (informations[6].equals("y")){
                    notify.append("เกิดความเสี่ยงจากฝุ่นอยู่ที่ระดับ "+fourData.get(3)+ " µg:");
                    dustarea.setBackgroundResource(R.color.Yellow);
                }
                if(informations[3].equals("r")){
                    notify.append("เกิดอันตรายจากความร้อนอยู่ที่ระดับ "+fourData.get(0)+ " °C:");
                    temparea.setBackgroundResource(R.color.Red);
                }
                if (informations[5].equals("r")){
                    notify.append("เกิดอันตรายจากแก๊สอยู่ที่ระดับ "+fourData.get(2)+ " Ppm:");
                    gasarea.setBackgroundResource(R.color.Red);
                }
                if (informations[6].equals("r")){
                    notify.append("เกิดอันตรายจากฝุ่นอยู่ที่ระดับ "+fourData.get(3)+ " µg:");
                    dustarea.setBackgroundResource(R.color.Red);
                }

                if (informations[2].equals("Safe"))
                {
                    NotifyArea.showNotification(message0,notify,getApplicationContext());
                    myStatus.setText("Safe");
                    myRiskofarea.setBackgroundResource(R.color.Green);

                }
                else if (informations[2].equals("Risky")){
                    NotifyArea.showNotification(message1,notify,getApplicationContext());
                    myStatus.setText("Risky");
                    myRiskofarea.setBackgroundResource(R.color.Yellow);
                }
                else{
                    NotifyArea.showNotification(message2,notify,getApplicationContext());
                    myStatus.setText("Hazard");
                    myRiskofarea.setBackgroundResource(R.color.Red);

                }
                notify.setLength(0);
                fourData.clear();
                Log.i("Message", String.valueOf("ส่งข้อมูลไปวิเคราห์แล้ว"));
            }
            else{

            }


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        microgear.connect(appid, key, secret, alias);
        microgear.setCallback(callback);
        microgear.subscribe("/Temp");
        microgear.subscribe("/Hum");
        microgear.subscribe("/Gas");
        microgear.subscribe("/Dust");
        microgear.subscribe("/Lat");
        microgear.subscribe("/Long");
        setContentView(R.layout.activity_information);
        myTextTemp = findViewById(R.id.vtemp);
        myTextGas = findViewById(R.id.vgas);
        myTextHum = findViewById(R.id.vhumi);
        myTextdust = findViewById(R.id.vdust);
        myRiskofhuman = findViewById(R.id.textView);
        myDeathtime = findViewById(R.id.deathtime);
        myRiskofarea = findViewById(R.id.quality);
        myStatus = findViewById(R.id.textView10);
        temparea = findViewById(R.id.tarea);
        humiarea = findViewById(R.id.harea);
        gasarea = findViewById(R.id.garea);
        dustarea = findViewById(R.id.darea);

        //final EditText txtName = (EditText)findViewById(R.id.editText1);
        //String value = txtName.getText().toString();
        //showMsg.setText("Your Name:"+value);
    }


    protected void onDestroy() {
        super.onDestroy();
        microgear.disconnect();
    }
    protected void onResume() {
        super.onResume();
        microgear.bindServiceResume();
    }

    public void vmap(View v){
        Intent i = new Intent(Viewinformation.this,Maps.class);
        i.putExtra("Latitude", latitude);
        i.putExtra("Longitude", longitude);
        startActivity(i);
    }

    public void vgraph(View v){
        Intent i = new Intent(getApplicationContext(),Graph.class);
        startActivity(i);
    }

    public void vhistory(View v){
        Intent i = new Intent(getApplicationContext(),History.class);
        startActivity(i);
    }




    class MicrogearCallBack implements MicrogearEventListener{
        @Override
        public void onConnect() {
            Log.i("Connected","Now I'm connected with netpie");
            Toast.makeText(getApplicationContext(), "เชื่อมต่อ Netpie สำเร็จ", Toast.LENGTH_LONG).show();
    }
        @Override
        public void onMessage(String topic, String message) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            if( topic.equals("/PJenv/Temp")){
                bundle.putString("Temp", message);
            }
            else if( topic.equals("/PJenv/Hum")){
                bundle.putString("Hum", message);
            }
            else if( topic.equals("/PJenv/Gas")){
                bundle.putString("Gas", message);
            }
            else if( topic.equals("/PJenv/Dust")){
                bundle.putString("Dust", message);
            }
            else if( topic.equals("/PJenv/Lat")){
                bundle.putString("Lat", message);
            }
            else if( topic.equals("/PJenv/Long")){
                bundle.putString("Long", message);
            }
            else {

            }
            msg.setData(bundle);

            handler.sendMessage(msg);
            Log.i("Message",topic+" : "+message);
        }
        @Override
        public void onPresent(String token) {
            Log.i("present","New friend Connect :"+token);
        }
        @Override
        public void onAbsent(String token) {
            Log.i("absent","Friend lost :"+token);
        }
        @Override
        public void onDisconnect() {
            Log.i("disconnect","Disconnected");
        }
        @Override
        public void onError(String error) {
            Log.i("exception","Exception : "+error);
        }
        @Override
        public void onInfo(String info) {
            Log.i("info","Info : "+info);
        }
    }
}
