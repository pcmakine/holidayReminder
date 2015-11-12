package eagleapp.com.holidaynotify.domain;

/**
 * Created by Pete on 11.11.2015.
 */
public class Region {
    public static final String DUMMY_REGION_NAME = "dummyRegion";
    private Long id;
    private String name;
    private String countryCode;

    public Region(Long id, String name, String countryCode){
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
