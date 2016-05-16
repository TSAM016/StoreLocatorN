package milanomaps.undefined.tsam.app.locatorstore;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends ArrayAdapter<Store> {
    public UsersAdapter(Context context, ArrayList<Store> stores) {
        super(context, 0, stores);
    }

    Store stores;
    String negozio;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        stores = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.post_name);
        // Populate the data into the template view using the data object
        tvName.setText(stores.nome);
        negozio=stores.nome;
        ImageButton Jesse = (ImageButton) convertView.findViewById(R.id.Store_img);
        final String finalNegozio = negozio;
        Jesse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.clickMap(finalNegozio);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}