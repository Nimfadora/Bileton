package dto;

public class PerformanceDto {
    private Long id;
    private Long troupe_id;
    private String troupe;
    private Long spectacle_id;
    private String spectacle;
    private String category;

    public PerformanceDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTroupe() {
        return troupe;
    }

    public void setTroupe(String troupe) {
        this.troupe = troupe;
    }

    public String getSpectacle() {
        return spectacle;
    }

    public void setSpectacle(String spectacle) {
        this.spectacle = spectacle;
    }

    public Long getTroupe_id() {
        return troupe_id;
    }

    public void setTroupe_id(Long troupe_id) {
        this.troupe_id = troupe_id;
    }

    public Long getSpectacle_id() {
        return spectacle_id;
    }

    public void setSpectacle_id(Long spectacle_id) {
        this.spectacle_id = spectacle_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PerformanceDto{" +
                "id=" + id +
                ", troupe_id=" + troupe_id +
                ", troupe='" + troupe + '\'' +
                ", spectacle_id=" + spectacle_id +
                ", spectacle='" + spectacle + '\'' +
                '}';
    }
}
