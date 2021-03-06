package com.begr.escalade.controller;


import com.begr.escalade.entity.Topo;
import com.begr.escalade.entity.User;
import com.begr.escalade.repository.SiteRepository;
import com.begr.escalade.repository.TopoRepository;
import com.begr.escalade.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;


@RequestMapping("/topo")
@Controller
public class TopoController {
    private final Logger logger = (Logger) LoggerFactory.getLogger(SiteController.class);

    @Resource
    TopoRepository topoRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    SiteRepository siteRepository;


    @GetMapping("/myTopos")
    public String getMyTopos (Model model, Principal principal) {
        String username = principal.getName();
        User owner = userRepository.findByUsername(username);
        String viewName= "topo/topoList";
        List<Topo> topos = topoRepository.findAllByOwnerId(owner.getId().intValue());
        model.addAttribute("topos", topos);
        return viewName;
    }

    @GetMapping("/topoDelete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView deleteTopoItem(Topo topo , Principal principal) {

        topoRepository.delete(topo);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/topo/myTopos");
        return new ModelAndView(redirect);
    }

    //affichage du formulaire
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/topoItemForm")
    public String showTopoItemForm(@RequestParam(required = false) Integer id, Model theModel) {
        String viewName= "topo/topoItemForm";
        theModel.addAttribute("sites", siteRepository.findAll());
        if (id != null){
            Topo existingTopo = topoRepository.getOne(Long.valueOf(id));
            if (existingTopo == null) {
                theModel.addAttribute("topo", new Topo());
            }
            else {
                theModel.addAttribute("topo", existingTopo);
            }
        }
        else {
            theModel.addAttribute("topo", new Topo());
        }
        return viewName;
    }

    //Traitement du formulaire
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value="/submitNewTopoForm")
    public RedirectView submitTopoForm(Topo theTopo, Principal principal) {
        String username = principal.getName();
        User owner = userRepository.findByUsername(username);
        theTopo.setOwner(owner);
        topoRepository.save(theTopo);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("myTopos");
        return redirect;
    }


}
