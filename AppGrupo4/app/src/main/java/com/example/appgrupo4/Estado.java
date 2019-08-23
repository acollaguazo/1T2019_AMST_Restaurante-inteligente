package com.example.appgrupo4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import java.util.List;
import java.util.Map;

/**
 * La clase Estado muestra la grafica de pastel del porcentaje de mesas ocupadas y desocupadas.
 * Ademas, de una lista de las mesas con su informacion.
 */
@SuppressWarnings("ALL")
public class Estado extends AppCompatActivity {
    private PieChart pastel;
    private final String[] estados = new String[]{"Mesas Ocupadas", "Mesas Libres"};
    private static final int[] colores = new int[]{Color.rgb(216, 96, 70),
            Color.rgb(70, 147, 216)};
    private final float[] porcentajes = new float[2]; // pos 0: ocupadas, pos 1: libres
    LinearLayout layout_estado;
    PieChart pieChart;

    public static int width, height;
    public static String orientacion = "";

    /**
     * METODO SOBREESCRITO: Permite almacenar y recuperar el estado de la actividad estado, ademas,
     * se actualizara cada cierto tiempo con los valores actuales.
     *
     * @param savedInstanceState La actividad almacenada a recuperar.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);
        pastel = findViewById(R.id.pieChartGraficaEstadoMesas);

        refresh();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        pieChart = findViewById(R.id.pieChartGraficaEstadoMesas);
        layout_estado = findViewById(R.id.Linear123);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            orientacion = "portrait";
            layout_estado.setOrientation(LinearLayout.VERTICAL);
            pieChart.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;
            pieChart.getLayoutParams().height = height/2;
        }
        else if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE){
            orientacion = "landscape";
            layout_estado.setOrientation(LinearLayout.HORIZONTAL);
            pieChart.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
            pieChart.getLayoutParams().width = width/2;
        }
    }
    /**
     * Permite la actualizacion de la aplicacion de manera automatica cada 7 segundos.
     */
    public void refresh(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                calcularPorcentajeMesas(Menu.registros, porcentajes);
                crearPastel();
                llenarScrolling(Menu.registros);
                handler.postDelayed(this, 7000);
            }
        };
        runnable.run();

    }

    /**
     * METODO SOBREESCRITO: Toma la acción del boton de regresar del telefono y se encarga de
     * llamar al método para eliminar TextView y regresar al menu.
     *
     * @param keyCode   variable que viene por override
     * @param event     evento que viene por override
     * @return          retorno que viene por override
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
     * Permite agregar a la lista de items la infomracion de las mesas dependiendo su estado.
     *
     * @param items     Lista de las mesas.
     * @param mesa      El numero de la mesa.
     * @param capacidad Numero de personas que se pueden sentar en la mesa.
     * @param estado    El estado actual de la mesa.
     */
    @SuppressLint("SetTextI18n")
    private void aggItem(List items, String mesa, String capacidad, String estado){
        if(estado == null){
            // Ingresa si no se posee infomracion del estado
            items.add(new Mesados(mesa,capacidad,"SIN DATOS"));
        }
        else if(estado.equals("OC")) {
            // Ingresa si la mesa esta ocupada.
            items.add(new Mesados(mesa,capacidad,"OCUPADA"));
        }
        else if(estado.equals("DE")) {
            // Ingresa si la mesa esta desocupada.
            items.add(new Mesados(mesa,capacidad,"LIBRE"));
        }
    }

    /**
     * Permite llenar la ventana de desplazamiento con los estados de las mesas.
     *
     * @param registros HashMap que contiene la informacion de las mesas
     */
    @SuppressWarnings("unused")
    private void llenarScrolling(HashMap<String,String[]> registros) {

        List items = new ArrayList();

        RecyclerView recycler = findViewById(R.id.reciclador2);
        recycler.setHasFixedSize(true);

        RecyclerView.Adapter adapter = new MesadosAdapter(items);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(mLayoutManager);
        recycler.setAdapter(adapter);
        /**
         * Se Recorre el HashMap para separar los datos de las mesas y luego se los agrega a la
         * lista separandolos por sus estados.
         */
        for (Map.Entry<String, String[]> entry : registros.entrySet()) {
            String mesa = entry.getKey();
            String ubicacion = entry.getValue()[0];
            String capacidad = entry.getValue()[1];
            String estado = entry.getValue()[2];
            aggItem(items,mesa,capacidad,estado);
        }
    }


    /**
     * Calcula los porcentajes de mesas ocupadas y mesas disponible y
     * almacena estos datos en el arreglo enviado.
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
        try {
            porcentajes[0] = (ocupados*100)/total; // ocupadas
            porcentajes[1] = 100 - porcentajes[0]; // libres
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }


    }

    /**
     * Se crea el grafico de pastel para mostrar el porcentaje del estado de las mesas.
     */
    private void crearPastel(){
        customChart(pastel, Color.TRANSPARENT, 2000);
        pastel.setHoleRadius(2);
        pastel.setHoleColor(Color.WHITE);
        pastel.setTransparentCircleRadius(6);
        pastel.setData(getPieData());
    }

    /**
     * Da las caracteristicas de la grafica de pastel a msotrar.
     *
     * @param chart           Objeto tipo Chart al cual le seran dadas las caracteristicas
     * @param backgroundColor Color de fondo de el objeto
     * @param animationTime   El tiempo de animacion en milisegundos
     * @return                El Chart que estamos modificando
     */
    private void customChart(Chart chart, int backgroundColor, int animationTime){
        chart.getDescription().setTextSize(20);
        chart.getDescription().setText("");
        chart.setBackgroundColor(backgroundColor);
        chart.animateY(animationTime);
        customCharLegend(chart);
    }

    /**
     * Da el formato y caracteristicas a las leyendas del objeto Chart (PieChart).
     *
     * @param chart La grafica que se dara formato.
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
     * Permite obtener los datos de la grafica de pastel.
     *
     * @return Retorna la informacion de la grafica de pastel.
     */
    private PieData getPieData(){
        PieDataSet pieDataSet = (PieDataSet) getData(new PieDataSet(getPieEntries(),""));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);
    }

    /**
     * Obtenemos los datos del dataset.
     * @param dataset   El dataset el cual se obtendran los datos
     * @return          Retorna el dataset creado.
     */
    private DataSet getData(DataSet dataset){
        dataset.setColors(colores);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueTextSize(20);
        return dataset;
    }

    /**
     *  Obtener los datos a ingresar a la grafica.
     *
     * @return Retorna una lista con los datos a ingresar a la grafica de pastel.
     */
    private ArrayList<PieEntry> getPieEntries(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i=0; i<estados.length; i++)
            entries.add(new PieEntry(porcentajes[i]));
        return entries;
    }
}
