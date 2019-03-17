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
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BomGrabber extends DefaultHandler {


    private String urlBackground = "http://www.bom.gov.au/products/radar_transparencies/IDR192.background.png";
    private String urlLocationsOverlay = "http://www.bom.gov.au/products/radar_transparencies/IDR192.locations.png";
    private String urlRangeOverlay = "http://www.bom.gov.au//products/radar_transparencies/IDR192.range.png";
    private String urlRadarOverlay = "http://www.bom.gov.au/radar/IDR194.T.201903171206.png";



    public String getDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyymmdd:hh:mm");
        String strDate = dateFormat.format(date);
        String[] dateTime = strDate.split(":");
        int time = Integer.parseInt(dateTime[2]);
        while (time%6 != 0){
            time--;
        }
        String nearesttime = dateTime[0] + dateTime[1] + Integer.toString(time);

        
        return nearesttime;
    }

    public Bitmap[] returnImages() {
        Bitmap backgroundImage = this.getImage(urlBackground);
        Bitmap overlayLocations = this.getImage(urlLocationsOverlay);
        Bitmap overlayRange = this.getImage(urlRangeOverlay);
        Bitmap overlayRadar = this.getImage(urlRadarOverlay);
        Bitmap[] bitmapArray = new Bitmap[]{backgroundImage, overlayLocations, overlayRange, overlayRadar};

        return bitmapArray;
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

}
