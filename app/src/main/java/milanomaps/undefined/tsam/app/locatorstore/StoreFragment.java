package milanomaps.undefined.tsam.app.locatorstore;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {

    JSONArray StoreJsonArray ;
    TwoWayView storeListView;
    public View vView;
    public static String keyp="";


    private static IOStoreInterface mListener= new IOStoreInterface() {
        @Override
        public void getObj(String nezozio) {
        }
    };

    public interface IOStoreInterface{
        void getObj(String negozio);
    }


    static public StoreFragment getInstance(JSONArray lispArray,String key){
        Bundle vBundle =new Bundle();
        vBundle.putString("arraylist",lispArray.toString());
        StoreFragment st1= new StoreFragment();
        st1.setArguments(vBundle);
         if(key!="") keyp=key;
        return st1;
    }


    public StoreFragment() {
        // Required empty public constructor
    }


    public void closeall(){
        Animation anim = AnimationUtils.loadAnimation(vView.getContext(), R.anim.right);
        vView.startAnimation(anim);
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle vBundle = getArguments();
        String vString = vBundle.getString("arraylist");
        try {
            StoreJsonArray= new JSONArray(vString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("jsoon", "andato in StoreFrag");
        vView = inflater.inflate(R.layout.fragment_store, container, false);
        storeListView = (TwoWayView) vView.findViewById(R.id.ListViewS);
        if(savedInstanceState!=null) StoreJsonArray= (JSONArray)savedInstanceState.get("List");
        ImageButton close = (ImageButton) vView.findViewById(R.id.close_menu);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeall();
            }
        });


            try {JSONObject evento;
                ArrayList<Store> arrayOfStore = new ArrayList<Store>();

                int k=0;int i=0;
                for (i = 0; i <StoreJsonArray.length(); i++) {

                    evento = (JSONObject) StoreJsonArray.get(i);

                    if(keyp=="") arrayOfStore.add(new Store(evento.getString("name"),1));
                    else if(evento.getString("name").toLowerCase().contains(keyp.toLowerCase())) arrayOfStore.add(new Store(evento.getString("name"),1));
                    else k++;
                }

                UsersAdapter adapter2;
                adapter2 = new UsersAdapter(vView.getContext(), arrayOfStore);
                storeListView.setAdapter(adapter2);
                if(k==i) {
                    closeall();



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Log.v("errorrrrrrrrrrr","poverocarlo");

        Animation anim = AnimationUtils.loadAnimation(this.vView.getContext(), R.anim.left);
        vView.startAnimation(anim);
        return vView;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity() instanceof IOStoreInterface)
        {
            mListener = (IOStoreInterface) getActivity();
        }
    }
    public static void openStore(String finalNegozio) {
        mListener.getObj(finalNegozio);
    }
}
