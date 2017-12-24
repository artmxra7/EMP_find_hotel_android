package com.emp.findhotel.findhotel;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Luka on 22. 12. 2017.
 */

public class Complete extends Fragment {

    private TextView imehotela;
    private TextView lokacija;
    private RatingBar ocena;
    private Spinner seznamsob;
    private TextView datum_od;
    private TextView datum_do;
    private Button izbira_od;
    private Button izbira_do;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ImageView slikasobe;
    private TextView ponudba;
    private TextView cena;
    private Button koncaj;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_complete, container, false);

        imehotela = (TextView) v.findViewById(R.id.imehotela);
        //imehotela.setText(...) -> pridobimo iz okna Reserve oz. razreda Reserve

        lokacija = (TextView) v.findViewById(R.id.lokacija);
        //lokacija.setText(...) -> pridobimo iz okna Reserve oz. razreda Reserve

        ocena = (RatingBar) v.findViewById(R.id.ocena);
        //npr. ocena.setRating(Float.parseFloat("2.0")); -> številko ocene pridobimo iz okna Reserve oz. razreda Reserve
        //rate_bar.setRating(0.0f);

        seznamsob = (Spinner) v.findViewById(R.id.seznamsob);
        //nastavimo na isti način kot v razredu All, podatke o sobah pridobimo iz baze

        datum_od = (TextView) v.findViewById(R.id.izpisfrom);
        izbira_od = (Button) v.findViewById(R.id.fromdate);

        izbira_od.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int leto = cal.get(Calendar.YEAR);
                int mesec = cal.get(Calendar.MONTH);
                int dan = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, leto, mesec, dan);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int leto, int mesec, int dan) {
                mesec = mesec + 1; //januar = 0, mora biti 1
                Log.d("TAG","onDateSet: dd.mm.yyyy: " + dan +"." + mesec +"." + leto);
                String datumod = dan + "." + mesec + "." + leto; //ta string lahko zapišemo v bazo za datum
                datum_od.setText(datumod);
            }
        };

        datum_do = (TextView) v.findViewById(R.id.izpisto);
        izbira_do = (Button) v.findViewById(R.id.todate);

        izbira_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int leto = cal.get(Calendar.YEAR);
                int mesec = cal.get(Calendar.MONTH);
                int dan = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, leto, mesec, dan);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int leto, int mesec, int dan) {
                mesec = mesec + 1;
                Log.d("TAG","onDateSet: dd.mm.yyyy: " + dan +"." + mesec +"." + leto);
                String datumod = dan + "." + mesec + "." + leto; //ta string lahko zapišemo v bazo za datum
                datum_do.setText(datumod);
            }
        };

        slikasobe = (ImageView) v.findViewById(R.id.slikasobe);
        //sliko nastavimo preko JSON iz baze

        ponudba = (TextView) v.findViewById(R.id.ponudbasobe);
        //ponudba.setText(...) -> pridobimo iz baze

        cena = (TextView) v.findViewById(R.id.cena);
        //cena.setText(...) -> pridobimo iz baze

        koncaj = (Button) v.findViewById(R.id.complete);
        koncaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //zapiši va bazo (flag), da se je rezervirala soba
                //nazaj na stran home
            }
        });

        return v;
    }
}
