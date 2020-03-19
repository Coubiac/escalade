package com.begr.escalade.manager;

import com.begr.escalade.entity.Reservation;
import com.begr.escalade.entity.ReservationStatus;
import com.begr.escalade.entity.Topo;
import com.begr.escalade.entity.User;
import com.begr.escalade.repository.ReservationRepository;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ReservationManager {
    @Resource
    ReservationRepository reservationRepository;

    @Resource
    UserManager userManager;

    public void create (Reservation theReservation, User emprunteur) {
        theReservation.setDateDemande(new Date());
        theReservation.setStatus(ReservationStatus.EN_ATTENTE);
        theReservation.setEmprunteur(emprunteur);
        reservationRepository.save(theReservation);
    }

    public void validate (Integer theReservationId){
        Reservation theReservation = reservationRepository.getOne(theReservationId.longValue());
        theReservation.setDateEmprunt(new Date());
        theReservation.setStatus(ReservationStatus.EN_COURS);
        reservationRepository.save(theReservation);
    }

    public void terminate (Integer theReservationId){
        Reservation theReservation = reservationRepository.getOne(theReservationId.longValue());
        theReservation.setDateRetour(new Date());
        theReservation.setStatus(ReservationStatus.TERMINE);
        reservationRepository.save(theReservation);
    }


    public List<Reservation> getMyReservations(){
        User theConnectedUser = userManager.getTheConnectedUser();
        List<Reservation> myReservations;
        myReservations = reservationRepository.findAllByTopoOwnerUsername(theConnectedUser.getUsername());
        return myReservations;
    }

    public List<Reservation> getMyReservedTopos(){
        User theConnectedUser = userManager.getTheConnectedUser();
        List<Reservation> myReservations;
        myReservations = reservationRepository.findAllByEmprunteurUsername(theConnectedUser.getUsername());
        return myReservations;
    }

    public List<Reservation> getMyActiveReservations(){
        User theConnectedUser = userManager.getTheConnectedUser();
        List<Reservation> myActiveReservations;
        myActiveReservations = reservationRepository.findAllByTopoOwnerUsernameAndIsActive(theConnectedUser.getUsername());
        return myActiveReservations;
    }

}
