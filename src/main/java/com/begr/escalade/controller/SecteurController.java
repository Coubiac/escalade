package com.begr.escalade.controller;

import com.begr.escalade.entity.Secteur;
import com.begr.escalade.entity.Site;
import com.begr.escalade.repository.SecteurRepository;
import com.begr.escalade.repository.SiteRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/secteur")
public class SecteurController {

    @Resource
    SecteurRepository secteurRepository;

    @Resource
    SiteRepository siteRepository;

    //affichage du formulaire
    @Secured("ROLE_UTILISATEUR")
    @GetMapping("/secteurItemForm")
    public String showSecteurItemForm(@RequestParam(required = false) Integer id, Model theModel) {
        String viewName= "secteur/secteurItemForm";

        if (id == null){
            theModel.addAttribute("secteur", new Secteur());
            theModel.addAttribute("sites",siteRepository.findAll());
        }
        else {
            Secteur existingSecteur = secteurRepository.getOne(Long.valueOf(id));
            theModel.addAttribute("secteur", existingSecteur);
            theModel.addAttribute("sites",siteRepository.findAll());
        }
        return viewName;
    }

    //Traitement du formulaire
    @RequestMapping(value="/SubmitSecteurForm", method = RequestMethod.POST)
    public String SubmitSecteurForm(@Valid Secteur theSecteur, BindingResult theBindingResult, Model model) {
        if( theBindingResult.hasErrors()){
            model.addAttribute("errors", theBindingResult.getAllErrors());
            return "secteur/secteurItemForm";
        }
        else{
            secteurRepository.save(theSecteur);
            List<Secteur> secteurs = secteurRepository.findAll();
            model.addAttribute("secteurs", secteurs);
        }
        return "secteur/secteurList";
    }

    //Affichage de la liste des secteurs d'un site
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String ShowSecteursOfOneSite(@RequestParam(required = false) Long siteId, Model model){
        if (siteId != null){
            List<Secteur> secteurs = secteurRepository.findAllBySiteId(siteId);
            model.addAttribute("secteurs", secteurs);
        }
        else{
            List<Secteur> secteurs = secteurRepository.findAll();
            model.addAttribute("secteurs", secteurs);
        }
        return "secteur/secteurList";
    }

    @GetMapping("/secteurDelete")
    public RedirectView deleteSiteItem(Secteur secteur) {
        secteurRepository.delete(secteur);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/secteur/list");
        return redirect;
    }
}
