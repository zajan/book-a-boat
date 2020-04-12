package com.ajna.bookaboat.entity;

import javax.persistence.*;

@Entity
@Table(name = "photos")

public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;


    @Column(name = "boat_id")
    private int boatId;

    @Column(name = "is_default")
    private boolean isDefault;


    public Photo() {
    }

    public Photo(String name, int boatId, boolean isDefault) {
        this.name = name;
        this.boatId = boatId;
        this.isDefault = isDefault;
    }

    public Photo(int id, String name, int boatId, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.boatId = boatId;
        this.isDefault = isDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
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
                ", name='" + name + '\'' +
                ", boatId=" + boatId +
                ", isDefault=" + isDefault +
                '}';
    }
}