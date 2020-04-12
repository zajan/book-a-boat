package com.ajna.bookaboat.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "boats")
public class Boat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_type_id")
    private BoatType boatType;

    @Column(name = "people")
    private int people;

    @Column(name = "price")
    private int price;

    @Column(name = "power")
    private int power;

    @Column(name = "is_certificate_need", columnDefinition = "tinyint")
    private boolean isCertificateNeed;

    @Column(name = "year")
    private int year;

    @Column(name = "length")
    private int length;

    @OneToMany
    @JoinColumn(name = "boat_id")
    private Set<Photo> photos;

    public Boat() {
    }

    public Boat(int id, String name, String description, BoatType boatType, int people, int price, int power, boolean isCertificateNeed, int year, int length, Set<Photo> photos, Photo profilePhoto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.boatType = boatType;
        this.people = people;
        this.price = price;
        this.power = power;
        this.isCertificateNeed = isCertificateNeed;
        this.year = year;
        this.length = length;
        this.photos = photos;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatType(BoatType boatType) {
        this.boatType = boatType;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isCertificateNeed() {
        return isCertificateNeed;
    }

    public void setCertificateNeed(boolean certificateNeed) {
        isCertificateNeed = certificateNeed;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", boatType=" + boatType +
                ", people=" + people +
                ", price=" + price +
                ", power=" + power +
                ", isCertificateNeed=" + isCertificateNeed +
                ", year=" + year +
                ", length=" + length +
                ", photos=" + photos +
                '}';
    }
}
