package com.app.undefined.carlostorelocator;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    TextView mail;
    TextView pwd;
    ImageButton submit;
    String strMail="";
    String strPwd="";
    public View vView;
    public MyFragment() {
        // Required empty public constructor
    }

    public void close() {
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }
    int callback(){ return 0;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vView = inflater.inflate(R.layout.login_fragment, container, false);
        mail = (TextView) vView.findViewById(R.id.mail);
        pwd = (TextView) vView.findViewById(R.id.pwd);
        submit = (ImageButton) vView.findViewById(R.id.btnLogin);
        if(savedInstanceState!=null){
            strMail= savedInstanceState.getString("mail");
            strPwd= savedInstanceState.getString("pwd");
        }
        mail.setText("tsac-2015@tecnicosuperiorekennedy.it"+strMail);
        pwd.setText("tsac"+strPwd);
        View.OnClickListener submitListen = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLogIn in= new GetLogIn();
                try {
                    in.LogInForm(mail.getText().toString(),pwd.getText().toString(),vView,callback());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }};
        submit.setOnClickListener(submitListen);
        return vView;
    }

}
