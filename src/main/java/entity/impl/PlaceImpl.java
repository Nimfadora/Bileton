package entity.impl;

public class PlaceImpl {
    private long id;
    private long audienceId;
    private int category;
    private int rowNum;
    private int placeNum;
    private boolean state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(long audienceId) {
        this.audienceId = audienceId;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(int placeNum) {
        this.placeNum = placeNum;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PlaceImpl{" +
                "id=" + id +
                ", audienceId=" + audienceId +
                ", category=" + category +
                ", rowNum=" + rowNum +
                ", placeNum=" + placeNum +
                ", state=" + state +
                '}';
    }
}
