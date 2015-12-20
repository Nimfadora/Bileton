package dto;

import java.util.Arrays;

public class PlayReportDto {
    private String name;
    private String theatre;
    private String troupe;
    private String date;
    private String time;
    private String summary;
    private String starring;
    private int[] prices;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStarring() {return starring;}

    public void setStarring(String starring) {this.starring = starring;}

    public void setName(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public void setTheatre(String theatre) {this.theatre = theatre;}

    public String getTheatre() {
        return theatre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        String [] arr = date.split("-");
        this.date = arr[2] + "." + arr[1];
    }

    public String getTroupe() {
        return troupe;
    }

    public void setTroupe(String troupe) {
        this.troupe = troupe;
    }

    public String getTime() {return time;}

    public void setTime(String time) {
        String [] arr = time.split(":");
        this.time = arr[0] + ":" + arr[1];
    }

    public String getSummary() {return summary;}

    public void setSummary(String summary) {this.summary = summary;}

    public int[] getPrices() {return prices;}

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public PlayReportDto(){}

    @Override
    public String toString() {
        return "PlayReportDto{" +
                "name='" + name + '\'' +
                ", theatre='" + theatre + '\'' +
                ", troupe='" + troupe + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", summary='" + summary + '\'' +
                ", starring='" + starring + '\'' +
                ", prices=" + Arrays.toString(prices) +
                '}';
    }
}
