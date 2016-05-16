package milanomaps.undefined.tsam.app.locatorstore;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;

public class GetLogIn extends Fragment {
    public JSONArray arrayGlobal;
    public MainActivity activity;
    public View mView;
    ProgressBar carica;



    private IOLoginInterface mListener= new IOLoginInterface() {
        @Override
        public void getArray(JSONArray aArray) {
        }
    };



    public interface IOLoginInterface{
        void getArray(JSONArray aArray);
    }
    public GetLogIn(){

    }
    String session="";
    public void setSession(String session1)
    {
        session=session1;
    }
    public void LogInForm(String mail, String pwd,View view) throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException {

        RequestParams params = new RequestParams();
        params.put("email",mail);
        vView=view;
        EncrypterInSha512 Obj = new EncrypterInSha512();
        pwd=Obj.GenerateHash(pwd);
        Log.d("pwd",pwd);
        params.put("password",pwd);

        carica.setVisibility(View.VISIBLE);
        HttpRest.post("public/v2/login", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONObject
                JSONObject event ;
                try {

                    event = (JSONObject) response.get("data");
                    setSession(event.get("session").toString());
                    Log.d("jsoon", "letto get"+session);
                    DownloadData(session);
                } catch (Exception e) {
                    Log.d("errorr", e.getMessage());
                }
                Log.d("jsoon", "letto get");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {Log.d("errorr", responseString+statusCode);}
        });

    }

    String params;
    JSONArray event = new JSONArray();
    private void DownloadData(String session) {

        params=session;
        HttpRest.postD("v2/stores", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONObject
                JSONArray event ;
                try {
                    carica.setVisibility(View.INVISIBLE);
                    MainActivity.barracerca.setEnabled(true);
                    mListener.getArray((JSONArray) response.get("data"));

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

    @Nullable
    TextView mail;
    TextView pwd;
    Button submit;
    View vView;
    public static GetLogIn getInstance(){
      return new GetLogIn();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vView = inflater.inflate(R.layout.login_fragment,container,false);
        mail = (TextView) vView.findViewById(R.id.mail);
        pwd = (TextView) vView.findViewById(R.id.pwd);
        submit = (Button) vView.findViewById(R.id.btnLogin);
        String strMail="tsac-2015@tecnicosuperiorekennedy.it";
        String strPwd="tsac";
        carica=(ProgressBar) vView.findViewById(R.id.carica);
        if(savedInstanceState!=null){
            strMail= savedInstanceState.getString("mail");
            strPwd= savedInstanceState.getString("pwd");
        }
        mail.setText(strMail);
        pwd.setText(strPwd);
        View.OnClickListener submitListen = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    LogInForm(mail.getText().toString(),pwd.getText().toString(),vView);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }



            }};
            submit.setOnClickListener(submitListen);
            Animation anim = AnimationUtils.loadAnimation(this.vView.getContext(), R.anim.enter);
        vView.startAnimation(anim);



        this.vView.setVisibility(View.VISIBLE);

        return vView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity() instanceof IOLoginInterface)
        {
            mListener = (IOLoginInterface) getActivity();
        }

    }
}