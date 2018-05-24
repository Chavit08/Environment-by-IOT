package com.example.chavit.projectenvnew;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.util.TimeZone;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import android.os.Environment;
import android.widget.Toast;

public class History extends AppCompatActivity {

    private ListView myListView;
    private MyDB Database = new MyDB(this);
    private Button buttonReport , buttondeleteHistory , sendemail;
    private ArrayList<HashMap<String, String>> DataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        myListView = (ListView)findViewById(R.id.listView);
        showHistory();

        buttonReport= (Button)findViewById(R.id.button2);
        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createPDF();
                Intent i = new Intent(getApplicationContext(),Report.class);
                startActivity(i);
                //ToastsMessagePDF();
                //finish();

            }
        });

        buttondeleteHistory= (Button)findViewById(R.id.button8);
        buttondeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.deleteAll();
                ToastsMessageDelete();
                finish();
            }
        });

        buttondeleteHistory= (Button)findViewById(R.id.button5);
        buttondeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendemail();
                finish();
            }
        });

    }

    public void showHistory(){

        DataList = Database.SelectData();
        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(this , DataList , R.layout.list_data,
                new String[]{"Date","Temp", "Humi", "Gas", "Dust", "Status", "Latitude","Longitude"},
                new int[]{R.id.IDTime,R.id.IDTemp, R.id.IDHumi, R.id.IDGas, R.id.IDDust, R.id.IDStatus, R.id.IDLatitude,R.id.IDLongitude});
        myListView.setAdapter(sAdap);

    }



    public void sendemail(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {});
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Report from Project Environment by iot" );
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is History");
        startActivity(shareIntent);
    }


    public void ToastsMessageDelete(){
        Toast.makeText(this, "ลบข้อมูลในฐานข้อมูลสำเร็จ", Toast.LENGTH_LONG).show();
    }


}





