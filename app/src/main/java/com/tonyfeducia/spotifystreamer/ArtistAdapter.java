package com.tonyfeducia.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Artist;


/**
 * Created by Tony on 7/8/2015.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {

    private ArrayList<Artist> items;
    private Context context;

    public ArtistAdapter(Context context, int resource, int textViewResourceId, ArrayList<Artist> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.artist_result_item, null);
        }

        Artist o = items.get(position);
        if (o != null) {
            TextView tt;
            ImageView img;

            tt = (TextView) v.findViewById(R.id.artist_result_text);
            if (tt == null) {
            } else {
                tt.setText(o.name);
            }

            img = (ImageView) v.findViewById(R.id.artist_result_icon);
            if (img == null) {
            } else {
                if (o.images == null || o.images.isEmpty()) {
                    Picasso.with(context)
                            .load(R.mipmap.ic_launcher)
                            .resize(200, 200)
                            .into(img);
                } else {
                    Picasso.with(context)
                            .load(o.images.get(0).url)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .resize(200, 200)
                            .into(img);
                }
            }
        }
        return v;
    }
}