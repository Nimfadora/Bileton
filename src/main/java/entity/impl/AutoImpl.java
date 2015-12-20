package entity.impl;

public class AutoImpl {
    private Long perfId;
    private Long spId;
    private long theatre;
    private String time;
    private String date;

    public Long getPerfId() {
        return perfId;
    }

    public void setPerfId(Long perfId) {
        this.perfId = perfId;
    }

    public Long getSpId() {
        return spId;
    }

    public void setSpId(Long spId) {
        this.spId = spId;
    }

    public long getTheatre() {
        return theatre;
    }

    public void setTheatre(long theatre) {
        this.theatre = theatre;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AutoImpl{" +
                "perfId=" + perfId +
                ", spId=" + spId +
                ", theatre=" + theatre +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
