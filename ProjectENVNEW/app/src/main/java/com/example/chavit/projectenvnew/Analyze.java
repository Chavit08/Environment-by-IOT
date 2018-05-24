package com.example.chavit.projectenvnew;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Chavit on 20/3/2561.
 */

public class Analyze {
    public static int ID ;
    public static String Analyze(ArrayList<String> data, Context a) {
        int deathtime[] = {999, 999, 999,999};
        String status = "";
        String riskdata[] = {"","","",""};
        StringBuffer riskofhuman = new StringBuffer("");
        Integer temp = Integer.parseInt(data.get(0));
        Integer humi = Integer.parseInt(data.get(1));
        Integer gas = Integer.parseInt(data.get(2));
        Integer dust = Integer.parseInt(data.get(3));
        Double latitude = Double.parseDouble(data.get(4));
        Double longitude = Double.parseDouble(data.get(5));


        if (temp >= 32 && temp < 34&& humi >= 60 && humi <= 70 || temp >= 34 && temp < 36 && humi >= 50 && humi <= 55 || temp >= 36 && temp < 38 && humi >= 35 && humi < 40) {
            riskofhuman.append("อุณหภูมิและความชื้นอยู่ในระดับเกิดความเสี่ยง อาจเกิดตะคริวเนื่องจากความร้อน\n");
            riskdata[0] = "y";
            deathtime[0] = 999;
        } else if (temp >= 32 && temp < 34 && humi >= 70 && humi < 75 || temp >= 34 && temp < 36 && humi > 55 && humi <= 58 || temp >= 36 && temp < 38 && humi >= 40 && humi <= 45) {
            riskofhuman.append("อุณหภูมิและความชื้นอยู่ในระดับสูง อาจเกิดอาการขาดน้ำ และ ตะคริวจากความร้อน\n");
            riskdata[0] = "y";
            deathtime[0] = 999;
        }
        else if (temp >= 32 && humi >= 75 || temp >= 34 && humi >= 58 || temp >= 38) {
            riskofhuman.append("อุณหภูมิและความชื้นอยู่ในระดับอันตรายร้ายแรง อาจเป็นลมเนื่องจากความร้อนในร่างกายสูง\n");
            riskdata[0] = "r";
            deathtime[0] = 30;
        }
        else{
            riskdata[0] = "g";
        }

        if (humi <30){
            riskofhuman.append("ความชื้นน้อยกว่ามาตรฐานอาจทำให้เกิดอาการ ผิวแตก แสบตามผิดหนัง\n");
            riskdata[1] = "y";
            deathtime[1] = 999;
        }
        else if (humi >60){
            riskofhuman.append("ความชื้นเกินค่ามาตรฐานอาจทำให้เกิดอาการ เหนียวตัว อึดอัด ผู้ป่วยโรคหืดหอบและทางเดินหายใจควรหลีกเลี่ยงบริเวณนี้\n");
            riskdata[1] = "y";
            deathtime[1] = 999;
        }
        else {
            riskdata[1] = "g";
        }

        if (gas > 30 && gas < 1000) {
            riskofhuman.append("ระดับความเข้มข้นของแก๊สอยู่ในระดับเกิดความเสี่ยง อาจเกิดอาการมึนงง คลื่นใส้ ปวดศรีษะ อาเจียน ถ้าดมเป็นเวลานาน\n");
            riskdata[2] = "y";
            deathtime[2] = 999;
        } else if (gas > 1000) {
            riskofhuman.append("ระดับความเข้มข้นของแก๊สอยู่ในระดับเอันตราย อาจเกิดอาการขาดออกซิเจนในเลือด ได้แก่ หลอน หมดสติ ชัก หัวใจหยุดเต้น และตายได้\n");
            riskdata[0] = "r";
            deathtime[2] = 6;
        }
        else{
            riskdata[2] = "g";
        }

        if (dust > 120 && dust < 350) {
            riskofhuman.append("ฝุ่นในบริเวณอยู่ระดับเกิดความเสี่ยง ส่งผลต่อระบบทางเดินหายใจไม่ควรกิจกรรมบริเวณนี้เป็นเวลานาน\n");
            deathtime[3] = 999;
            riskdata[3] = "y";
        } else if (dust > 350 && dust < 420) {
            riskofhuman.append("ฝุ่นในบริเวณอยู่ในระดับเกิดความเสี่ยง ผู้ป่วยโรคระบบทางเดินหายใจ ควรหลีกเลี่ยงกิจกรรมภายนอกอาคาร บุคคลทั่วไป โดยเฉพาะเด็กและผู้สูงอายุ ควรจำกัดการออกกำลังภายนอกอาคาร\n");
            deathtime[3] = 999;
            riskdata[3] = "y";
        } else if (dust > 420) {
            riskofhuman.append("ฝุ่นในบริเวณอยู่ในระดับอันตราย ควรหลีกเลี่ยงการออกกำลังภายนอกอาคาร ผู้ป่วยโรคระบบทางเดินหายใจ ควรอยู่ภายในอาคาร อาจทำให้โรค หอบหืดเรื้อรัง ปอดอักเสบเรื้อรัง ไอเป็นเลือด โรคหลอดเลือดและหัวใจ\n");
            deathtime[3] = 4;
            riskdata[3] = "r";
        }
        else{
            riskdata[3] = "g";
        }

        Arrays.sort(deathtime);

        if (riskofhuman.toString().equals("") && deathtime[0] == 999) {
            status = "Safe";
        } else if (!riskofhuman.toString().equals("") && deathtime[0] == 999) {
            status = "Risky";
        } else  {
            status = "Hazard";
        }

        if (status.equals("Risky") || status.equals("Hazard")) {
            uploadDB(temp.toString(), humi.toString(), gas.toString(), dust.toString(), status, latitude.toString(), longitude.toString(), a);
            Log.i("Message", String.valueOf("บันทึกข้อมูล"));
            ID ++;

        }
        return riskofhuman.toString() + ":" + deathtime[0] + ":" + status + ":" + riskdata[0] + ":" + riskdata[1] + ":" + riskdata[2] + ":" + riskdata[3];

    }

    public static void uploadDB(String temp, String humi, String gas, String dust, String status, String lati, String longi, Context a) {
                    MyDB Database = new MyDB(a);
                    long millis = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                    String Date = sdf.format(millis);
                    Database.InData(Date, temp, humi, gas, dust, status, lati, longi);



    }
}

