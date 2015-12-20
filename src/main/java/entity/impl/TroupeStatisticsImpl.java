package entity.impl;

public class TroupeStatisticsImpl {
    private String troupe;
    private int countActors;

    public String getTroupe() {
        return troupe;
    }

    public void setTroupe(String troupe) {
        this.troupe = troupe;
    }

    public int getCountActors() {
        return countActors;
    }

    public void setCountActors(int countActors) {
        this.countActors = countActors;
    }

    @Override
    public String toString() {
        return "TroupeStatisticsImpl{" +
                "troupe='" + troupe + '\'' +
                ", countActors=" + countActors +
                '}';
    }
}
