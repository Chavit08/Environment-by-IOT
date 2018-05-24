package com.example.chavit.projectenvnew;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.TimeZone;

public class Report extends AppCompatActivity {
    public static String dateTime ;
    private MyDB Database = new MyDB(this);
    private ArrayList<HashMap<String, String>> DataList;
    private Button report;
    private ArrayList<Integer> sumtemp = new ArrayList<Integer>();
    private ArrayList<Integer> sumhumi = new ArrayList<Integer>();
    private ArrayList<Integer> sumgas = new ArrayList<Integer>();
    private ArrayList<Integer> sumdust = new ArrayList<Integer>();
    private ArrayList<String> temparray = new ArrayList<String>();
    private ArrayList<String> humiarray = new ArrayList<String>();
    private ArrayList<String> gasarray = new ArrayList<String>();
    private ArrayList<String> dustarray = new ArrayList<String>();
    private RadioButton oneday , threeday , sevenday , onemonth , threemonth , sixmonth , oneyears;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        report= (Button)findViewById(R.id.buttonreport);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createPDF();
                if(oneday.isChecked())
                {
                    createPDF("1");
                    Log.e("Message", String.valueOf("1"));
                }
                else if (threeday.isChecked())
                {
                    createPDF("3");
                    Log.e("Message", String.valueOf("3"));
                }
                else if (sevenday.isChecked())
                {
                    createPDF("7");
                    Log.e("Message", String.valueOf("7"));
                }
                else if (onemonth.isChecked())
                {
                    createPDF("30");
                    Log.e("Message", String.valueOf("30"));
                }
                else if (threemonth.isChecked())
                {
                    createPDF("90");
                    Log.e("Message", String.valueOf("90"));
                }
                else if (sixmonth.isChecked())
                {
                    createPDF("180");
                    Log.e("Message", String.valueOf("180"));
                }
                else if (oneyears.isChecked())
                {
                    createPDF("365");
                    Log.e("Message", String.valueOf("365"));
                }


                finish();

            }
        });

        oneday = (RadioButton) findViewById(R.id.day1);
        oneday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        threeday = (RadioButton) findViewById(R.id.day3);
        threeday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        sevenday = (RadioButton) findViewById(R.id.day7);
        sevenday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        onemonth = (RadioButton) findViewById(R.id.month1);
        onemonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        threemonth = (RadioButton) findViewById(R.id.month3);
        threemonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        sixmonth = (RadioButton) findViewById(R.id.month6);
        sixmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        oneyears = (RadioButton) findViewById(R.id.yearones);
        oneyears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    public void createPDF(String day){
        DataList = Database.SelectData2(day);
        int rowcount = Database.getDataCount2(day);
        int avgtemps = 0;
        int avghumis = 0;
        int avggass = 0;
        int avgdusts = 0;
        Document doc = new Document();
        long millis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy-HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        dateTime = sdf.format(millis);

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RePortPDF";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "Report "+dateTime+".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);
            doc.open();
            Paragraph p1 = new Paragraph("History of Environment by IOT\n ");
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p1);
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.2f, 1.0f, 0.7f,0.6f,0.6f,0.9f,0.9f,0.7f});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Date & time");
            table.addCell("Temperature");
            table.addCell("Humidity");
            table.addCell("Gas");
            table.addCell("Dust");
            table.addCell("Latitude");
            table.addCell("Longitude");
            table.addCell("Status");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }

            for (int i = 0 ; i <  rowcount ;i++ ) {

                table.addCell(String.valueOf(DataList.get(i).get("Date")));
                table.addCell(String.valueOf(DataList.get(i).get("Temp")));
                table.addCell(String.valueOf(DataList.get(i).get("Humi")));
                table.addCell(String.valueOf(DataList.get(i).get("Gas")));
                table.addCell(String.valueOf(DataList.get(i).get("Dust")));
                table.addCell(String.valueOf(DataList.get(i).get("Latitude")));
                table.addCell(String.valueOf(DataList.get(i).get("Longitude")));
                table.addCell(String.valueOf(DataList.get(i).get("Status")));


                temparray.add(DataList.get(i).get("Temp"));
                humiarray.add(DataList.get(i).get("Humi"));
                gasarray.add(DataList.get(i).get("Gas"));
                dustarray.add(DataList.get(i).get("Dust"));

                sumtemp.add(Integer.parseInt(temparray.get(i)));
                sumhumi.add(Integer.parseInt(humiarray.get(i)));
                sumgas.add(Integer.parseInt(gasarray.get(i)));
                sumdust.add(Integer.parseInt(dustarray.get(i)));

                avgtemps += sumtemp.get(i);
                avghumis += sumhumi.get(i);
                avggass += sumgas.get(i);
                avgdusts += sumdust.get(i);


            }
            doc.add(table);
            Collections.sort(sumtemp);
            Collections.sort(sumhumi);
            Collections.sort(sumgas);
            Collections.sort(sumdust);
            Paragraph p21 = new Paragraph("Temperature Data");
            Paragraph p2 = new Paragraph("Low Temperature : " + sumtemp.get(0));
            Paragraph p3 = new Paragraph("Max Temperature : " + sumtemp.get(sumtemp.size() - 1));
            Paragraph p4 = new Paragraph("Avg Temperature : " + avgtemps/sumtemp.size());
            Paragraph p22 = new Paragraph("Humidity Data");
            Paragraph p5 = new Paragraph("Low Humidity : " + sumhumi.get(0)) ;
            Paragraph p6 = new Paragraph("Max Humidity : " + sumhumi.get(sumhumi.size() - 1)) ;
            Paragraph p7 = new Paragraph("Avg Humidity : " + avghumis/sumhumi.size());
            Paragraph p23 = new Paragraph("Gas Data");
            Paragraph p8 = new Paragraph("Low Gas : " + sumgas.get(0) + "");
            Paragraph p9 = new Paragraph("Max Gas : " + sumgas.get(sumgas.size() - 1));
            Paragraph p10 = new Paragraph("Avg Gas : " + avggass/sumgas.size());
            Paragraph p24 = new Paragraph("Dust Data");
            Paragraph p11 = new Paragraph("Low Dust : " + sumdust.get(0));
            Paragraph p12 = new Paragraph("Max Dust : " + sumdust.get(sumdust.size() - 1));
            Paragraph p13 = new Paragraph("Avg Dust : " + avgdusts/sumdust.size());
            p21.setAlignment(Paragraph.ALIGN_CENTER);
            p22.setAlignment(Paragraph.ALIGN_CENTER);
            p23.setAlignment(Paragraph.ALIGN_CENTER);
            p24.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p21);
            doc.add(p2);
            doc.add(p3);
            doc.add(p4);
            doc.add(p22);
            doc.add(p5);
            doc.add(p6);
            doc.add(p7);
            doc.add(p23);
            doc.add(p8);
            doc.add(p9);
            doc.add(p10);
            doc.add(p24);
            doc.add(p11);
            doc.add(p12);
            doc.add(p13);



        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
        }

    }

    public void ToastsMessagePDF(){
        Toast.makeText(this, "ออกรายงานประวัติการใช้งานสำเร็จ", Toast.LENGTH_LONG).show();
    }
}
