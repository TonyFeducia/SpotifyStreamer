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

    private ArrayList<Artist> artistsArrayList;
    private Context context;

    public ArtistAdapter(Context context, int resource, int textViewResourceId, ArrayList<Artist> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
        this.artistsArrayList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View artistView = convertView;
        if (artistView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            artistView = inflater.inflate(R.layout.artist_result_item, null);
        }

        Artist artist = artistsArrayList.get(position);
        if (artist != null) {
            TextView artistName;
            ImageView artistIcon;

            artistName = (TextView) artistView.findViewById(R.id.artist_result_text);
            if (artistName == null) {
            } else {
                artistName.setText(artist.name);
            }

            artistIcon = (ImageView) artistView.findViewById(R.id.artist_result_icon);
            if (artistIcon == null) {
            } else {
                if (artist.images == null || artist.images.isEmpty()) {
                    Picasso.with(context)
                            .load(R.drawable.spotify)
                            .resize(200, 200)
                            .into(artistIcon);
                } else {
                    Picasso.with(context)
                            .load(artist.images.get(0).url)
                            .error(R.drawable.spotify)
                            .resize(200, 200)
                            .into(artistIcon);
                }
            }
        }
        return artistView;
    }
}