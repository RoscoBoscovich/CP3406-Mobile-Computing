package cp3406.jcu.edu.au.cairnsradar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;
    private Spinner spinner;
    int radarRange;
    int speed;
    public static final int SETTINGS_REQUEST = 333;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.range);
        spinner = findViewById(R.id.speedSpinner);

        // get the current settings from the RadarActivity intent
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            radarRange = extras.getInt("range");
            speed = extras.getInt("speed");
            seekBar.setProgress(radarRange);
            setRangeTextView(radarRange);
            spinner.setSelection(3 - (speed/500));
        }

        // change the value on the text at the seekbar is moved
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // when value changes
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                radarRange = progressValue;
                setRangeTextView(radarRange);
            }

            // the following two methods must be included for the onProgressChanged method to work
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    // when back is pressed return to radar and cancel changes
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "Changes Cancelled", Toast.LENGTH_SHORT).show();
        finish();
    }

    //method to get the speed value in milliseconds from the spinner
    private int getSpeedFromSpinner(String text){
        int speed = 1000;
        switch (text){
            case "Slow":
                speed = 1500;
                break;
            case "Medium":
                break;
            case "Fast":
                speed = 500;
                break;
        }
        return speed;
    }

    // changes the textview element to reflect the range selected on the seekbar
    private void setRangeTextView(int range){
        switch (range){
            case 0:
                textView.setText(R.string.range64);
                break;
            case 1:
                textView.setText(R.string.range128);
                break;
            case 2:
                textView.setText(R.string.range256);
                break;
        }
    }


    // when apply button is clicked the settings activity is finished and the selected
    // settings are passed through the intent
    public void doneClicked(View view){
        spinner = findViewById(R.id.speedSpinner);
        String text = spinner.getSelectedItem().toString();
        int speed = getSpeedFromSpinner(text);
        radarRange= seekBar.getProgress();
        Intent intent = new Intent();
        intent.putExtra("speed", speed);
        intent.putExtra("range", radarRange);
        setResult(RESULT_OK, intent);
        finish();
    }

}

