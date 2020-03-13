package com.begr.escalade.controller;

import com.begr.escalade.entity.Reservation;
import com.begr.escalade.entity.User;
import com.begr.escalade.manager.ReservationManager;
import com.begr.escalade.manager.TopoManager;
import com.begr.escalade.repository.ReservationRepository;
import com.begr.escalade.repository.TopoRepository;
import com.begr.escalade.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Resource
    ReservationRepository reservationRepository;

    @Resource
    TopoRepository topoRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    TopoManager topoManager;

    @Resource
    ReservationManager reservationManager;

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

    //Validation du formulaire de demande de réservation
    @PostMapping("/SubmitReservationForm")
    public RedirectView submitLongueurForm(Reservation theReservation, Principal principal) {
        User theUser = userRepository.findByUsername(principal.getName());
        reservationManager.create(theReservation, theUser);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/");
        return redirect;
    }

}
