package com.begr.escalade.repository;

import com.begr.escalade.entity.Reservation;

import java.util.List;

public interface ReservationRepositoryCustom {

    List<Reservation> findAllByStatus(String status);

    List<Reservation> findAllByStatusNot(String status);

    List<Reservation> findAllByTopoOwnerUsername(String username);

    List<Reservation> findAllByTopoOwnerUsernameAndIsActive(String username);

    List<Reservation>findAllByEmprunteurUsername(String username);
}
