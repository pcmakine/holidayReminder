package eagleapp.com.holidaynotify.httprequest.enrico.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams;

/**
 * Created by Pete on 5.11.2015.
 */
public class YearHolidays extends EnricoAction {
    private int year;
    private String region;

    public YearHolidays(){
        this.actionStr = EnricoParams.Actions.YEAR_HOLIDAYS;
        this.requiredKeys.add( EnricoParams.Keys.YEAR );
    }

    @Override
    public Map<String, String> buildParamsMap() {
        Map<String, String> params = new HashMap<>();
        params.put(EnricoParams.Keys.ACTION, this.actionStr);
        params.put(EnricoParams.Keys.YEAR, String.valueOf(year));
        params.put(EnricoParams.Keys.COUNTRY, countryCode);
        params.put(EnricoParams.Keys.REGION, region);
        return params;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
}
