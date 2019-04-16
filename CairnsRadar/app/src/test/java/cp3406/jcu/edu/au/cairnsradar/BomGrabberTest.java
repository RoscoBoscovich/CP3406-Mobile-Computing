package cp3406.jcu.edu.au.cairnsradar;

import org.junit.Assert;
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

        // testing to see if calculation of radar times happens correctly using test input UTC time
        String time = "20190323:01:02";
        String[] timeArrayAnswer = {"201903230100","201903230054","201903230048","201903230042","201903230036","201903230030","201903230024"};

        BomGrabber grabber = new BomGrabber();
        grabber.timeUTC = time;
        grabber.getTimeArray();

        Assert.assertArrayEquals(timeArrayAnswer, grabber.timesArray);
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

        Assert.assertArrayEquals(urlArrayAnswer, grabber.urls);
    }

    @Test
    public void stepIndexTest() {
        // testing to see the stepindex method will cycle the animation properly
        // starting index and expected array of indexes for ten steps
        int[] indexAnswerExpected = {3, 2, 1, 0, 6, 5, 4, 3, 2, 1};
        int indexAnswerReal[] = new int[10];

        BomGrabber grabber = new BomGrabber();
        grabber.animationIndex = 4;  // starting index

        for (int i = 0; i < 10; i++) {
            grabber.stepIndex();
            indexAnswerReal[i] = grabber.animationIndex;
        }

        Assert.assertArrayEquals(indexAnswerExpected, indexAnswerReal);
    }
}