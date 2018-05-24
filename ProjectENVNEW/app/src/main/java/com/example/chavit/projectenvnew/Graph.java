package com.example.chavit.projectenvnew;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Graph extends AppCompatActivity {
    private ArrayList<HashMap<String, String>> DataList;
    private ArrayList<String> datearray = new ArrayList<String>();
    private ArrayList<String> temparray = new ArrayList<String>();
    private ArrayList<String> humiarray = new ArrayList<String>();
    private ArrayList<String> gasarray = new ArrayList<String>();
    private ArrayList<String> dustarray = new ArrayList<String>();
    private ArrayList<Integer> sumtemp = new ArrayList<Integer>();
    private ArrayList<Integer> sumhumi = new ArrayList<Integer>();
    private ArrayList<Integer> sumgas = new ArrayList<Integer>();
    private ArrayList<Integer> sumdust = new ArrayList<Integer>();
    private LineChart ChartTemp,ChartHumi, ChartGas, ChartDust;
    private TextView lowtemp , hightemp , avgtemp, lowhumit, highhumit, avghumit, lowgast,highgast,avggast,lowdustt,highdustt,avgdustt;
    private MyDB Database = new MyDB(this);
    private RadioButton oneday , threeday , sevenday , allday;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ChartTemp = (LineChart) findViewById(R.id.graphTemp);
        ChartHumi = (LineChart) findViewById(R.id.graphhumi);
        ChartGas = (LineChart) findViewById(R.id.graphgas);
        ChartDust = (LineChart) findViewById(R.id.graphdust);
        lowtemp = (TextView) findViewById(R.id.lowtemp);
        hightemp = (TextView) findViewById(R.id.hightemp);
        avgtemp = (TextView) findViewById(R.id.avgtemp);
        lowhumit = (TextView) findViewById(R.id.lowhumi);
        highhumit = (TextView) findViewById(R.id.highhumi);
        avghumit = (TextView) findViewById(R.id.avghumi);
        lowgast = (TextView) findViewById(R.id.lowgas);
        highgast = (TextView) findViewById(R.id.highgas);
        avggast = (TextView) findViewById(R.id.avggas);
        lowdustt = (TextView) findViewById(R.id.lowdust);
        highdustt = (TextView) findViewById(R.id.highdust);
        avgdustt = (TextView) findViewById(R.id.avgdust);


        createGraphfirst();

        oneday= (RadioButton) findViewById(R.id.oneday);
        oneday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGraphsortday("1");
                Log.e("Message", String.valueOf("1"));
            }
        });
        threeday= (RadioButton) findViewById(R.id.threeday);
        threeday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGraphsortday("3");
                Log.e("Message", String.valueOf("3"));
            }
        });
        sevenday= (RadioButton) findViewById(R.id.sevenday);
        sevenday.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createGraphsortday("7");
            Log.e("Message", String.valueOf("7"));
        }
    });
        allday= (RadioButton) findViewById(R.id.allday);
        allday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGraphallday();
                Log.e("Message", String.valueOf("all"));
            }
        });
    }


    public void createGraphfirst(){
        DataList = Database.SelectDataAll();
        int rowcount = Database.getDataCount();
        int avgtemps = 0;
        int avghumi = 0;
        int avggas = 0;
        int avgdust = 0;
        for (int i=0 ; i< rowcount;i++) {
            datearray.add(DataList.get(i).get("Date"));
            temparray.add(DataList.get(i).get("Temp"));
            humiarray.add(DataList.get(i).get("Humi"));
            gasarray.add(DataList.get(i).get("Gas"));
            dustarray.add(DataList.get(i).get("Dust"));

        }
        for (int i=0 ; i< rowcount;i++) {
            sumtemp.add(Integer.parseInt(temparray.get(i)));
            sumhumi.add(Integer.parseInt(humiarray.get(i)));
            sumgas.add(Integer.parseInt(gasarray.get(i)));
            sumdust.add(Integer.parseInt(dustarray.get(i)));

            avgtemps += sumtemp.get(i);
            avghumi += sumhumi.get(i);
            avggas += sumgas.get(i);
            avgdust += sumdust.get(i);
        }

        settempgraph();
        sethumigraph();
        setgasgraph();
        setdustgraph();

        if (temparray.size() != 0 && humiarray.size() != 0 && gasarray.size() != 0 && dustarray.size() != 0) {
             creategraphTemp();
            creategraphHumi();
            creategraphGas();
            creategraphDust();

            Collections.sort(temparray);
            Collections.sort(humiarray);
            Collections.sort(sumgas);
            Collections.sort(sumdust);
            Double mintemp = Double.parseDouble(temparray.get(0));
            Double maxtemp = Double.parseDouble(temparray.get(temparray.size() - 1));
            Double minhumi = Double.parseDouble(humiarray.get(0));
            Double maxhumi = Double.parseDouble(humiarray.get(humiarray.size() - 1));
            Double mingas = Double.parseDouble(String.valueOf(sumgas.get(0)));
            Double maxgas = Double.parseDouble(String.valueOf(sumgas.get(sumgas.size() - 1)));
            Double mindust = Double.parseDouble(String.valueOf(sumdust.get(0)));
            Double maxdust = Double.parseDouble(String.valueOf(sumdust.get(sumdust.size() - 1)));
            lowtemp.setText("อุณหภูมิต่ำสุดอยู่ที่ : " + mintemp + " °C");
            hightemp.setText("อุณหภูมิสูงสุดอยู่ที่ : " + maxtemp + " °C");
            avgtemp.setText("อุณหภูมิเฉลี่ยอยู่ที่ : " + avgtemps/sumtemp.size() + " °C");
            lowhumit.setText("ความชื้นต่ำสุดอยู่ที่ : " + minhumi + " %");
            highhumit.setText("ความชื้นสูงสุดอยู่ที่ : " + maxhumi + " %");
            avghumit.setText("ความชื้นเฉลี่ยอยู่ที่ : " + avghumi/sumhumi.size() + " %");
            lowgast.setText("แก๊สต่ำสุดอยู่ที่ : " + mingas + " Ppm");
            highgast.setText("แก๊สสูงสุดอยู่ที่ : " + maxgas + " Ppm");
            avggast.setText("แก๊สเฉลี่ยอยู่ที่ : " + avggas/sumgas.size() + " Ppm");
            lowdustt.setText("ฝุ่นต่ำสุดอยู่ที่ : " + mindust + " ug");
            highdustt.setText("ฝุ่นสูงสุดอยู่ที่ : " + maxdust + " ug");
            avgdustt.setText("ฝุ่นเฉลี่ยอยู่ที่ : " + avgdust/sumdust.size() + " ug");
        }
        else{
            Toast.makeText(this, "ไม่มีข้อมูลในฐานข้อมูลในการสร้างกราฟ", Toast.LENGTH_LONG).show();
        }

    }

    public void createGraphsortday(String day){
        DataList.removeAll(DataList);
        int avgtemps = 0;
        int avghumi = 0;
        int avggas = 0;
        int avgdust = 0;
        datearray.clear();
        temparray.clear();
        humiarray.clear();
        gasarray.clear();
        dustarray.clear();
        sumtemp.clear();
        sumhumi.clear();
        sumgas.clear();
        sumdust.clear();
        DataList = Database.SelectData2(day);
        int rowcount = Database.getDataCount2(day);
        for (int i=0 ; i< rowcount;i++) {
            datearray.add(DataList.get(i).get("Date"));
            temparray.add(DataList.get(i).get("Temp"));
            humiarray.add(DataList.get(i).get("Humi"));
            gasarray.add(DataList.get(i).get("Gas"));
            dustarray.add(DataList.get(i).get("Dust"));

        }
        for (int i=0 ; i< rowcount;i++) {
            sumtemp.add(Integer.parseInt(temparray.get(i)));
            sumhumi.add(Integer.parseInt(humiarray.get(i)));
            sumgas.add(Integer.parseInt(gasarray.get(i)));
            sumdust.add(Integer.parseInt(dustarray.get(i)));

            avgtemps += sumtemp.get(i);
            avghumi += sumhumi.get(i);
            avggas += sumgas.get(i);
            avgdust += sumdust.get(i);
        }

        settempgraph();
        sethumigraph();
        setgasgraph();
        setdustgraph();
        if (temparray.size() != 0 && humiarray.size() != 0 && gasarray.size() != 0 && dustarray.size() != 0) {
            creategraphTemp1(day);
            creategraphHumi1(day);
            creategraphGas1(day);
            creategraphDust1(day);

            Collections.sort(temparray);
            Collections.sort(humiarray);
            Collections.sort(sumgas);
            Collections.sort(sumdust);
            Double mintemp = Double.parseDouble(temparray.get(0));
            Double maxtemp = Double.parseDouble(temparray.get(temparray.size() - 1));
            Double minhumi = Double.parseDouble(humiarray.get(0));
            Double maxhumi = Double.parseDouble(humiarray.get(humiarray.size() - 1));
            Double mingas = Double.parseDouble(String.valueOf(sumgas.get(0)));
            Double maxgas = Double.parseDouble(String.valueOf(sumgas.get(sumgas.size() - 1)));
            Double mindust = Double.parseDouble(String.valueOf(sumdust.get(0)));
            Double maxdust = Double.parseDouble(String.valueOf(sumdust.get(sumdust.size() - 1)));
            lowtemp.setText("อุณหภูมิต่ำสุดอยู่ที่ : " + mintemp + " °C");
            hightemp.setText("อุณหภูมิสูงสุดอยู่ที่ : " + maxtemp + " °C");
            avgtemp.setText("อุณหภูมิเฉลี่ยอยู่ที่ : " + avgtemps/sumtemp.size() + " °C");
            lowhumit.setText("ความชื้นต่ำสุดอยู่ที่ : " + minhumi + " %");
            highhumit.setText("ความชื้นสูงสุดอยู่ที่ : " + maxhumi + " %");
            avghumit.setText("ความชื้นเฉลี่ยอยู่ที่ : " + avghumi/sumhumi.size() + " %");
            lowgast.setText("แก๊สต่ำสุดอยู่ที่ : " + mingas + " Ppm");
            highgast.setText("แก๊สสูงสุดอยู่ที่ : " + maxgas + " Ppm");
            avggast.setText("แก๊สเฉลี่ยอยู่ที่ : " + avggas/sumgas.size() + " Ppm");
            lowdustt.setText("ฝุ่นต่ำสุดอยู่ที่ : " + mindust + " ug");
            highdustt.setText("ฝุ่นสูงสุดอยู่ที่ : " + maxdust + " ug");
            avgdustt.setText("ฝุ่นเฉลี่ยอยู่ที่ : " + avgdust/sumdust.size() + " ug");
        }
        else{
            Toast.makeText(getApplicationContext(), "ไม่มีข้อมูลในฐานข้อมูลในการสร้างกราฟ", Toast.LENGTH_LONG).show();
        }
        Log.e("Message", String.valueOf("All day"));

    }

    public void createGraphallday(){
        DataList.removeAll(DataList);
        int avgtemps = 0;
        int avghumi = 0;
        int avggas = 0;
        int avgdust = 0;
        datearray.clear();
        temparray.clear();
        humiarray.clear();
        gasarray.clear();
        dustarray.clear();
        sumtemp.clear();
        sumhumi.clear();
        sumgas.clear();
        sumdust.clear();
        DataList = Database.SelectDataAll();
        int rowcount = Database.getDataCount();
        for (int i=0 ; i< rowcount;i++) {
            datearray.add(DataList.get(i).get("Date"));
            temparray.add(DataList.get(i).get("Temp"));
            humiarray.add(DataList.get(i).get("Humi"));
            gasarray.add(DataList.get(i).get("Gas"));
            dustarray.add(DataList.get(i).get("Dust"));
        }

        for (int i=0 ; i< rowcount;i++) {
            sumtemp.add(Integer.parseInt(temparray.get(i)));
            sumhumi.add(Integer.parseInt(humiarray.get(i)));
            sumgas.add(Integer.parseInt(gasarray.get(i)));
            sumdust.add(Integer.parseInt(dustarray.get(i)));

            avgtemps += sumtemp.get(i);
            avghumi += sumhumi.get(i);
            avggas += sumgas.get(i);
            avgdust += sumdust.get(i);
        }

        settempgraph();
        sethumigraph();
        setgasgraph();
        setdustgraph();
        if (temparray.size() != 0 && humiarray.size() != 0 && gasarray.size() != 0 && dustarray.size() != 0) {
            creategraphTemp();
            creategraphHumi();
            creategraphGas();
            creategraphDust();

            Collections.sort(temparray);
            Collections.sort(humiarray);
            Collections.sort(sumgas);
            Collections.sort(sumdust);
            Double mintemp = Double.parseDouble(temparray.get(0));
            Double maxtemp = Double.parseDouble(temparray.get(temparray.size() - 1));
            Double minhumi = Double.parseDouble(humiarray.get(0));
            Double maxhumi = Double.parseDouble(humiarray.get(humiarray.size() - 1));
            Double mingas = Double.parseDouble(String.valueOf(sumgas.get(0)));
            Double maxgas = Double.parseDouble(String.valueOf(sumgas.get(sumgas.size() - 1)));
            Double mindust = Double.parseDouble(String.valueOf(sumdust.get(0)));
            Double maxdust = Double.parseDouble(String.valueOf(sumdust.get(sumdust.size() - 1)));
            lowtemp.setText("อุณหภูมิต่ำสุดอยู่ที่ : " + mintemp + " °C");
            hightemp.setText("อุณหภูมิสูงสุดอยู่ที่ : " + maxtemp + " °C");
            avgtemp.setText("อุณหภูมิเฉลี่ยอยู่ที่ : " + avgtemps/sumtemp.size() + " °C");
            lowhumit.setText("ความชื้นต่ำสุดอยู่ที่ : " + minhumi + " %");
            highhumit.setText("ความชื้นสูงสุดอยู่ที่ : " + maxhumi + " %");
            avghumit.setText("ความชื้นเฉลี่ยอยู่ที่ : " + avghumi/sumhumi.size() + " %");
            lowgast.setText("แก๊สต่ำสุดอยู่ที่ : " + mingas + " Ppm");
            highgast.setText("แก๊สสูงสุดอยู่ที่ : " + maxgas + " Ppm");
            avggast.setText("แก๊สเฉลี่ยอยู่ที่ : " + avggas/sumgas.size() + " Ppm");
            lowdustt.setText("ฝุ่นต่ำสุดอยู่ที่ : " + mindust + " ug");
            highdustt.setText("ฝุ่นสูงสุดอยู่ที่ : " + maxdust + " ug");
            avgdustt.setText("ฝุ่นเฉลี่ยอยู่ที่ : " + avgdust/sumdust.size() + " ug");
        }
        else{
            Toast.makeText(getApplicationContext(), "ไม่มีข้อมูลในฐานข้อมูลในการสร้างกราฟ", Toast.LENGTH_LONG).show();
        }
        Log.e("Message", String.valueOf("All day"));

    }

    public void settempgraph(){
        LineData data = new LineData();
        ChartTemp.getDescription().setEnabled(true);
        ChartTemp.setTouchEnabled(true);
        ChartTemp.setDragEnabled(true);
        ChartTemp.setScaleEnabled(true);
        ChartTemp.setDrawGridBackground(false);
        ChartTemp.setPinchZoom(true);
        ChartTemp.setBackgroundColor(Color.WHITE);
        ChartTemp.setData(data);
        data.setValueTextColor(Color.BLUE);
        Legend l = ChartTemp.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.BLUE);
        XAxis xl = ChartTemp.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(Color.RED);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
        YAxis leftAxis = ChartTemp.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);
        xl.setLabelCount(5, true);
        leftAxis.setLabelCount(5, true);
        YAxis rightAxis = ChartTemp.getAxisRight();
        rightAxis.setEnabled(false);

        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return datearray.get((int)value);
            }
        });
        xl.setLabelRotationAngle(50f);

    }
    public void sethumigraph(){
        ChartHumi.getDescription().setEnabled(true);
        ChartHumi.setTouchEnabled(true);
        ChartHumi.setDragEnabled(true);
        ChartHumi.setScaleEnabled(true);
        ChartHumi.setDrawGridBackground(false);
        ChartHumi.setPinchZoom(true);
        ChartHumi.setBackgroundColor(Color.WHITE);
        LineData data = new LineData();
        ChartHumi.setData(data);
        data.setValueTextColor(Color.BLUE);
        Legend l = ChartHumi.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.BLUE);
        YAxis leftAxis = ChartHumi.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);
        YAxis rightAxis = ChartHumi.getAxisRight();
        rightAxis.setEnabled(false);
        XAxis xl = ChartHumi.getXAxis();
        xl.setLabelRotationAngle(50f);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(Color.RED);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawGridLines(false);
        xl.setLabelCount(5, true);
        leftAxis.setLabelCount(5, true);
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return  datearray.get((int)value);
            }
        });
    }
    public void setgasgraph(){
        ChartGas.getDescription().setEnabled(true);
        ChartGas.setTouchEnabled(true);
        ChartGas.setDragEnabled(true);
        ChartGas.setScaleEnabled(true);
        ChartGas.setDrawGridBackground(false);
        ChartGas.setPinchZoom(true);
        ChartGas.setBackgroundColor(Color.WHITE);
        LineData data = new LineData();
        ChartGas.setData(data);
        data.setValueTextColor(Color.BLUE);
        Legend l = ChartGas.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.BLUE);
        XAxis xl = ChartGas.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(Color.RED);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
        YAxis leftAxis = ChartGas.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(5, true);
        xl.setLabelCount(5, true);
        YAxis rightAxis = ChartGas.getAxisRight();
        rightAxis.setEnabled(false);
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return  datearray.get((int)value);
            }
        });
        xl.setLabelRotationAngle(50f);

    }
    public void setdustgraph(){
        ChartDust.getDescription().setEnabled(true);
        ChartDust.setTouchEnabled(true);
        ChartDust.setDragEnabled(true);
        ChartDust.setScaleEnabled(true);
        ChartDust.setDrawGridBackground(false);
        ChartDust.setPinchZoom(true);
        ChartDust.setBackgroundColor(Color.WHITE);
        LineData data = new LineData();
        ChartDust.setData(data);
        data.setValueTextColor(Color.BLUE);
        Legend l = ChartDust.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.BLUE);
        YAxis leftAxis = ChartDust.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(5, true);
        YAxis rightAxis = ChartDust.getAxisRight();
        rightAxis.setEnabled(false);
        XAxis xl = ChartDust.getXAxis();
        xl.setLabelCount(5, true);
        xl.setLabelRotationAngle(50f);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(Color.RED);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawGridLines(false);
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return  datearray.get((int)value);
            }
        });
        xl.setLabelRotationAngle(50f);
    }

    public void creategraphTemp() {
        LineData data = ChartTemp.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createTempSet();
                data.addDataSet(set);
            }
            int rowcount = Database.getDataCount();
            for (int i = 0; i < rowcount; i++) {
                data.addEntry(new Entry(set.getEntryCount(), Float.valueOf(temparray.get(i))), 0);

            }
            data.notifyDataChanged();
            ChartTemp.notifyDataSetChanged();
            ChartTemp.setVisibleXRangeMaximum(120);
            ChartTemp.moveViewToX(data.getEntryCount());
        }
    }
    public void creategraphHumi() {

        LineData data = ChartHumi.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createHumiSet();
                data.addDataSet(set);
            }
            int rowcount = Database.getDataCount();
            for (int i=0 ; i< rowcount;i++) {
                data.addEntry(new Entry(set.getEntryCount(), Float.valueOf(humiarray.get(i))), 0);
                }
            data.notifyDataChanged();
            ChartHumi.notifyDataSetChanged();
            ChartHumi.setVisibleXRangeMaximum(120);
            ChartHumi.moveViewToX(data.getEntryCount());

        }
    }
    public void creategraphGas() {

        LineData data = ChartGas.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = creategasSet();
                data.addDataSet(set);
            }
            int rowcount = Database.getDataCount();

                for (int i = 0; i < rowcount; i++) {
                    data.addEntry(new Entry(set.getEntryCount(), Float.valueOf(gasarray.get(i))), 0);

                }

            data.notifyDataChanged();
            ChartGas.notifyDataSetChanged();
            ChartGas.setVisibleXRangeMaximum(120);
            ChartGas.moveViewToX(data.getEntryCount());

        }
    }
    public void creategraphDust() {

        LineData data = ChartDust.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createdustSet();
                data.addDataSet(set);
            }
            int rowcount = Database.getDataCount();

            for (int i=0 ; i< rowcount;i++) {
                data.addEntry(new Entry(set.getEntryCount(), Float.valueOf(dustarray.get(i))), 0);
            }

            data.notifyDataChanged();
            ChartDust.notifyDataSetChanged();
            ChartDust.setVisibleXRangeMaximum(120);
            ChartDust.moveViewToX(data.getEntryCount());

        }
    }

    public void creategraphTemp1(String day) {
        LineData data = ChartTemp.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createTempSet();
                data.addDataSet(set);
            }
            int rowcount = Database.getDataCount2(day);
            for (int i = 0; i < rowcount; i++) {
                data.addEntry(new Entry(set.getEntryCount(), Float.valueOf(temparray.get(i))), 0);

            }
            data.notifyDataChanged();
            ChartTemp.notifyDataSetChanged();
            ChartTemp.setVisibleXRangeMaximum(120);
            ChartTemp.moveViewToX(data.getEntryCount());
        }
    }
    public void creategraphHumi1(String day) {

        LineData data = ChartHumi.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createHumiSet();
                data.addDataSet(set);
            }
            int rowcount = Database.getDataCount2(day);
            for (int i=0 ; i< rowcount;i++) {
                data.addEntry(new Entry(set.getEntryCount(), Float.valueOf(humiarray.get(i))), 0);
            }
            data.notifyDataChanged();
            ChartHumi.notifyDataSetChanged();
            ChartHumi.setVisibleXRangeMaximum(120);
            ChartHumi.moveViewToX(data.getEntryCount());

        }
    }
    public void creategraphGas1(String day) {

        LineData data = ChartGas.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = creategasSet();
                data.addDataSet(set);
            }
            int rowcount = Database.getDataCount2(day);
            for (int i = 0; i < rowcount; i++) {
                data.addEntry(new Entry(set.getEntryCount(), Float.valueOf(gasarray.get(i))), 0);
            }
            data.notifyDataChanged();
            ChartGas.notifyDataSetChanged();
            ChartGas.setVisibleXRangeMaximum(120);
            ChartGas.moveViewToX(data.getEntryCount());

        }
    }
    public void creategraphDust1(String day) {

        LineData data = ChartDust.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createdustSet();
                data.addDataSet(set);
            }
            int rowcount = Database.getDataCount2(day);
            for (int i=0 ; i< rowcount;i++) {
                data.addEntry(new Entry(set.getEntryCount(), Float.valueOf(dustarray.get(i))), 0);
            }
            data.notifyDataChanged();
            ChartDust.notifyDataSetChanged();
            ChartDust.setVisibleXRangeMaximum(120);
            ChartDust.moveViewToX(data.getEntryCount());

        }
    }

    public LineDataSet createTempSet() {

        LineDataSet set = new LineDataSet(null, "Temp Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.RED);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(15f);
        set.setDrawValues(false);
        return set;
    }
    public LineDataSet createHumiSet() {

        LineDataSet set = new LineDataSet(null, "Humi Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.RED);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(15f);
        set.setDrawValues(false);
        return set;
    }
    public LineDataSet creategasSet() {

        LineDataSet set = new LineDataSet(null, "Gas Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.RED);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(15f);
        set.setDrawValues(false);
        return set;
    }
    public  LineDataSet createdustSet() {

        LineDataSet set = new LineDataSet(null, "Dust Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.RED);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(15f);
        set.setDrawValues(false);
        return set;
    }
}
