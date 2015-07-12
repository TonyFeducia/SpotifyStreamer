package com.tonyfeducia.spotifystreamer;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


public class TracksFragment extends Fragment {

    //following the process of the artist fragment
    TracksAdapter mTracksAdapter;
    private String searchTerm;
    ArrayList<Track> tracks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // should retain this fragment on rotation
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        LOG_TAG = getString(R.string.app_log_tag);

        if (tracks == null)
            tracks = new ArrayList<Track>();

        mTracksAdapter = new TracksAdapter(
                getActivity(),
                R.layout.tracks_item,
                R.id.tracks_text,
                tracks);

        View rootView = inflater.inflate(R.layout.fragment_tracks, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listview_tracks);
        listView.setAdapter(mTracksAdapter);

        String extra = Intent.EXTRA_TEXT;
        String artistID = "";
        Intent intent = getActivity().getIntent();
        if ((intent != null) && intent.hasExtra(extra)) {
            artistID = intent.getStringExtra(extra);
        }
        updateTracks(artistID);

        return rootView;
    }

    private void updateTracks(String searchTerm) {
        if (!searchTerm.equals(this.searchTerm)) {
            this.searchTerm = searchTerm;

            FetchTracksTask task = new FetchTracksTask();
            task.execute(searchTerm);
        }
    }

    public class FetchTracksTask extends AsyncTask<String, Void, ArrayList<Track>> {
        @Override
        protected ArrayList<Track> doInBackground(String... params) {
            tracks = new ArrayList<Track>();

            //need this for the spotify API top track call
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("country", "US");

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            Tracks tracks = spotify.getArtistTopTrack(params[0], queryMap);

            for (Track track : tracks.tracks) {
                TracksFragment.this.tracks.add(track);
            }

            return TracksFragment.this.tracks;
        }

        @Override
        protected void onPostExecute(ArrayList<Track> tracks) {
            if (tracks == null) {
                Toast toast = Toast.makeText(getActivity(), "No Tracks Found for this Artist", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                mTracksAdapter.clear();
                for (Track track : tracks) {
                    mTracksAdapter.add(track);
                }
                if (mTracksAdapter.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), "No Tracks Found for this Artist", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }
}