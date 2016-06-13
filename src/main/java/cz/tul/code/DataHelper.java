package cz.tul.code;



import java.util.Date;
import java.util.Random;

/**
 * Created by zdars on 6/10/2016.
 */
public final class DataHelper {

    private DataHelper() {

    }

    public static int randomNumber(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public static Date randomDate() {
        //Random date from 2016 to 2017
        Random r =new Random();
        long unixtime =(long) (1451606400+r.nextDouble()*60*60*24*365*1000);
        Date utilDate = new Date(unixtime);

        return utilDate;
    }
}
