package eagleapp.com.holidaynotify.httprequest.enrico;

/**
 * Created by Pete on 3.11.2015.
 */
public enum Action {
    monthHolidays ("getPublicHolidaysForMonth");

    private final String name;

    private Action(String s){
        name = s;
    }

    public String getName(){
        return this.name;
    }

}
