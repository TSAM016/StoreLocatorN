package milanomaps.undefined.tsam.app.locatorstore;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailFragment extends Fragment {
    View vView;
    JSONObject StoreJsonObject ;
    TextView prodottit;
    static public StoreDetailFragment getInstance(JSONObject lispObject){
        Bundle vBundle =new Bundle();
        vBundle.putString("ObjectJSON",lispObject.toString());
        StoreDetailFragment st1= new StoreDetailFragment();
        st1.setArguments(vBundle);
        return st1;
    }

    public StoreDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vView=inflater.inflate(R.layout.fragment_store_detail, container, false);
        Bundle vBundle = getArguments();
        String vString = vBundle.getString("ObjectJSON");
        TextView titolo=(TextView) vView.findViewById(R.id.detail_titolo);
        TextView phone=(TextView) vView.findViewById(R.id.detail_phone);
        TextView via=(TextView) vView.findViewById(R.id.detail_via);
        prodottit=(TextView) vView.findViewById(R.id.prodottit);
        Button close=(Button) vView.findViewById(R.id.close_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeall();
            }
        });
        try {
            StoreJsonObject= new JSONObject(vString);
            titolo.setText(""+StoreJsonObject.getString("name").toString());
            phone.setText(""+StoreJsonObject.getString("phone").toString());
            via.setText(""+StoreJsonObject.getString("address").toString());

            String params= MainActivity.getSession();
        HttpRest.postD("v2/stores/"+StoreJsonObject.getString("guid").toString(), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray event;
                try {
                    JSONObject obj = (JSONObject)response.get("data");
                    JSONArray prodotti = (JSONArray) obj.get("products");
                    for(int i=0;i<prodotti.length();i++)
                    {
                        JSONObject prodotto=(JSONObject) prodotti.get(0);
                        String nome=prodotto.getString("name");
                        String price=prodotto.getString("price");
                        prodottit.setText(prodottit.getText().toString()+nome+"\t"+price+"\n");
                    }

                    Log.d("Prodotti","stampati");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("jsoon", "letto get");

            }
        });
        }
        catch (JSONException e) {
                e.printStackTrace();
        }

        return vView;
    }
    public void closeall(){
        Animation anim = AnimationUtils.loadAnimation(vView.getContext(), R.anim.exit);
        vView.startAnimation(anim);
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }

}

