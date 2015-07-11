package com.tonyfeducia.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * A placeholder fragment containing a simple view.
 */


public class ArtistFragment extends Fragment {
//    String LOG_TAG = "";

    private ArtistAdapter mArtistsAdapter;  //my custom adapter
    private ArrayList<Artist> artists;      //the array to hold the artists

    private String searchTerm; //user entered search phrase

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);  // this should retain this fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        LOG_TAG = getString(R.string.app_log_tag);

        if (artists == null)  //why do we need this check?
            artists = new ArrayList<Artist>();

        mArtistsAdapter = new ArtistAdapter(  //connecting the apapter
                getActivity(),
                R.layout.artist_result_item,
                R.id.artist_result_text,
                artists);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listview_artist_results);
        listView.setAdapter(mArtistsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = (Artist) parent.getAdapter().getItem(position);
            // onClick we are launching the detail activity
                Intent intent = new Intent(getActivity(), TracksActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, artist.id);  //sending the artist info over to the new activity
                intent.putExtra(Intent.EXTRA_TITLE, artist.name);
                startActivity(intent);
            }
        });

        EditText edtArtist = (EditText)rootView.findViewById(R.id.editText);
        edtArtist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) { updateArtists(s.toString()); }
        });

        return rootView;
    }

    /*
    * Compare the saved search argument and the current search argument;
    * Do the search only if the arguments don't match
    *
    * The deletion of the last character in the search box doesn't trigger the search because of the second 'if'.
    * I didn't intend to implement this behaviour but I like it better than blanking the list view
    * when the user deletes the last character in the search box.
    */
    private void updateArtists(String searchArgument) {
        if (!searchArgument.equals(this.searchTerm)) {
            this.searchTerm = searchArgument;

            if (!searchArgument.isEmpty()) {
                FetchArtistsTask task = new FetchArtistsTask();
                task.execute(searchArgument);
            }
        }
    }

    public class FetchArtistsTask extends AsyncTask<String, Void, ArrayList<Artist>> {
        @Override
        protected ArrayList<Artist> doInBackground(String... params) {
            artists = new ArrayList<Artist>();

//            try {
                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                ArtistsPager artistsPager = spotify.searchArtists(params[0]);

                for (Artist artist : artistsPager.artists.items) {
                    ArtistFragment.this.artists.add(artist);
                }
//            }
//            catch (Exception e) {
//                Log.e(LOG_TAG, e.getMessage());
//                artists = null;
//            }

            return artists;
        }

        @Override
        protected void onPostExecute(ArrayList<Artist> artists) {
            if (artists == null) {
                Toast toast = Toast.makeText(getActivity(), getString(R.string.error_search_artists), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                mArtistsAdapter.clear();

                for (Artist artist : artists) {
                    mArtistsAdapter.add(artist);
                }

                if (mArtistsAdapter.isEmpty()) {
                    Toast toast = Toast.makeText(getActivity(), getString(R.string.warn_empty_search_artists), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }
}