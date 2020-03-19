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

    public List<Reservation> findAllByStatusNot(String reservationStatus) {
        Query query = entityManager.createNativeQuery("SELECT * FROM reservations WHERE status <> :status", Reservation.class);
        query.setParameter("status", reservationStatus);
        return query.getResultList();
    }

    public List<Reservation> findAllByTopoOwnerUsername(String username) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM reservations\n" +
                        "    INNER JOIN topos ON reservations.topo_id = topos.id\n" +
                        "        INNER JOIN user ON user.id = topos.owner_id\n" +
                        "WHERE username = :username ORDER BY status ASC, date_emprunt DESC", Reservation.class
        );
        query.setParameter("username", username);
        return query.getResultList();
    }

    public List<Reservation>findAllByEmprunteurUsername(String username){
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM reservations " +
                        "INNER JOIN user ON user.id = reservations.emprunteur_id " +
                        "WHERE username = :username ORDER BY status ASC, date_emprunt DESC", Reservation.class);
        query.setParameter("username", username);
        return query.getResultList();
    };

    public List<Reservation> findAllByTopoOwnerUsernameAndIsActive(String username) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM reservations\n" +
                        "    INNER JOIN topos ON reservations.topo_id = topos.id\n" +
                        "        INNER JOIN user ON user.id = topos.owner_id\n" +
                        "WHERE username = :username AND reservations.status <> 'TERMINE'", Reservation.class

        );
        query.setParameter("username", username);
        return query.getResultList();
    }
}
