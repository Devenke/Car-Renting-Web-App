package myprojects.car_renting.model;

import org.springframework.util.Base64Utils;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private String cost;

    @Column(name = "gear_type")
    private String gearType;

    @Column(name = "seats")
    private String seats;

    @Column(name = "available")
    private String available;

    @Column(name = "location")
    private String location;

    @Lob
    @Column(name = "image")
    private byte[] image;

    public Car() {
    }

    public Car(String title, String description, String cost, String gearType, String seats, String available, String location, byte[] image) {
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.gearType = gearType;
        this.seats = seats;
        this.available = available;
        this.location = location;
        this.image = image;
    }


    public String getImageBase64() {

        byte[] base64Array = Base64Utils.encode(image);
        String base64String = new String(base64Array);

        return base64String;
    }


    public String getAvailable() {
        return available;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String isAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", gearType='" + gearType + '\'' +
                ", seats='" + seats + '\'' +
                ", available=" + available +
                ", location='" + location + '\'' +
                '}';
    }
}