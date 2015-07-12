package com.tonyfeducia.spotifystreamer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class TracksActivity extends ActionBarActivity {

//    private static final String TAG_FRAGMENT = "tracks_fragment";
//    private TracksFragment tracksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);


        //Adding the artist name to to the Action Bar
        String extra = Intent.EXTRA_TITLE;
        String subTitle = "";
        Intent intent = getIntent();
        if ((intent != null) && intent.hasExtra(extra)) {
            subTitle = intent.getStringExtra(extra);
           if (!subTitle.isEmpty()) {
                ActionBar ab = getSupportActionBar();
                ab.setSubtitle(subTitle);
            }
        }

        // should manage the fragment across configuration changes
 //       FragmentManager fm = getSupportFragmentManager();
 //       tracksFragment = (TracksFragment) fm.findFragmentByTag(TAG_FRAGMENT);
 //       if (tracksFragment == null) {
 //           tracksFragment = new TracksFragment();
 //           fm.beginTransaction().add(tracksFragment, TAG_FRAGMENT).commit();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}