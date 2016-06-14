package cz.tul.code;



import java.util.Date;
import java.util.Random;

/**
 * Created by zdars on 6/10/2016.
 */
public final class DataHelper {

    private DataHelper() {

    }

    public static Date randomDate() {
        Random r =new Random();
        Date utilDate = new Date((long) Math.abs(System.currentTimeMillis() - r.nextDouble()*60*60*24*365*1000));
        return utilDate;
    }
}
