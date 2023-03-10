package com.freestack;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "endbooking")
    private LocalDateTime endBooking;

    @Column(name = "evaluation")
    private Integer score;

    @Column(name = "startbooking")
    private LocalDateTime startBooking;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private UberDriver driver;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UberUser user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEndBooking() {
        return endBooking;
    }

    public void setEndBooking(LocalDateTime endBooking) {
        this.endBooking = endBooking;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getStartBooking() {
        return startBooking;
    }

    public void setStartBooking(LocalDateTime startBooking) {
        this.startBooking = startBooking;
    }

    public UberDriver getDriver() {
        return driver;
    }

    public void setDriver(UberDriver driver) {
        this.driver = driver;
    }

    public UberUser getUser() {
        return user;
    }

    public void setUser(UberUser user) {
        this.user = user;
    }

}
