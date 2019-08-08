package helpers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateParsing {

    public static Date getDateFromForm(String date) {

        Date d = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            d = new Date(sdf.parse(date).getTime());
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return d;
    }


    public static String getStringFromDate(Date d) {
        String date;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(d);
        return date;
    }

    public static int getDaysFromDates(Date d1, Date d2) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        int days1 = Integer.parseInt(sdf.format(d1));
        int days2 = Integer.parseInt(sdf.format(d2));

        return days2-days1;

    }

}
