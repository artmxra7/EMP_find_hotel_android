package com.emp.findhotel.findhotel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
    private RequestQueue requestQueue;
    private ArrayList<HashMap<String, String>> data;

    private String rooms[];
    private int roomID;

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

        slikasobe = (ImageView) findViewById(R.id.slikasobe);

        lokacija = (TextView) findViewById(R.id.lokacija);
        lokacija.setText(All.naslov);

        ocena = (RatingBar) findViewById(R.id.ocena);
        ocena.setRating(All.ocena);

        ponudba = (TextView) findViewById(R.id.ponudbasobe);
        cena = (TextView) findViewById(R.id.cena);

        seznamsob = (Spinner) findViewById(R.id.seznamsob);
        // nastavi sobe
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://83.212.82.49/api/rooms/?hotel=" + All.hotelID;
        JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
        requestQueue.add(request);

        seznamsob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ponudba.setText(data.get(i).get("description"));
                cena.setText(data.get(i).get("price") + "€");

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://83.212.82.49/static/" + data.get(i).get("img");
                ImageRequest imageRequest= new ImageRequest(url, bitmapListener, 0, 0,
                        ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, errorListener);
                requestQueue.add(imageRequest);

                roomID = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        koncaj = (Button) findViewById(R.id.complete);
        koncaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datum_do.getText().length() != 0 && datum_do.getText().length() != 0) {
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


    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            data = new ArrayList<>();
            rooms = new String[response.length()];
            for (int i = 0; i < response.length(); i++) {
                try {
                    HashMap<String, String> map = new HashMap<>();
                    JSONObject jsonObject = response.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    String price = jsonObject.getString("price");
                    String img = jsonObject.getString("img");

                    map.put("name", name);
                    map.put("description", description);
                    map.put("price", price);
                    map.put("img", img);
                    data.add(map);
                    rooms[i] = name;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                ArrayAdapter<String> roomsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, rooms);
                roomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                seznamsob.setAdapter(roomsAdapter);
                ponudba.setText(data.get(0).get("description"));
                cena.setText(data.get(0).get("price") + "€");


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://83.212.82.49/static/" + data.get(0).get("img");
                ImageRequest imageRequest= new ImageRequest(url, bitmapListener, 0, 0,
                        ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, errorListener);
                requestQueue.add(imageRequest);

            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

    private Response.Listener<Bitmap> bitmapListener = new Response.Listener<Bitmap>() {
        @Override
        public void onResponse(Bitmap response) {
            double ratio = (double)response.getHeight() / (double)response.getWidth();
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int newWidth = metrics.heightPixels;
            int newHeight = (int)((double)newWidth * ratio);
            Bitmap scaled = Bitmap.createScaledBitmap(response, newWidth, newHeight, false);
            slikasobe.setImageBitmap(scaled);
        }
    };


}
