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
import java.util.HashMap;
import java.util.Map;

public class Estado extends AppCompatActivity {

    private PieChart pastel;
    private final String[] estados = new String[]{"Mesas Ocupadas", "Mesas Libres"};
    private static final int[] colores = new int[]{Color.rgb(216, 96, 70), Color.rgb(70, 147, 216)};
    private final float[] porcentajes = new float[2]; // pos 0: ocupadas, pos 1: libres

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);
        pastel = findViewById(R.id.pieChartGraficaEstadoMesas);
        calcularPorcentajeMesas(Menu.registros, porcentajes);
        crearPastel();
        llenarScrolling(Menu.registros);
    }

    /**
     * METODO SOBREESCRITO: Toma la acción del boton de regresar del telefono y se encarga de llamar al método para eliminar TextView y regresar al menu
     * @param keyCode variable que viene por override
     * @param event evento que viene por override
     * @return retorno que viene por override
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            super.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     * @param registros
     */
    private void llenarScrolling(HashMap<String,String[]> registros) {
        for (Map.Entry<String, String[]> entry : registros.entrySet()) {
            String mesa = entry.getKey();
            String ubicacion = entry.getValue()[0];
            String capacidad = entry.getValue()[1];
            String estado = entry.getValue()[2];
            crearTextView(estado,capacidad,mesa);
        }
    }

    /**
     *
     * @param estado
     * @param capacidad
     * @param id
     */
    private void crearTextView(String estado,String capacidad, String id){
        View linearLayout = findViewById(R.id.LinearScrollEstado);
        TextView newTextView = new TextView(this);
        if(Integer.parseInt(id)<10)
            id = "0"+id;
        if(estado == null){
            newTextView.setText("           "+id+"                       "+"Sin Dato "+"                      "+capacidad);
        }
        else if(estado.equals("DE")){
            newTextView.setText("           "+id+"                       "+"   Libre    "+"                      "+capacidad);
        }
        else if(estado.equals("OC")){
            newTextView.setText("           "+id+"                       "+"Ocupada"+"                      "+capacidad);
        }
        newTextView.setId(Integer.parseInt(id));
        newTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ((LinearLayout) linearLayout).addView(newTextView);
    }

    /**
     * Elimina todos los TextView que representan los datos de mesas
     * @param numeroDeMesas numero de mesas para saber cuantos TextView se crearon al iniciar esta actividad
     */
    private void vaciarScrolling(int numeroDeMesas){
        View linearLayout = findViewById(R.id.LinearScrollEstado);
        for(int i = 0; i < numeroDeMesas; i++){
            ((LinearLayout) linearLayout).removeView(findViewById(i));
        }
    }

    /**
     * Calcula los porcentajes de mesas ocupadas y mesas disponible y almacena estos datos en el arreglo enviado.
     */
    private void calcularPorcentajeMesas(HashMap<String,String[]> registros, float[] porcentajes){
        int total = registros.size();
        int ocupados = 0;
        for (Map.Entry<String, String[]> entry : registros.entrySet()) {
            if(entry.getValue()[2] != null){
                if(entry.getValue()[2].equals("OC")){
                    ocupados++;
                }
            } else {
                total--;
            }
        }
        porcentajes[0] = (ocupados*100)/total; // ocupadas
        porcentajes[1] = 100 - porcentajes[0]; // libres

    }

    /**
     * Crea el gráfico de pastel
     */
    private void crearPastel(){
        customChart(pastel, Color.WHITE, 2000);
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
    private void customChart(Chart chart, int backgroundColor, int animationTime){
        chart.getDescription().setTextSize(20);
        chart.getDescription().setText("");
        chart.setBackgroundColor(backgroundColor);
        chart.animateY(animationTime);
        customCharLegend(chart);
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
