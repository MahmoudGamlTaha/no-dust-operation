package MTS.security.MTSClasses;

public class DailyAssignedStreet {
    public DailyAssignedStreet() {
        super();
    }
    private int city_id;
    private int region_id;
    private int area_id;
    private int street_id;

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setStreet_id(int street_id) {
        this.street_id = street_id;
    }

    public int getStreet_id() {
        return street_id;
    }
}
