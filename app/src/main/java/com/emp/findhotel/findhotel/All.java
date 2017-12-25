package com.emp.findhotel.findhotel;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private Spinner sort_spinner;
    private Spinner drzava_spiner;
    private ListView hoteli;
    private ArrayList<HashMap<String, String>> data = null;
    private RequestQueue requestQueue;

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

        //http://www.parallelcodes.com/android-listview-from-ms-sql-server/
        //preko poizvedbe prebereva vse hotele, preko  while(query.next()) dobiva vse vrstice in jih vstaviva v ListView (ime hotela, rating, cena)
        //ko klikneš na hotel iz ListViewa se odpre okno oz. razred Reserve
        hoteli = (ListView) v.findViewById(R.id.vsihoteli);
        hoteli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(data.get(i));
            }
        });


        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://83.212.82.49/api/";
        JsonArrayRequest request = new JsonArrayRequest(url, jsonArrayListener, errorListener);
        requestQueue.add(request);

        return v;
    }

    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String id = jsonObject.getString("id");
                    String rating = jsonObject.getString("rating");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", name);
                    map.put("id", id);
                    map.put("rating", rating);
                    data.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }

            SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
                    data, R.layout.list_layout, new String[]{"name", "id"},
                    new int[]{R.id.textViewName, R.id.textViewInfo});
            hoteli.setAdapter(adapter);
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("Error:" + error.toString());
        }
    };



/*
    public ArrayList<HashMap<String, String>> parseJSON(){
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(MainActivity.data);
            JSONArray jsonArray = jsonObject.getJSONArray("hotels");
            JSONObject object;
            String name;
            HashMap<String, String> map;
            for (int i = 0; i < jsonArray.length(); i++){
                 map = new HashMap<>();
                 object = jsonArray.getJSONObject(i);
                 // id = object.getString("id");
                 name = object.getString("name");
                 map.put("name", name);
                 data.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void createList(){
        MainActivity.setData();
        data = parseJSON();

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
                data, R.layout.list_layout, new String[]{"name", "info"},
                new int[]{R.id.textViewName, R.id.textViewInfo});
        hoteli.setAdapter(adapter);
    }
*/
}
