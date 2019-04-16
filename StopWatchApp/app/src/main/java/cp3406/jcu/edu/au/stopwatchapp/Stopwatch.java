package cp3406.jcu.edu.au.stopwatchapp;

public class Stopwatch {

    private int hours;
    private int minutes;
    private int seconds;

    Stopwatch(){
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    Stopwatch(String time){
        String[] split_time = time.split(":");
        this.hours = Integer.parseInt(split_time[0]);
        this.minutes = Integer.parseInt(split_time[1]);
        this.seconds = Integer.parseInt(split_time[2]);
    }


    public void setTime(String sting){
        String[] time = sting.split(":");
        this.hours = Integer.parseInt(time[0]);
        this.minutes = Integer.parseInt(time[1]);
        this.seconds = Integer.parseInt(time[2]);
    }


    public String toString(){
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    public void tick(){
       seconds++;
       if (seconds == 60){
           seconds = 0;
           minutes++;
       }
       if (minutes == 60){
           minutes = 0;
           hours++;
       }
    }


}
