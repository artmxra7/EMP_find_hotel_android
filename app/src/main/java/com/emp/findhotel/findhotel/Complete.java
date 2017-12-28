package com.emp.findhotel.findhotel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Luka on 22. 12. 2017.
 */

public class Complete extends AppCompatActivity {

    private TextView imehotela;
    private TextView lokacija;
    private RatingBar ocena;
    private Spinner seznamsob;
    private TextView datum_od;
    private TextView datum_do;
    private Button izbira_od;
    private Button izbira_do;
    private DatePickerDialog.OnDateSetListener dateOdSetListener;
    private DatePickerDialog.OnDateSetListener dateDoSetListener;
    private ImageView slikasobe;
    private TextView ponudba;
    private TextView cena;
    private Button koncaj;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);

        imehotela = (TextView) findViewById(R.id.imehotela);
        imehotela.setText(All.ime);

        lokacija = (TextView) findViewById(R.id.lokacija);
        lokacija.setText(All.naslov);

        ocena = (RatingBar) findViewById(R.id.ocena);
        ocena.setRating(All.ocena);

        seznamsob = (Spinner) findViewById(R.id.seznamsob);
        //nastavimo na isti način kot v razredu All, podatke o sobah pridobimo iz baze

        datum_od = (TextView) findViewById(R.id.izpisfrom);
        izbira_od = (Button) findViewById(R.id.fromdate);

        izbira_od.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int leto = cal.get(Calendar.YEAR);
                int mesec = cal.get(Calendar.MONTH);
                int dan = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_Holo_Dialog_MinWidth, dateOdSetListener, leto, mesec, dan);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateOdSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int leto, int mesec, int dan) {
                mesec = mesec + 1; //januar = 0, mora biti 1
                Log.d("TAG","onDateSet: dd.mm.yyyy: " + dan +"." + mesec +"." + leto);
                String datumod = dan + "." + mesec + "." + leto; //ta string lahko zapišemo v bazo za datum
                datum_od.setText(datumod);
            }
        };

        datum_do = (TextView) findViewById(R.id.izpisto);
        izbira_do = (Button) findViewById(R.id.todate);

        izbira_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int leto = cal.get(Calendar.YEAR);
                int mesec = cal.get(Calendar.MONTH);
                int dan = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_Holo_Dialog_MinWidth, dateDoSetListener, leto, mesec, dan);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateDoSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int leto, int mesec, int dan) {
                mesec = mesec + 1;
                Log.d("TAG","onDateSet: dd.mm.yyyy: " + dan +"." + mesec +"." + leto);
                String datumdo = dan + "." + mesec + "." + leto; //ta string lahko zapišemo v bazo za datum
                datum_do.setText(datumdo);
            }
        };

        slikasobe = (ImageView) findViewById(R.id.slikasobe);
        //sliko nastavimo preko JSON iz baze

        ponudba = (TextView) findViewById(R.id.ponudbasobe);
        //ponudba.setText(...) -> pridobimo iz baze

        cena = (TextView) findViewById(R.id.cena);
        //cena.setText(...) -> pridobimo iz baze

        koncaj = (Button) findViewById(R.id.complete);
        koncaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datum_do.getText().length() != 0 && datum_do.getText().length() != 0) {
                    //zapiši va bazo (flag), da se je rezervirala soba
                    goHome();
                }
                else{
                    Toast.makeText(getApplication().getBaseContext(), "Dates or room not chosen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        MenuItem item = menu.findItem(R.id.menuHome);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                goHome();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
