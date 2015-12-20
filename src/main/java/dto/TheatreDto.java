package dto;

public class TheatreDto {
    private String name;
    private Long id;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "TheatreDto{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
