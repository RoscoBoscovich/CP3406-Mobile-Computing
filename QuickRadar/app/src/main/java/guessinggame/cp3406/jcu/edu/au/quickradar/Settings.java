package guessinggame.cp3406.jcu.edu.au.quickradar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private Spinner spinner;
    private SeekBar seekBar;
    private String selection;
    private TextView textView;
//    Intent intent;
    Bundle bundle;
    int radarRange;
    public static final int SETTINGS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.range);
        spinner = (Spinner) findViewById(R.id.mapStyles);

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
                    case 3:
                        textView.setText(R.string.range512);
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
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent();
        radarRange= seekBar.getProgress();
        intent.putExtra("range", radarRange);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void doneClicked(View view){
        radarRange= seekBar.getProgress();
        Intent intent = new Intent();
        intent.putExtra("range", radarRange);
        setResult(RESULT_OK, intent);
        finish();
    }

}

