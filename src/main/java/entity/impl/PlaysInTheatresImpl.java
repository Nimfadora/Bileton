package entity.impl;

public class PlaysInTheatresImpl {
    private String name;
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PlaysInTheatresImpl{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
