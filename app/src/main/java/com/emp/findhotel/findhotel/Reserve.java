package com.emp.findhotel.findhotel;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by Luka on 20. 12. 2017.
 */

public class Reserve extends Fragment{

    private TextView imehotela;
    private RatingBar ocena;
    private ImageView slika1;
    private ImageView slika2;
    private TextView opis;
    private Button naslednja_slika;
    private ImageSwitcher ponudba_slike;
    private Button rezerviraj;
    int st = 0;
    Integer [] slike = {R.drawable.parking, R.drawable.pet, R.drawable.wifi};

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_reserve, container, false);

        imehotela = (TextView) v.findViewById(R.id.imehotela);
        //imehotela.setText(); -> pridobimo iz baze oz. glede na to kateri hotel smo izbrali

        ocena = (RatingBar) v.findViewById(R.id.ocena);
        //npr. ocena.setRating(Float.parseFloat("2.0")); -> številko ocene pridobimo iz baze oz. glede na to kateri hotel smo izbrali
        //rate_bar.setRating(0.0f);

        slika1 = (ImageView) v.findViewById(R.id.slika1);
        slika2 = (ImageView) v.findViewById(R.id.slika2);
        //slika1.setImageResource(R.drawable.my_image); -> sliko nastavimo preko JSON
        //slika2.setImageResource(R.drawable.my_image);

        opis = (TextView) v.findViewById(R.id.opis);
        //opis.setText(...) -> nastavimo preko JSON, povlečemo podatke iz baze

        naslednja_slika = (Button) v.findViewById(R.id.naslednjaslika);

        ponudba_slike = (ImageSwitcher) v.findViewById(R.id.ponudbaslike);
        rezerviraj = (Button) v.findViewById(R.id.rezerviraj);

        ponudba_slike.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView(){
                ImageView imageView = new ImageView(getActivity().getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });

        ponudba_slike.setImageResource(slike[st]);

        naslednja_slika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(st + 1 == slike.length)
                    st = 0;
                else
                    st += 1;
                ponudba_slike.setImageResource(slike[st]);
            }
        });

        rezerviraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //odpri Complete okno in razred in prenese vse potrebne podatke
            }
        });

        return v;
    }
}
