package com.tonyfeducia.spotifystreamer;

/**
 * Created by Tony on 7/10/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

public class TracksAdapter extends ArrayAdapter<Track> {

    private ArrayList<Track> tracksArrayList;
    private Context context;

    public TracksAdapter(Context context, int resource, int textViewResourceId, ArrayList<Track> tracks) {
        super(context, resource, textViewResourceId, tracks);

        this.context = context;
        this.tracksArrayList = tracks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View tracksView = convertView;

        if (tracksView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            tracksView = inflater.inflate(R.layout.tracks_item, null);
        }

        Track track = tracksArrayList.get(position);
        if (track != null) {
            TextView trackName;
            ImageView trackIcon;

            trackName = (TextView) tracksView.findViewById(R.id.tracks_text);
            if (trackName == null) {
            } else {
                trackName.setText(track.name);
            }

            trackName = (TextView) tracksView.findViewById(R.id.tracks_subtext);
            if (trackName == null) {
            } else {
                trackName.setText(track.album.name);
            }

            trackIcon = (ImageView) tracksView.findViewById(R.id.tracks_icon);
            if (trackIcon == null) {
            } else {
                if (track.album.images == null || track.album.images.isEmpty()) {
                    Picasso.with(context)
                            .load(R.drawable.spotify)
                            .resize(200, 200)
                            .into(trackIcon);
                } else {
                    Image image;
                    if (track.album.images.size() >= 2)
                        image = track.album.images.get(1);
                    else
                        image = track.album.images.get(0);

                    Picasso.with(context)
                            .load(image.url)
                            .error(R.drawable.spotify)
                            .resize(200, 200)
                            .into(trackIcon);
                }
            }
        }
        return tracksView;
    }
}