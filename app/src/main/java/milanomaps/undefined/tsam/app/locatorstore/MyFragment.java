package milanomaps.undefined.tsam.app.locatorstore;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
    Button submit;
    String strMail="";
    String strPwd="";
    public View vView;
    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vView = inflater.inflate(R.layout.login_fragment, container, false);
        mail = (TextView) vView.findViewById(R.id.mail);
        pwd = (TextView) vView.findViewById(R.id.pwd);
        submit = (Button) vView.findViewById(R.id.btnLogin);
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
                    in.LogInForm(mail.getText().toString(),pwd.getText().toString(),vView);
                    closefragment();


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

        getActivity().findViewById(R.id.map).setClickable(false);


        return vView;
    }



    void closefragment() {

        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }

}
