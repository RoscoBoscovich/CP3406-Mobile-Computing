package guessinggame.cp3406.jcu.edu.au.quickradar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
    private String[] urls = new String[7];
    private String[] timesArray = new String[7];
    private String timeUTC;
    public int animationIndex = 6;

    private void getUTCtime(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd:HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeUTC = dateFormat.format(date);
    }


    private void getTimeArray(){
        this.getUTCtime();
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


    private void getBOMUrls() {
        this.getTimeArray();
        for (int i=0; i<7; i++){
            urls[i] ="http://www.bom.gov.au/radar/IDR192.T." + timesArray[i]  +".png";
        }
    }


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

    public void getOverlays(){
        this.getBOMUrls();
        for (int i = 0; i < 7; i++) {
            overlays[i] = this.getImage(urls[i]);
        }
    }

    public void stepIndex(){
        if (animationIndex==0){
            animationIndex = 6;
        }else{
            animationIndex--;
        }
    }
}

