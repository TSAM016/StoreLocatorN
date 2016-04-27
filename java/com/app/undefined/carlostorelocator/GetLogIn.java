package com.app.undefined.carlostorelocator;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import cz.msebera.android.httpclient.Header;

public class GetLogIn {
    public JSONArray arrayGlobal;
    public MainActivity activity;
    public View mView;


    public void LogInForm(String mail, String pwd,View view,Object callback) throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException {
        RequestParams params = new RequestParams();
        params.put("email",mail);
        mView=view;
        EncrypterInSha512 Obj = new EncrypterInSha512();
        pwd=Obj.GenerateHash(pwd);
        Log.d("pwd",pwd);
        params.put("password",pwd);
        HttpRest.post("http://its-bitrace.herokuapp.com/api/public/v2/login", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONObject
                JSONObject event ;
                try {
                    event = (JSONObject) response.get("data");
                    String session= event.get("session").toString();
                    Log.d("jsoon", "letto get"+session);
                    DownloadData(session);



                } catch (Exception e) {
                    Log.d("errorr", e.getMessage());
                }
                Log.d("jsoon", "letto get");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("errorr", responseString+statusCode);

            }

        });

    }
    String params;
    private void DownloadData(String session) {
        params=session;
        HttpRest.postD("http://its-bitrace.herokuapp.com/api/v2/stores", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONObject
                JSONArray event ;
                try {
                    event = (JSONArray) response.get("data");
                    JSONObject session=(JSONObject) event.get(0);
                    Log.d("jsoon", "letto get dentro postD" + session.get("phone"));
                    Toast.makeText(mView.getContext(),"letto get dentro postD sessione:" + params/*session.get("phone").toString()*/ ,Toast.LENGTH_LONG).show();
                    MainActivity obj= new MainActivity();

                    obj.DataGetted(event);


                } catch (Exception e) {
                    Log.d("errorr", e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("errorr", responseString+statusCode);

            }

        });
    }
}