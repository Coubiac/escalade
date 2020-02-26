package com.begr.escalade.repository;

import com.begr.escalade.entity.Reservation;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Reservation> findAllByStatus(String reservationStatus) {
        Query query = entityManager.createNativeQuery("SELECT * FROM reservations WHERE status = :status", Reservation.class);
        query.setParameter("status", reservationStatus);
        return query.getResultList();
    }
}
