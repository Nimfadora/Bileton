package entity.impl;

public class PlaysDuringSevenDaysImpl {
    private String theatre;
    private Integer count;

    public String getTheatre() {
        return theatre;
    }

    public void setTheatre(String theatre) {
        this.theatre = theatre;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PlaysDuringSevenDaysImpl{" +
                "theatre='" + theatre + '\'' +
                ", count=" + count +
                '}';
    }
}
