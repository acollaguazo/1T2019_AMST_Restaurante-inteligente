package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private static int[] colores = new int[]{Color.rgb(216, 96, 70), Color.rgb(70, 147, 216)};
    private float[] porcentajes = new float[2]; // TEMPORAL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);
        pastel = (PieChart) findViewById(R.id.pieChart1);
        calcularPorcentajeMesas(porcentajes);
        crearPastel();
        llenarScrolling(Menu.entries.size());
    }


    /**
     * METODO SOBREESCRITO: Toma la acción del boton de regresar del telefono y se encarga de llamar al método para eliminar TextView y regresar al menu
     *
     * @param keyCode variable que viene por override
     * @param event   evento que viene por override
     * @return retorno que viene por override
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            vaciarScrolling(Menu.entries.size());
            super.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Crea todos los TextView para representar los datos de mesas
     * @param numeroDeMesas numero de mesas para saber cuantos TextView crear
     */
    private void llenarScrolling(int numeroDeMesas) {
        for (int i = 0; i < numeroDeMesas; i++) {
            crearTextView((String) Menu.entries.get(i), i);
        }
    }

    /**
     * Crea un TextView para representar los datos de unna mesa en el activity
     * @param data el contenido que tendrá eñ texTView
     * @param id el ID que se asignará al TextView una vez creado
     */
    private void crearTextView(String data,int id){
        View linearLayout = findViewById(R.id.LinearScroll);
        TextView newTextView = new TextView(this);
        newTextView.setText(data);
        newTextView.setId(id);
        newTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ((LinearLayout) linearLayout).addView(newTextView);
    }

    /**
     * Elimina todos los TextView que representan los datos de mesas
     * @param numeroDeMesas numero de mesas para saber cuantos TextView se crearon al iniciar esta actividad
     */
    private void vaciarScrolling(int numeroDeMesas){
        for(int i = 0; i < numeroDeMesas; i++){
            eliminarTextView(i);
        }
    }

    /**
     * Elimina un textView creado para presentar los datos de una mesa en el activity
     * @param id id con el que se sabe que TextView eliminar
     */
    private void eliminarTextView(int id){
        View linearLayout = findViewById(R.id.LinearScroll);
        ((LinearLayout) linearLayout).removeView((TextView)findViewById(id));
    }

    /**
     * Calcula los porcentajes de mesas ocupadas y mesas disponible y almacena estos datos en el arreglo enviado.
     */
    private void calcularPorcentajeMesas(float[] porcentajes){   // aun hay que programar esto. esta como provicional para mostrar datos
        porcentajes[0] = 63;
        porcentajes[1] = 37;
    }

    /**
     * Crea el gráfico de pastel
     */
    public void crearPastel(){
        pastel = (PieChart) customChart(pastel, Color.WHITE,2000);
        pastel.setHoleRadius(2);
        pastel.setHoleColor(Color.WHITE);
        pastel.setTransparentCircleRadius(6);
        pastel.setData(getPieData());
    }

    /**
     * Da las caracteristicas a nuestro objeto Chart (PieChart)
     * @param chart objeto tipo Chart al cual le seran dadas las caracteristicas
     * @param backgroundColor color de fondo de el objeto
     * @param animationTime tiempo de animacion para que se presente por complesto en milisegundos
     * @return el Chart que estamos modificando
     */
    private Chart customChart(Chart chart, int backgroundColor, int animationTime){
        chart.getDescription().setTextSize(20);
        chart.getDescription().setText("");
        chart.setBackgroundColor(backgroundColor);
        chart.animateY(animationTime);
        customCharLegend(chart);
        return chart;
    }

    /**
     * Da el formato y caracteristicas a las leyendas del objeto Chart (PieChart)
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
