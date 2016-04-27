package com.app.undefined.carlostorelocator;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetail extends Fragment {
    JSONArray StoreJsonArray;
    ListView storeListView;
    public View vView;




    public FragmentDetail() {
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
    }


}
