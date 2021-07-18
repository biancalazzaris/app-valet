package com.example.valet;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.valet.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.AdapterView;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private List<String> carros = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private DateFormat df = null;
    private DateFormat hf = null;

    private AlertDialog dialog;
    private String carroSaida = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), ValetActivity.class);
                    startActivityForResult(intent); 1;);
                    }
                });

        df = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        hf = android.text.format.DateFormat.getTimeFormat(getApplicationContext());

        ListView lista = (ListView) findViewById(R.id.lista);
        adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, carros);
        lista.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            String modelo = data.getStringExtra("modelo");
            String placa = data.getStringExtra("placa");
            Date agora = new Date();
            carros.add(modelo + " - " + placa + "\nEntrada: " + df.format(agora)
                    + " " + hf.format(agora));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), ValetActivity.class);
        startActivityForResult(intent, 1);
    }
});

        df = android.text.format.DateFormat
        .getDateFormat(getApplicationContext());
        hf = android.text.format.DateFormat
        .getTimeFormat(getApplicationContext());

        ListView lista = (ListView) findViewById(R.id.lista);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, carros);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view,
    int position, long id) {
                carroSaida = carros.get(position);
                dialog = confirmarSaida();
                dialog.show();
                }
    });
    }
}

    public AlertDialog confirmarSaida() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.saida);
        builder.setMessage(carroSaida);
        builder.setPositiveButton(getString(R.string.sim),
                new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
                carros.remove(carroSaida);
                adapter.notifyDataSetChanged();
                }
});
        builder.setNegativeButton(getString(R.string.nao), null);
        return builder.create();
}