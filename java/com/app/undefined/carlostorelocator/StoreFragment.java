package com.app.undefined.carlostorelocator;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {

    JSONArray StoreJsonArray;
    ListView storeListView;
    public View vView;




    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("jsoon", "andato in StoreFrag");
        vView = inflater.inflate(R.layout.fragment_store, container, false);
        storeListView = (ListView) vView.findViewById(R.id.ListViewS);
        if(savedInstanceState!=null){
            StoreJsonArray= (JSONArray)savedInstanceState.get("List");
        }


            try {JSONObject evento;
                final ArrayList<String> listp = new ArrayList<String>();
                for (int i = 0; i < 10; i++) {
                    evento = (JSONObject) StoreJsonArray.get(i);
                    listp.add(evento.getString("name"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(vView.getContext(), R.layout.listview_layout, listp);
                storeListView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return vView;

       /* MyAdapter adapter = new MyAdapter(this, generateData());

        ListView listView = (ListView) findViewById(R.id.ListViewS);

        listView.setAdapter(adapter);*/
    }

   /* private ArrayList<Items> generateData(){
        ArrayList<Items> items = new ArrayList<Items>();
        items.add(new Items("Item 1","Da sostituire con i dettagli del JSONArray"));
        items.add(new Items("Item 2","Da sostituire con i dettagli del JSONArray"));
        items.add(new Items("Item 3","Da sostituire con i dettagli del JSONArray"));

        return items;
    }*/

}
