package eagleapp.com.holidaynotify.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by Pete on 7.11.2015.
 */
public class Country {
    private String fullName;
    private String countryCode;
    private Date from;
    private Date to;
    private List<String> regions;

    public Country(String fullName, String countryCode, Date from, Date to, List<String> regions) {
        this.fullName = fullName;
        this.countryCode = countryCode;
        this.from = from;
        this.to = to;
        this.regions = regions;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    @Override
    public String toString(){
        return this.fullName + " (" + countryCode + ")";
    }
}
