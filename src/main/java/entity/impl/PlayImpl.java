package entity.impl;

public class PlayImpl {
    private Long id;
    private String date;
    private String time;
    private Long performance_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public Long getPerformance_id() {
        return this.performance_id;
    }

    public void setPerformance_id(Long performance_id) {
        this.performance_id = performance_id;
    }

    public PlayImpl(){}
}
