package eagleapp.com.holidaynotify.httprequest.enrico.actions;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams;
import eagleapp.com.holidaynotify.utils.DateUtils;

/**
 * Created by Pete on 13.11.2015.
 */
public class HolidaysForDateRange extends EnricoAction {
    private String region;
    private Date fromDate;
    private Date toDate;

    public HolidaysForDateRange(){
        super();
        this.actionStr = EnricoParams.Actions.DATE_RANGE_HOLIDAYS;
        this.requiredKeys.add( EnricoParams.Keys.FROM_DATE );
        this.requiredKeys.add( EnricoParams.Keys.TO_DATE );
        this.requiredKeys.add( EnricoParams.Keys.COUNTRY );
    }

    public HolidaysForDateRange(String countryCode, String region, Date fromDate, Date toDate){
        this.countryCode = countryCode;
        this.region = region;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public Map<String, String> buildParamsMap() {
        Map<String, String> params = new HashMap<>();
        addToParams(params, EnricoParams.Keys.ACTION, this.actionStr);
        addToParams(params, EnricoParams.Keys.REGION, region);
        addToParams(params, EnricoParams.Keys.COUNTRY, countryCode);
        addToParams(params, EnricoParams.Keys.FROM_DATE, DateUtils.dateToString(EnricoParams.DATE_PATTERN, Locale.ENGLISH, fromDate));
        addToParams(params, EnricoParams.Keys.TO_DATE, DateUtils.dateToString(EnricoParams.DATE_PATTERN, Locale.ENGLISH, toDate));
        return params;
    }


}
