package entity.impl;


public class PlayAuto {
    private String spectacle;
    private Long perfId;
    private Long spId;

    public String getSpectacle() {
        return spectacle;
    }

    public void setSpectacle(String spectacle) {
        this.spectacle = spectacle;
    }

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

    @Override
    public String toString() {
        return "PlayAuto{" +
                "spectacle='" + spectacle + '\'' +
                ", perfId=" + perfId +
                ", spId=" + spId +
                '}';
    }
}
