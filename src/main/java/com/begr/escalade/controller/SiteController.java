package com.begr.escalade.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.begr.escalade.entity.Site;
import com.begr.escalade.repository.SiteRepository;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/site")
public class SiteController {

    @Resource
    SiteRepository siteRepository;

    @GetMapping("/list")
    public ModelAndView getSiteList() {

        String viewName= "site/siteList";
        List<Site> sites = siteRepository.findAll();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("sites", sites);
        model.put("numberOfSites", sites.size());

        return new ModelAndView(viewName,model);

    }

    //affichage du formulaire
    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping("/siteItemForm")
    public ModelAndView showSiteItemForm(@RequestParam(required = false) Integer id) {
        String viewName= "site/siteItemForm";
        Map<String,Object> model = new HashMap<String,Object>();

        if (id != null){
            Site existingSite = siteRepository.getOne(Long.valueOf(id));
            if (existingSite == null) {
                model.put("site", new Site());
            }
            else {
                model.put("site", existingSite);
            }
        }
        else {
            model.put("site", new Site());
        }
        return new ModelAndView(viewName,model);
    }

    //Traitement du formulaire
    @PostMapping("/siteItemForm")
    public ModelAndView submitSiteItemForm(@Valid Site site) {
        if (site.getId() != null){
            Site existingItem = siteRepository.getOne(site.getId());
            if (existingItem == null) {
                siteRepository.save(site);
            }
            else {
                existingItem.setName(site.getName());
                existingItem.setDescription(site.getDescription());
                siteRepository.save(existingItem);
            }
        }
        else {
            siteRepository.save(site);
        }
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/site/list");
        return new ModelAndView(redirect);
    }

    @GetMapping("/siteDelete")
    public ModelAndView deleteSiteItem(Site site) {
        siteRepository.delete(site);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/");
        return new ModelAndView(redirect);
    }

}
