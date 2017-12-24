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

import java.util.ArrayList;
import java.util.HashMap;

import static com.emp.findhotel.findhotel.R.id.sort;

/**
 * Created by Luka on 18. 12. 2017.
 */

public class All extends Fragment {

    private String[] sort_items = {"Expensive first", "Cheap first", "Low rate", "High rate"};
    private Spinner sort_spinner;
    private Spinner drzava_spiner;
    private ListView hoteli;

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
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "nameTest");
        map.put("info", "infoTest");
        data.add(map);

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
                data, R.layout.list_layout, new String[]{"name", "info"},
                new int[]{R.id.textViewName, R.id.textViewInfo});
        hoteli = (ListView) v.findViewById(R.id.vsihoteli);
        hoteli.setAdapter(adapter);
        hoteli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
            }
        });
        return v;
    }



}
