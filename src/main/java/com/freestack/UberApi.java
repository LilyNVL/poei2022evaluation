package com.freestack;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

public class UberApi {
    public static UberUser enrollUser(UberUser user) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public static UberDriver enrollDriver(UberDriver driver) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(driver);
        em.getTransaction().commit();
        em.close();
        return driver;
    }

    public static Booking bookOneDriver(UberUser user) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();

        UberDriver driver = em.createQuery("SELECT d FROM uberdriver d WHERE NOT EXISTS"
                        +" SELECT b FROM booking b WHERE b.uberdriver = d AND b.endbooking > :currentTimestamp"
                        , UberDriver.class)
                .getSingleResult();

        if (driver != null) {
            em.getTransaction().begin();
            em.persist(driver);

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setDriver(driver);
            booking.setStartBooking(LocalDateTime.now());
            em.persist(booking);

            em.getTransaction().commit();
            em.close();

            return booking;
        } else {
            em.close();
            return null;
        }
    }

    public static void finishBooking(Booking booking) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        booking.setEndBooking(LocalDateTime.now());
        em.merge(booking);
        em.getTransaction()
                .commit();
        em.close();
    }

    public static void evaluateDriver(Booking booking, int evaluationOfTheUser) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        booking.setScore(evaluationOfTheUser);
        em.merge(booking);
        em.getTransaction()
                .commit();
        em.close();
    }

    public static List<Booking> listDriverBookings(UberDriver driver) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();

        List<Booking> bookings = em.createQuery("SELECT b FROM booking b WHERE b.driver_id = :driver_id", Booking.class)
                .setParameter("driver_id", driver)
                .getResultList();

        em.close();
        return bookings;
    }

    public static float meanScore(UberDriver driver) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();

        List<Booking> bookings = em.createQuery("SELECT b FROM booking WHERE b.driver_id = :driver_id", Booking.class)
                .setParameter("driver_id", driver)
                .getResultList();

        em.close();

        float meanscore = 0;
        for (Booking booking : bookings) {
            meanscore += booking.getScore();
        }
        return meanscore / bookings.size();
    }

    public static List<Booking> listUnfinishedBookings() {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();

        List<Booking> listUnfinishedBookings = em.createQuery("SELECT b FROM booking where b.endbooking IS NULL", Booking.class)
                .getResultList();

        em.close();
        return listUnfinishedBookings;
    }

    public static List<?> listScoreAndUser() {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = emf.createEntityManager();

        List<Object[]> listScoreAndUser = em.createQuery("SELECT b.uberuser, SUM(b.evaluation) FROM booking b GROUP BY b.uberuser", Object[].class)
                .getResultList();

        for (Object[] obj : listScoreAndUser) {
            ///récupérer le user
            ///récupérer le totalscore
            ///if (totalscore == null) { sout(firstname+lastname+"n'a pas donné d'évaluation"); }
            ///else { sout(firstname+lastname+" a donné en moyenne une note de "+totalscore); }

        }
        return null;
    }
}
