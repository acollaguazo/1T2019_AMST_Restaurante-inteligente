package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class Estado extends AppCompatActivity {

    private PieChart pastel;
    private String[] estados = new String[]{"Mesas Ocupadas", "Mesas Libres"};
    private static int[] colores = new int[]{Color.rgb(216,96,70), Color.rgb(70,147,216)};
    private float[] porcentajes = new float[]{65,35}; // TEMPORAL
    private static ArrayList<TextView> edittexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);
        pastel = (PieChart)findViewById(R.id.pieChart1);
        crearPastel();
        for(int i = 0; i<Menu.NUMMESAS; i++){
            TextView tv = (TextView) findViewById(R.id.textView+i+1);
            tv.setText(Menu.entries.get(i));
        }


    }

    public void crearPastel(){
        pastel = (PieChart) customChart(pastel, Color.WHITE,2000);
        pastel.setHoleRadius(2);
        pastel.setHoleColor(Color.WHITE);
        pastel.setTransparentCircleRadius(6);
        pastel.setData(getPieData());
    }

    private Chart customChart(Chart chart, int backgroundColor, int animationTime){
        chart.getDescription().setTextSize(20);
        chart.getDescription().setText("");
        chart.setBackgroundColor(backgroundColor);
        chart.animateY(animationTime);
        customCharLegend(chart);
        return chart;
    }

    /**
     *
     * @param chart
     */
    private void customCharLegend(Chart chart){
        Legend leyenda = chart.getLegend();
        leyenda.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        leyenda.setFormSize(4);
        ArrayList<LegendEntry> entries = new ArrayList<>();
        for(int i=0; i<estados.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = colores[i];
            entry.label = estados[i];
            entries.add(entry);
        }
        leyenda.setCustom(entries);
    }

    /**
     *
     * @return
     */
    private PieData getPieData(){
        PieDataSet pieDataSet = (PieDataSet) getData(new PieDataSet(getPieEntries(),""));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);
    }

    /**
     *
     * @param dataset
     * @return
     */
    private DataSet getData(DataSet dataset){
        dataset.setColors(colores);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueTextSize(20);
        return dataset;
    }

    /**
     *
     * @return
     */
    private ArrayList<PieEntry> getPieEntries(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i=0; i<estados.length; i++)
            entries.add(new PieEntry(porcentajes[i]));
        return entries;
    }
}
