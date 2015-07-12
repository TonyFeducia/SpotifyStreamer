package com.tonyfeducia.spotifystreamer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class TracksActivity extends ActionBarActivity {

    private static final String TAG_FRAGMENT = "tracks_fragment";

    private TracksFragment mTracksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
//        setSubTitle();

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

        FragmentManager fm = getSupportFragmentManager();
        mTracksFragment = (TracksFragment)fm.findFragmentByTag(TAG_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTracksFragment == null) {
            mTracksFragment = new TracksFragment();
            fm.beginTransaction().add(mTracksFragment, TAG_FRAGMENT).commit();
        }
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

//    private void setSubTitle() {
//        String extra = Intent.EXTRA_TITLE;
//
//        String subTitle = "";
//        Intent intent = getIntent();
//        if ((intent != null) && intent.hasExtra(extra)) {
//            subTitle = intent.getStringExtra(extra);
//
//            if (!subTitle.isEmpty()) {
//                ActionBar ab = getSupportActionBar();
 //               ab.setSubtitle(subTitle);
 //           }
//        }
//    }
}