package com.begr.escalade.controller;


import com.begr.escalade.entity.Voie;
import com.begr.escalade.repository.CotationRepository;
import com.begr.escalade.repository.SecteurRepository;
import com.begr.escalade.repository.VoieRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/voie")
public class VoieController {

    @Resource
    VoieRepository voieRepository;

    @Resource
    SecteurRepository secteurRepository;

    @Resource
    CotationRepository cotationRepository;

    //affichage du formulaire
    @GetMapping("/voieItemForm")
    @PreAuthorize("hasAuthority('USER')")
    public String showVoieItemForm(@RequestParam(required = false) Integer id, Model theModel) {
        String viewName= "voie/voieItemForm";

        if (id == null){
            theModel.addAttribute("voie", new Voie());
            theModel.addAttribute("secteurs",secteurRepository.findAll());
            theModel.addAttribute("cotations", cotationRepository.findAll());
        }
        else {
            Voie existingVoie = voieRepository.getOne(Long.valueOf(id));
            theModel.addAttribute("voie", existingVoie);
            theModel.addAttribute("secteurs",secteurRepository.findAll());
            theModel.addAttribute("cotations", cotationRepository.findAll());

        }
        return viewName;
    }

    //Traitement du formulaire
    @PostMapping(value="/SubmitVoieForm")
    @PreAuthorize("hasAuthority('USER')")
    public String submitVoieForm(@Valid Voie theVoie, BindingResult theBindingResult, Model model) {
        System.out.println(theVoie.toString());
        if( theBindingResult.hasErrors()){
            model.addAttribute("errors", theBindingResult.getAllErrors());
            return "voie/voieItemForm";
        }
        else{
            voieRepository.save(theVoie);
            List<Voie> voies = voieRepository.findAll();
            model.addAttribute("voies", voies);
        }
        return "voie/voieList";
    }

    //Affichage de la liste des voies d'un site
    @GetMapping(value="/list")
    public String showVoiesOfOneSecteur(@RequestParam(required = false) Long secteurId, Model model){
        if (secteurId != null){
            List<Voie> voies = voieRepository.findAllBySecteurId(secteurId);
            model.addAttribute("voies", voies);
        }
        else{
            List<Voie> voies = voieRepository.findAll();
            model.addAttribute("voies", voies);
        }
        return "voie/voieList";
    }

    @GetMapping("/voieDelete")
    @PreAuthorize("hasAuthority('USER')")
    public RedirectView deleteSiteItem(Voie voie) {
        voieRepository.delete(voie);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/voie/list");
        return redirect;
    }
}
