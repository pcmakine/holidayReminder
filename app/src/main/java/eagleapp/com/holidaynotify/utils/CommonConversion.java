package eagleapp.com.holidaynotify.utils;

/**
 * Created by Pete on 14.11.2015.
 */
public class CommonConversion {

    public static boolean intToBoolean(int value){
        return value == 1? true : false;
    }

    public static int booleanToInt(boolean value){
        return value? 1 : 0;
    }
}
