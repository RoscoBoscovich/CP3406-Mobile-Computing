package cp3406.jcu.edu.au.cairnsradar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class BomGrabber extends DefaultHandler {


    public Bitmap[] overlays = new Bitmap[7];
    public int animationIndex = 6;
    public String[] urls = new String[7];
    public String[] timesArray = new String[7];
    public String timeUTC;


    public void getUTCtime(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd:HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeUTC = dateFormat.format(date);
    }

    // makes a string array of the most recent time that the radar has been updated
    public void getTimeArray(){
        String[] dateTime = timeUTC.split(":");
        int timeMins = Integer.parseInt(dateTime[2]);
        int timeHours = Integer.parseInt(dateTime[1]) ;

        //radar updated every six mins stating on the hour so find nearest past multiple of 6
        while (timeMins%6 != 0){
            timeMins--;
        }

        //only six radar images are ever available but take between 0 and 10 mins to update
        //so make seven urls and one image will comes back null
        for (int i=0; i<7; i++){
            timesArray[i] = dateTime[0] + String.format(Locale.US,"%02d", timeHours)
                    + String.format(Locale.US,"%02d", timeMins);
            if (timeMins > 1) {
                timeMins -= 6;
            }else{
                timeMins = 54;
                timeHours--;
            }
        }
    }

    //  compile an array of urls for whichever radar range is selected
    public void getBOMUrls(int range) {
        String url64km = "http://www.bom.gov.au/radar/IDR194.T.";
        String url128km = "http://www.bom.gov.au/radar/IDR193.T.";
        String url256km = "http://www.bom.gov.au/radar/IDR192.T.";
        for (int i=0; i<7; i++){
            switch (range) {
                case 0:
                    urls[i] = url64km + timesArray[i]  +".png";
                    break;
                case 1:
                    urls[i] = url128km + timesArray[i]  +".png";
                    break;
                case 2:
                    urls[i] = url256km + timesArray[i]  +".png";
            }
        }
    }

    // retrieves a bitmap of the radar overlay from the BOM server
    private Bitmap getImage(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();
            return bitmap;

        } catch (IOException ioe) {
            return null;
        }
    }

    //main method that gets an array of the bitmap overlays
    public void getOverlays(int range){
        this.getUTCtime();
        this.getTimeArray();
        this.getBOMUrls(range);
        for (int i = 0; i < 7; i++) {
            overlays[i] = this.getImage(urls[i]);
        }
    }

    // steps down the index for playing the animation (oldest to newest)
    public void stepIndex(){
        if (animationIndex==0){
            animationIndex = 6;
        }else{
            animationIndex--;
        }
    }
}
