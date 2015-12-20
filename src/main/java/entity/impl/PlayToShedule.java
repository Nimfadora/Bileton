package entity.impl;

public class PlayToShedule {
    private Long play_id;
    private Integer count;

    public Long getPlay_id() {
        return play_id;
    }

    public void setPlay_id(Long play_id) {
        this.play_id = play_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PlayToShedule{" +
                "play_id=" + play_id +
                ", count=" + count +
                '}';
    }
}
