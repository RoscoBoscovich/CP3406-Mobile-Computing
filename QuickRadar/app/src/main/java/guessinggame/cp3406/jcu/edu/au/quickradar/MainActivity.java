package guessinggame.cp3406.jcu.edu.au.quickradar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// what is thread stuff??
        /// had to insert the following line to avoid exception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // stuff to initialise radar image
        BomGrabber grabber = new BomGrabber();
        Bitmap[] image = grabber.returnImages();
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        //imageView.setImageBitmap(image[0]);
//        imageView = (ImageView)findViewById(R.id.imageView2);
//        imageView.setImageBitmap(image[1]);
//        imageView = (ImageView)findViewById(R.id.imageView3);
//        imageView.setImageBitmap(image[2]);
        imageView = (ImageView)findViewById(R.id.imageView4);
        imageView.setImageBitmap(image[3]);


        String date = grabber.getDate();
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(date);


    }



    public void refreshRadar(View view) {
      //  Button button = (Button) view;
        BomGrabber grabber = new BomGrabber();
        Bitmap[] image = grabber.returnImages();
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap(image[0]);
        imageView = (ImageView)findViewById(R.id.imageView2);
        imageView.setImageBitmap(image[1]);
        imageView = (ImageView)findViewById(R.id.imageView3);
        imageView.setImageBitmap(image[2]);


    }

    public void gotoSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

}
