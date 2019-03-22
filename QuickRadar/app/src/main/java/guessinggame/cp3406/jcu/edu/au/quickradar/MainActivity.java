package guessinggame.cp3406.jcu.edu.au.quickradar;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public BomGrabber grabber = new BomGrabber();
    final Handler handler = new Handler();
    public int rangeInt = 1;
    TextView textView;
    ImageView imageViewOverlay;
    ImageView imageViewBackground;
    ImageView imageViewLocations;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// what is thread stuff??
        /// had to insert the following lines to avoid IoOnMainThread exception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
        imageViewLocations = (ImageView)findViewById(R.id.locationsOverlay);
        imageViewBackground = (ImageView)findViewById(R.id.imageViewBackground);
        switch (rangeInt) {
            case 0:
                imageViewBackground.setImageResource(R.drawable.background64km);
                imageViewLocations.setImageResource(R.drawable.locations64km);
                Toast.makeText(getApplicationContext(), "64km range", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                imageViewBackground.setImageResource(R.drawable.background128km);
                imageViewLocations.setImageResource(R.drawable.locations128km);
                Toast.makeText(getApplicationContext(), "128km range", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                imageViewBackground.setImageResource(R.drawable.background256km);
                imageViewLocations.setImageResource(R.drawable.locations256km);
                Toast.makeText(getApplicationContext(), "256km range", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                imageViewBackground.setImageResource(R.drawable.background64km);
                imageViewLocations.setImageResource(R.drawable.locations64km);
                Toast.makeText(getApplicationContext(), "512km range", Toast.LENGTH_SHORT).show();
                break;
        }

        // get overlays and remove any animation runnables already on the stack
        // then post new animation runnable
        grabber.getOverlays();
        handler.removeCallbacksAndMessages(null);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updateAnimation();
                    handler.postDelayed(this, 500);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(1, resultCode, data);
        if (requestCode == Settings.SETTINGS_REQUEST){
            if (resultCode == RESULT_OK){
                if (data != null){
                    rangeInt = data.getIntExtra("range", 0);

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, Settings.class);
        startActivityForResult(intent,Settings.SETTINGS_REQUEST);
        return super.onOptionsItemSelected(item);
    }

    public void updateAnimation (){
        imageViewOverlay = (ImageView)findViewById(R.id.radarOverlay);
        textView = (TextView)findViewById(R.id.textView);

        // first or seventh frame will sometimes be missing due to arbitrary updating of radar
        // so discard any frames that come back null
        while (grabber.overlays[grabber.animationIndex] == null){
            grabber.stepIndex();
        }
        imageViewOverlay.setImageBitmap(grabber.overlays[grabber.animationIndex]);
        textView.setText(String.format("%d", grabber.animationIndex));
        grabber.stepIndex();
    }

    public void refreshRadar(View view){
        grabber.getOverlays();
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
    }

    public void gotoSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivityForResult(intent,Settings.SETTINGS_REQUEST);
    }
}
