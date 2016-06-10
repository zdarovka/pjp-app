package cz.tul.code;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        int year = 2016;
        int month = randomNumber(1, 12);
        int day = randomNumber(1, 28);
        int hour = randomNumber(1, 23);
        int minute = randomNumber(1, 59);

        String date = year + "/" + month + "/" + day + " " + hour + ":" + minute;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date utilDate = null;
        try {
            utilDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return utilDate;
    }
}
