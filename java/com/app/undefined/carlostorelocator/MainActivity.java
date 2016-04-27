package com.app.undefined.carlostorelocator;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private final String FRAGMENT = "Login";
    private final String FRAGMENTST = "Store";
    static MyFragment LoginFragment;
    static StoreFragment StoreList;
    static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager= getFragmentManager();
        LoginFragment = (MyFragment)fragmentManager.findFragmentByTag(FRAGMENT);
        if(LoginFragment==null){
            FragmentTransaction calcTrans = fragmentManager.beginTransaction();
            LoginFragment = new MyFragment();
            calcTrans.add(R.id.container,LoginFragment,FRAGMENT);
            calcTrans.commit();
        }
    }



    public void DataGetted(JSONArray myArray) {
        LoginFragment.close();
        Log.d("jsoon", "andato in datagetted");
        StoreList = (StoreFragment)fragmentManager.findFragmentByTag(FRAGMENTST);
        if(StoreList==null){
            FragmentTransaction storeTrans = fragmentManager.beginTransaction();
            StoreList = new StoreFragment();
            StoreList.StoreJsonArray=myArray;
            storeTrans.add(R.id.container,StoreList,FRAGMENTST);
            storeTrans.commit();
        }

    }
}
