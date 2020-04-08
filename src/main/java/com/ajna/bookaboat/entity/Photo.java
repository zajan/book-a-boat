package com.ajna.bookaboat.entity;

import javax.persistence.*;

@Entity
@Table(name = "photos")

public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat")
    private Boat boat;

    @Column(name = "is_default")
    private boolean isDefault;


    public Photo() {
    }

    public Photo(int id, String url, Boat boat, boolean isDefault) {
        this.id = id;
        this.url = url;
        this.boat = boat;
        this.isDefault = isDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", boat=" + boat +
                ", isDefault=" + isDefault +
                '}';
    }
}