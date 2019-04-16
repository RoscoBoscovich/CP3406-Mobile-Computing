package guessinggame.cp3406.jcu.edu.au.quickradar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

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


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.range);
        spinner = (Spinner) findViewById(R.id.speedSpinner);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int radarRange = 0;
            // when value changes
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                radarRange = progressValue;
                switch (progressValue){
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

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
            radarRange = intent.getIntExtra("range", 1);
            speed = intent.getIntExtra("speed", 1000);
            seekBar.setProgress(radarRange);
            spinner.setSelection((speed/500)-1);
            Toast.makeText(getApplicationContext(), String.format("range %d",radarRange), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "Changes Cancelled", Toast.LENGTH_SHORT).show();
        finish();
    }

    public int getSpeedFromSpinner(String text){
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


    public void doneClicked(View view){
        spinner = (Spinner) findViewById(R.id.speedSpinner);
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

