package cp3406.jcu.edu.au.guesstheceleb;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager manager = getAssets();
        try {
            String[] names = manager.list("celebs");
            System.out.println(Arrays.toString(names));
        } catch (IOException e){
            System.out.println("failed to get names");
        }
    }
}
