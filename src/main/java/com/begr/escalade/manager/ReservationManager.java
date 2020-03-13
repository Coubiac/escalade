package com.begr.escalade.manager;

import com.begr.escalade.entity.Reservation;
import com.begr.escalade.entity.ReservationStatus;
import com.begr.escalade.entity.Topo;
import com.begr.escalade.entity.User;
import com.begr.escalade.repository.ReservationRepository;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ReservationManager {
    @Resource
    ReservationRepository reservationRepository;

    public void create (Reservation theReservation, User emprunteur) {
        theReservation.setDateDemande(new Date());
        theReservation.setStatus(ReservationStatus.EN_ATTENTE);
        theReservation.setEmprunteur(emprunteur);
        reservationRepository.save(theReservation);
    }

    public void validate (Reservation theReservation){
        theReservation.setDateEmprunt(new Date());
        theReservation.setStatus(ReservationStatus.EN_COURS);
        reservationRepository.save(theReservation);
    }

    public void terminate (Reservation theReservation){
        theReservation.setDateRetour(new Date());
        theReservation.setStatus(ReservationStatus.TERMINE);
        reservationRepository.save(theReservation);
    }

}
