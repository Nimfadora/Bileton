package entity.impl;

public class TicketImpl {
    private Integer place;
    private Integer row;
    private Long barcode;
    private String troupe;
    private String play;
    private String theatre;
    private String actors;
    private String price;
    private String date;
    private String time;
    private Integer category;



    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        String [] arr = date.split("-");
        this.date = arr[2] + "." + arr[1];
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        String [] arr = time.split(":");
        this.time = arr[0] + ":" + arr[1];
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(Long barcode) {
        this.barcode = barcode;
    }

    public String getTroupe() {
        return troupe;
    }

    public void setTroupe(String troupe) {
        this.troupe = troupe;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getTheatre() {
        return theatre;
    }

    public void setTheatre(String theatre) {
        this.theatre = theatre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String prices) {
        String [] pricesArr = prices.split("/");
        this.price = pricesArr[category-1];
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "TicketImpl{" +
                "place=" + place +
                ", row=" + row +
                ", barcode=" + barcode +
                ", troupe='" + troupe + '\'' +
                ", play='" + play + '\'' +
                ", theatre='" + theatre + '\'' +
                ", actors='" + actors + '\'' +
                ", price='" + price + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
