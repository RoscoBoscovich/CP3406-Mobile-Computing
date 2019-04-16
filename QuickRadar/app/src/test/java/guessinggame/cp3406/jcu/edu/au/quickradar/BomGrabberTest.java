package guessinggame.cp3406.jcu.edu.au.quickradar;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BomGrabberTest {

    @Test
    public void getTimeArrayTest() {

        // starting time and expected time array using starting time
        String time = "20190323:01:02";
        String[] timeArrayAnswer = {"201903230100","201903230054","201903230048","201903230042","201903230036","201903230030","201903230024"};

        BomGrabber grabber = new BomGrabber();
        grabber.timeUTC = time;
        grabber.getTimeArray();

        assertEquals(timeArrayAnswer, grabber.timesArray);
    }

    @Test
    public void getUrlArrayTest() {
        // testing to see compilation of urls happens correctly
        int range = 0;
        String[] times = {"201903230100","201903230054","201903230048","201903230042","201903230036","201903230030","201903230024"};
        String[] urlArrayAnswer = {"http://www.bom.gov.au/radar/IDR194.T.201903230100.png",
                "http://www.bom.gov.au/radar/IDR194.T.201903230054.png",
                "http://www.bom.gov.au/radar/IDR194.T.201903230048.png",
                "http://www.bom.gov.au/radar/IDR194.T.201903230042.png",
                "http://www.bom.gov.au/radar/IDR194.T.201903230036.png",
                "http://www.bom.gov.au/radar/IDR194.T.201903230030.png",
                "http://www.bom.gov.au/radar/IDR194.T.201903230024.png"};

        BomGrabber grabber = new BomGrabber();
        grabber.timesArray = times;
        grabber.getBOMUrls(range);

        assertEquals(urlArrayAnswer, grabber.urls);
    }

    @Test

    public void stepIndexTest() {

        // starting time and expected time array using starting time
        int indexAnswer = 3;


        BomGrabber grabber = new BomGrabber();
        grabber.animationIndex = 4;
        grabber.stepIndex();

        assertEquals(indexAnswer, grabber.animationIndex);
    }

}