package com.begr.escalade.controller;

import com.begr.escalade.entity.Longueur;
import com.begr.escalade.repository.CotationRepository;
import com.begr.escalade.repository.VoieRepository;
import com.begr.escalade.repository.LongueurRepository;
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
@RequestMapping("/longueur")
public class LongueurController {

    @Resource
    LongueurRepository longueurRepository;

    @Resource
    VoieRepository voieRepository;

    @Resource
    CotationRepository cotationRepository;

    //affichage du formulaire
    @GetMapping("/longueurItemForm")
    @PreAuthorize("hasAuthority('USER')")
    public String showLongueurItemForm(@RequestParam(required = false) Integer id, Model theModel) {
        String viewName= "longueur/longueurItemForm";

        if (id == null){
            theModel.addAttribute("longueur", new Longueur());
            theModel.addAttribute("voies",voieRepository.findAll());
            theModel.addAttribute("cotations", cotationRepository.findAll());
        }
        else {
            Longueur existingLongueur = longueurRepository.getOne(Long.valueOf(id));
            theModel.addAttribute("longueur", existingLongueur);
            theModel.addAttribute("voies",voieRepository.findAll());
            theModel.addAttribute("cotations", cotationRepository.findAll());
        }
        return viewName;
    }

    //Traitement du formulaire
    @PostMapping(value="/SubmitLongueurForm")
    @PreAuthorize("hasAuthority('USER')")
    public String submitLongueurForm(@Valid Longueur theLongueur, BindingResult theBindingResult, Model model) {
        if( theBindingResult.hasErrors()){
            model.addAttribute("errors", theBindingResult.getAllErrors());
            return "longueur/longueurItemForm";
        }
        else{
            longueurRepository.save(theLongueur);
            List<Longueur> longueurs = longueurRepository.findAll();
            model.addAttribute("longueurs", longueurs);
        }
        return "longueur/longueurList";
    }

    //Affichage de la liste des longueurs d'un site
    @GetMapping(value="/list")
    public String showLongueursOfOneVoie(@RequestParam(required = false) Long voieId, Model model){
        if (voieId != null){
            List<Longueur> longueurs = longueurRepository.findAllByVoieId(voieId);
            model.addAttribute("longueurs", longueurs);
        }
        else{
            List<Longueur> longueurs = longueurRepository.findAll();
            model.addAttribute("longueurs", longueurs);
        }
        return "longueur/longueurList";
    }

    @GetMapping("/longueurDelete")
    @PreAuthorize("hasAuthority('MEMBER')")
    public RedirectView deleteLongueurItem(Longueur longueur) {
        longueurRepository.delete(longueur);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/longueur/list");
        return redirect;
    }
}
