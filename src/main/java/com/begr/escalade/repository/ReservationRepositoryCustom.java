package com.begr.escalade.repository;

import com.begr.escalade.entity.Reservation;

import java.util.List;

public interface ReservationRepositoryCustom {

    List<Reservation> findAllByStatus(String status);

}
