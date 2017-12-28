package com.emp.findhotel.findhotel;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by Luka on 20. 12. 2017.
 */

public class Reserve extends AppCompatActivity{

    private TextView imehotela;
    private TextView lokacija;
    private RatingBar ocena;
    private ImageView slika1;
    private ImageView slika2;
    private TextView opis;
    private Button naslednja_slika;
    private ImageSwitcher ponudba_slike;
    private Button rezerviraj;
    int st = 0;
    Integer [] slike = {R.drawable.parking, R.drawable.pet, R.drawable.wifi};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);

        imehotela = (TextView) findViewById(R.id.imehotela);
        imehotela.setText(All.ime);

        imehotela = (TextView) findViewById(R.id.lokacija);
        imehotela.setText(All.naslov);

        ocena = (RatingBar) findViewById(R.id.ocena);
        ocena.setRating(All.ocena);

        slika1 = (ImageView) findViewById(R.id.slika1);
        slika2 = (ImageView) findViewById(R.id.slika2);
        //slika1.setImageResource(R.drawable.my_image); -> sliko nastavimo preko JSON
        //slika2.setImageResource(R.drawable.my_image);

        opis = (TextView) findViewById(R.id.opis);
        opis.setText(All.opis);

        naslednja_slika = (Button) findViewById(R.id.naslednjaslika);

        ponudba_slike = (ImageSwitcher) findViewById(R.id.ponudbaslike);
        rezerviraj = (Button) findViewById(R.id.rezerviraj);

        ponudba_slike.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView(){
                ImageView imageView = new ImageView(getApplicationContext());
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
                goComplete();
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

    public void goComplete(){
        Intent intent = new Intent(this, Complete.class);
        startActivity(intent);
    }
}
