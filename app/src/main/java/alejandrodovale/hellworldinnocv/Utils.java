package alejandrodovale.hellworldinnocv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Alejandro Dovale on 22/09/2017.
 */

public class Utils {

    public static final java.lang.String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static String getFecha(){
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(d);
    }
}
