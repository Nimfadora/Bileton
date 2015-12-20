package entity.impl;

import java.util.Comparator;

public class PlayReportImpl{
    private Long id;
    private String name;
    private String theatre;
    private String troupe;
    private String date;
    private String time;
    private String summary;
    private String starring;
    private int[] prices;

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
        this.date = date;
    }

    public String getTroupe() {
        return troupe;
    }

    public void setTroupe(String troupe) {
        this.troupe = troupe;
    }

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public String getSummary() {return summary;}

    public void setSummary(String summary) {this.summary = summary;}

    public int[] getPrices() {return prices;}

    public void setPrices(String prices) {
        String [] pricesArr = prices.split("/");
        this.prices = new int[pricesArr.length];
        for (int i = 0; i < pricesArr.length; i++) {
            this.prices[i]=Integer.parseInt(pricesArr[i]);
        }
    }

    public PlayReportImpl(){}

    public static Comparator<PlayReportImpl> comparatorAsc = (p1, p2) -> {
        Integer price1 = p1.getPrices()[0];
        Integer price2 = p2.getPrices()[0];

        return price1.compareTo(price2);
    };
    public static Comparator<PlayReportImpl> comparatorDesc = (p1, p2) -> {
        Integer price1 = p1.getPrices()[0];
        Integer price2 = p2.getPrices()[0];

        return price2.compareTo(price1);
    };
}
