package com.begr.escalade.controller;

import com.begr.escalade.entity.Reservation;
import com.begr.escalade.manager.TopoManager;
import com.begr.escalade.repository.ReservationRepository;
import com.begr.escalade.repository.TopoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Resource
    ReservationRepository reservationRepository;

    @Resource
    TopoRepository topoRepository;

    @Resource
    TopoManager topoManager;

    //affichage du formulaire
    @GetMapping("/reservationItemForm")
    public String showReservationItemForm(@RequestParam(required = false) Integer id, Model theModel) {
        String viewName= "reservation/reservationItemForm";

        if (id == null){
            theModel.addAttribute("reservation", new Reservation());
            theModel.addAttribute("topos",topoManager.getFreeTopos());
        }
        else {
            Reservation existingReservation = reservationRepository.getOne(Long.valueOf(id));
            theModel.addAttribute("reservation", existingReservation);
            theModel.addAttribute("topos",topoManager.getFreeTopos());
        }
        return viewName;
    }

    //Traitement du formulaire
    @PostMapping("/SubmitLongueurForm")
    public String submitLongueurForm() {

        return null;
    }

}
