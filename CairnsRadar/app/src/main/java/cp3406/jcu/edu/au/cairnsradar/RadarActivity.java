package cp3406.jcu.edu.au.cairnsradar;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class RadarActivity extends AppCompatActivity {


    public BomGrabber grabber;
    private Handler handler;
    private int rangeInt;
    private int defaultRange = 1; // default range
    private int playbackSpeed;
    private final int defaultSpeed = 500;  //default playback speed
    TextView textView;
    ImageView imageViewOverlay;
    ImageView imageViewBackground;
    ImageView imageViewLocations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        /// had to insert the following lines to avoid IoOnMainThread exception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        grabber = new BomGrabber();
        handler = new Handler();
        imageViewLocations = findViewById(R.id.locationsOverlay);
        imageViewBackground = findViewById(R.id.imageViewBackground);


        // get speed and range from last instance state else set defaults
        if (savedInstanceState != null){
            rangeInt = savedInstanceState.getInt("range", 1);
            playbackSpeed = savedInstanceState.getInt("speed", 500);
        }else{
            rangeInt = defaultRange;
            playbackSpeed = defaultSpeed;
        }

        // set background and overlays for selected range
        switch (rangeInt) {
            case 0:
                imageViewBackground.setImageResource(R.drawable.background64km);
                imageViewLocations.setImageResource(R.drawable.locations64km);
//                Toast.makeText(getApplicationContext(), "64km range", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                imageViewBackground.setImageResource(R.drawable.background128km);
                imageViewLocations.setImageResource(R.drawable.locations128km);
//                Toast.makeText(getApplicationContext(), "128km range", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                imageViewBackground.setImageResource(R.drawable.background256km);
                imageViewLocations.setImageResource(R.drawable.locations256km);
//                Toast.makeText(getApplicationContext(), "256km range", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_radar);

        // get overlays for chosen range and remove any animation runnables already in the handler
        // then post new animation runnable
        grabber.getOverlays(rangeInt);
        handler.removeCallbacksAndMessages(null);
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateAnimation();
                handler.postDelayed(this, playbackSpeed);
            }
        });
    }

    // pass range and speed info for when app stop/starts
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("range", rangeInt);
        outState.putInt("speed", playbackSpeed);
    }

    // get results from setting screen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(1, resultCode, data);
        if (requestCode == SettingsActivity.SETTINGS_REQUEST){
            if (resultCode == RESULT_OK){
                if (data != null){
                    rangeInt = data.getIntExtra("range", 0);
                    playbackSpeed = data.getIntExtra("speed", 1000);
                    Toast.makeText(getApplicationContext(), "Settings applied", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    // add settings icon in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // go to settings activity when button pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("range", rangeInt);
        intent.putExtra("speed", playbackSpeed);
        startActivityForResult(intent,SettingsActivity.SETTINGS_REQUEST);
        return super.onOptionsItemSelected(item);
    }

    // method to update the animation overlay
    public void updateAnimation (){
        imageViewOverlay = findViewById(R.id.radarOverlay);
        textView = findViewById(R.id.textView);

        // first or seventh frame will sometimes be missing due to arbitrary updating of radar
        // so discard any frames that come back null
        while (grabber.overlays[grabber.animationIndex] == null){
            grabber.stepIndex();
        }
        imageViewOverlay.setImageBitmap(grabber.overlays[grabber.animationIndex]);
        grabber.stepIndex();
    }

    // when refresh button is pressed the BOM radar images are updated
    public void refreshRadar(View view){
        grabber.getOverlays(rangeInt);
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
    }

}
