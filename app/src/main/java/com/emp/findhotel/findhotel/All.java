package com.emp.findhotel.findhotel;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Luka on 18. 12. 2017.
 */

public class All extends Fragment {

    private String[] sort_items = {"Expensive first", "Cheap first", "Low rate", "High rate"};
    private String[] countries;
    private Spinner sort_spinner;
    private Spinner drzava_spiner;
    private ListView hoteli;
    private ArrayList<HashMap<String, String>> data = null;
    private RequestQueue requestQueue;
    private JsonArrayRequest request;

    private String country = "All";
    private String sort = "None";

    static String ime;
    static float ocena;
    static String opis;
    static String img1, img2;
    static String naslov;
    static int hotelID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_all, container, false);

        sort_spinner = (Spinner) v.findViewById(R.id.sort);
        ArrayAdapter<String> sort_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, sort_items);
        sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort_spinner.setAdapter(sort_adapter);

        //tukaj preko poizvedbe izpišemo imena držav v ArrayList ali Array
        //nato na isti postopek kot pri sort_spinner nastavimo elemente v drzava_spinner
        drzava_spiner = (Spinner) v.findViewById(R.id.drzava);

        //preko poizvedbe prebereva vse hotele, preko  while(query.next()) dobiva vse vrstice in jih vstaviva v ListView (ime hotela, rating, cena)
        //ko klikneš na hotel iz ListViewa se odpre okno oz. razred Reserve
        hoteli = (ListView) v.findViewById(R.id.vsihoteli);

        // Creates a new JSON request to the server API
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://83.212.82.49/api?country=" + country + "&sort=" + sort;
        request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
        requestQueue.add(request);


        url = "http://83.212.82.49/api/countries";
        request = new JsonArrayRequest(url, jsonCountryArrayListener, errorListener);
        requestQueue.add(request);

        drzava_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (view != null) {
                    country = countries[i];
                    String url = "http://83.212.82.49/api?country=" + country + "&sort=" + sort;
                    request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
                    requestQueue.add(request);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (view != null) {
                    sort = sort_items[i].replace(" ", "");
                    String url = "http://83.212.82.49/api?country=" + country + "&sort=" + sort;
                    request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
                    requestQueue.add(request);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ko izberemo nekaj iz listViewa
        hoteli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //System.out.println(data.get(i));
                ime = data.get(i).get("name");
                naslov = data.get(i).get("address");
                ocena = Float.parseFloat(data.get(i).get("rating"));
                opis = data.get(i).get("description");
                img1 = data.get(i).get("img1");
                img2 = data.get(i).get("img2");
                hotelID =  Integer.parseInt(data.get(i).get("id"));

                Intent intent = new Intent(getActivity(), Reserve.class);
                startActivity(intent);
            }
        });

        return v;
    }

    // What happens when the server responds
    private Response.Listener<JSONArray> jsonCountryArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            countries = new String[response.length() + 1];
            countries[0] = "All";
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    String country = jsonObject.getString("name");
                    countries[i + 1] = country;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                ArrayAdapter<String> drzavaAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countries);
                drzavaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                drzava_spiner.setAdapter(drzavaAdapter);
            }
        }
    };

    // What happens when the server responds
    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String price = jsonObject.getString("price") + "€+";
                    String rating = jsonObject.getString("rating");
                    String description = jsonObject.getString("description");
                    String address = jsonObject.getString("address");
                    String img1 = jsonObject.getString("img1");
                    String img2 = jsonObject.getString("img2");
                    String id = jsonObject.getString("id");
                    HashMap<String, String> map = new HashMap<>();
                    if(MainActivity.iskanje && MainActivity.iskanihotel != "" && name.toLowerCase().contains(MainActivity.iskanihotel.toLowerCase())) {
                        map.put("name", name);
                        map.put("price", price);
                        map.put("rating", rating);
                        map.put("description", description);
                        map.put("address", address);
                        map.put("img1", img1);
                        map.put("img2", img2);
                        map.put("id", id);
                        data.add(map);
                    }
                    else if (MainActivity.iskanje == false){
                        map.put("name", name);
                        map.put("price", price);
                        map.put("rating", rating);
                        map.put("description", description);
                        map.put("address", address);
                        map.put("img1", img1);
                        map.put("img2", img2);
                        map.put("id", id);
                        data.add(map);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }

            SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
                    data, R.layout.list_layout, new String[]{"name", "rating", "price"},
                    new int[]{R.id.textViewName, R.id.textViewRating, R.id.textViewPrice});
            hoteli.setAdapter(adapter);
        }
    };

    // What happens if error occurs
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("Error:" + error.toString());
        }
    };

}
