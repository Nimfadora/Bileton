package entity.impl;

public class TicketStatisticsImpl {
    private  String spectacle;
    private int ticketsSold;
    private Long playId;
    private int total;

    public String getSpectacle() {
        return spectacle;
    }

    public void setSpectacle(String spectacle) {
        this.spectacle = spectacle;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Long getPlayId() {
        return playId;
    }

    public void setPlayId(Long playId) {
        this.playId = playId;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "TicketStatisticsImpl{" +
                "spectacle='" + spectacle + '\'' +
                ", ticketsSold=" + ticketsSold +
                ", playId=" + playId +
                ", total=" + total +
                '}';
    }
}
