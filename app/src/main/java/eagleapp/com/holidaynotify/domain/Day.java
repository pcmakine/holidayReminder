package eagleapp.com.holidaynotify.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pete on 4.11.2015.
 */
public class Day implements Comparable{
    private Long id;
    private Date date;
    private String localName;
    private String englishName;
    private String notes;
    private Region region;
    private boolean notificationActive;

    public Day(Date date, String localName, String englishName, String notes){
        this(null, date, localName, englishName, notes, null, true);
    }

    public Day(Long id, Date date, String localName, String englishName, String notes, Region region, boolean notificationActive){
        this.id = id;
        this.date = date;
        this.localName = localName;
        this.englishName = englishName;
        this.notes = notes;
        this.region = region;
        this.notificationActive = notificationActive;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setLocalName(String localName){
        this.localName = localName;
    }
    public void setEnglishName(String englishName){
        this.englishName = englishName;
    }
    public Date getDate(){
        return this.date;
    }
    public String getLocalName(){
        return this.localName;
    }
    public String getEnglishName(){
        return this.englishName;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean isNotificationActive() {
        return notificationActive;
    }
    public void setNotificationActive(boolean notificationActive) {
        this.notificationActive = notificationActive;
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 31).     //two randomly chosen prime numbers TODO can we just always use 17 and 31, or should we have some randomization check somewhere?
                append(date).
                append(englishName).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof  Day)){
            return false;
        }
        if(obj == this){
            return true;
        }

        Day day = (Day) obj;
        return new EqualsBuilder().
                append(date, day.date).
                append(englishName, day.englishName).
                isEquals();
    }

    @Override
    public String toString(){
        String ret = "";
       // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = DateFormat.getDateInstance().format(date);
        ret = localName + " (" + englishName + ")\n" +dateStr ;
        return ret;
    }

    @Override
    public int compareTo(Object another) {
        Day day = (Day) another;
        if( this.date.before(day.getDate()) ){
            return -1;
        }else if( this.date.after(day.getDate()) ){
            return 1;
        }
        return 0;
    }
}
