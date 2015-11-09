package eagleapp.com.holidaynotify.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Pete on 7.11.2015.
 */
public class Country {
    private Long id;
    private String fullName;
    private String countryCode;
    private Date from;
    private Date to;
    private Set<String> regions;

    public Country(String fullName, String countryCode, Date from, Date to, Set<String> regions) {
        this(null, fullName, countryCode, from, to, regions);
    }

    public Country(Long id, String fullName, String countryCode, Date from, Date to, Set<String> regions){
        this.id = id;
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

    public Set<String> getRegions() {
        return regions;
    }

    public void setRegions(Set<String> regions) {
        this.regions = regions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.fullName + " (" + countryCode + ")";
    }
}
