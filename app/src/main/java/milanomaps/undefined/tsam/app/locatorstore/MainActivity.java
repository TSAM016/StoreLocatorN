package milanomaps.undefined.tsam.app.locatorstore;


import android.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import android.support.design.widget.Snackbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;


import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity
    implements StoreFragment.IOStoreInterface, GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener,GetLogIn.IOLoginInterface,LogOutDialog.IOButtonSelectedDialog {



    private static GoogleMap mMap;
    static JSONArray mArrayList= new JSONArray();
    StoreFragment StoreList;
    GetLogIn LoginFragment=GetLogIn.getInstance();
    public static String session;
    public static TextView barracerca;


    @Override
    public void getArray(JSONArray aArray) {
        try {
            mArrayList=aArray;
            for(int i=0; i<aArray.length();i++){
                JSONObject mObject=(JSONObject) aArray.get(i);
                double latitudine = mObject.getDouble("latitude");
                double longitudine = mObject.getDouble("longitude");
                LatLng coor = new LatLng(latitudine,longitudine);
                mMap.addMarker(new MarkerOptions().position(coor).title(mObject.getString("name").toString()).snippet("Via"+mObject.getString("address").toString()) .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            }
            FragmentTransaction vTr = getFragmentManager().beginTransaction();
            vTr.remove(LoginFragment).commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getFragmentManager().findFragmentByTag("login")==null)
        {
            if (!haveNetworkConnection()){
                //Snackbar.make(getApplicationContext(), "Geolocalizzazione in corso", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Toast.makeText(getApplicationContext(),"Abilita internet",Toast.LENGTH_LONG).show();
            }
            FragmentTransaction vTr = getFragmentManager().beginTransaction();
            vTr.replace(R.id.container,LoginFragment,"login");
            vTr.commit();

        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        barracerca=(TextView) findViewById(R.id.barracerca);
        barracerca.setText("");
        barracerca.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {mostranegozi();}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        Button mostranegozibutton=(Button) findViewById(R.id.imageView2);
        mostranegozibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager().findFragmentByTag("login")==null)
                {

                    mostranegozi();

                }

            }
        });


        Button openmenu= (Button) findViewById(R.id.openmenu);
        openmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager().findFragmentByTag("login")==null)
                {

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);

                }

            }
        });


        Button gps =(Button) findViewById(R.id.buttongps);
       gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager().findFragmentByTag("login")==null) {
                    Snackbar.make(view, "Geolocalizzazione in corso ...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            LatLng milano = new LatLng(45.952585277632664, 12.680344046696518);
                            mMap.addMarker(new MarkerOptions().position(milano).title("Ti trovi qui"));
                            CameraUpdate center = CameraUpdateFactory.newLatLng(milano);
                            mMap.moveCamera(center);
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
                            mMap.animateCamera(zoom);
                        }
                    }.start();
                }

            }
        });
        barracerca.setEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*LatLng milano = new LatLng(45.4642700,9.1895100);
        mMap.addMarker(new MarkerOptions().position(milano).title("Milano"));

        CameraUpdate center= CameraUpdateFactory.newLatLng(milano);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(9);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);*/
        mMap.setOnInfoWindowClickListener(this);    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(getFragmentManager().findFragmentByTag("login")==null)
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_share) condividiapp();
        else if (id == R.id.nav_desktop) takeScreenshot();
        else if (id == R.id.nav_negozio) mostranegozi();
        else if (id == R.id.nav_trash) reloading();
        {//cancellacronologia();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);}
        return true;
    }
    private void reloading() {
        LogOutDialog vDialog = LogOutDialog.getInstance();
        vDialog.show(getSupportFragmentManager(),"LogOut");
    }
    private void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    private void mostranegozi() {
        if(getFragmentManager().findFragmentByTag("lista")==null)
        {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            StoreList= StoreFragment.getInstance(mArrayList,barracerca.getText().toString());
            FragmentTransaction vTr = getFragmentManager().beginTransaction();
            vTr.replace(R.id.container, StoreList, "login");
            vTr.commit();
        }

    }
    public void setSession(String session) {
        this.session = session;
    }
    private void condividiapp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey, sto usando questa applicazione per trovare i negozi+" +
                "\nScaricala anche tu: \nhttps://www.dropbox.com/s/ys3he23t0b4eah8/app-debug.apk?dl=0");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Condivici app"));
    }
    private void takeScreenshot() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        new CountDownTimer(400, 400) {
            public void onFinish() {
                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
                try {
                    // image naming and path  to include sd card  appending name you choose for file
                    String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
                    // create bitmap screen capture
                    View v1 = getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);
                    final File imageFile = new File(mPath);
                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/jpeg");
                    Uri uri = Uri.fromFile(imageFile);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(share, "Share Image"));
                } catch (Throwable e) {e.printStackTrace();}}
            public void onTick(long millisUntilFinished) {}}.start();
    }

    public static void clickMap(String negozio) {
        try {
            for(int i=0; i<mArrayList.length();i++){
                JSONObject mObject=(JSONObject) mArrayList.get(i);
                if(negozio.equals(mObject.getString("name")))
                {
                double latitudine = mObject.getDouble("latitude");
                double longitudine = mObject.getDouble("longitude");
                    LatLng waypoint = new LatLng(latitudine,longitudine);
                    CameraUpdate center= CameraUpdateFactory.newLatLng(waypoint);
                    mMap.moveCamera(center);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onButtonSelected(String aValue) {
        if(aValue.equals("+++")) {
            reload();
        }
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
    if( haveConnectedWifi || haveConnectedMobile) return true;
        else return false;
    }


    @Override
    public void getObj(String negozio) {
        try {
            for(int i=0; i<mArrayList.length();i++){
                JSONObject mObject=(JSONObject) mArrayList.get(i);
                if(negozio.equals(mObject.getString("name")))
                {
                    StoreDetailFragment StoreDetail= StoreDetailFragment.getInstance(mObject);
                    FragmentTransaction vTr = getFragmentManager().beginTransaction();
                    vTr.replace(R.id.container, StoreDetail, "login");
                    vTr.commit();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static String getSession() {
        return session;
    }

    @Override
    public void onInfoWindowClick(Marker marker){
        String finalNegozio=marker.getTitle().toString();
        getObj(finalNegozio);
    }
}
