package com.begr.escalade.controller;


import com.begr.escalade.entity.Site;
import com.begr.escalade.repository.SiteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/arborescence")
public class ArborescenceController {
    @Resource
    SiteRepository siteRepository;


    @GetMapping("/list")
    public ModelAndView getSiteList() {

        String viewName= "arborescence/list";
        List<Site> sites = siteRepository.findAll();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("sites", sites);
        model.put("numberOfSites", sites.size());

        return new ModelAndView(viewName,model);

    }

    @GetMapping("/find")
    public ModelAndView findSite(@RequestParam(required = false, value = "searchQuery") String searchQuery){
        String viewName= "arborescence/list";

        if(searchQuery == null){
            RedirectView redirect = new RedirectView();
            redirect.setUrl("/site/list");
            return new ModelAndView(redirect);
        }
        List<Site> sites = siteRepository.findFulltext(searchQuery);
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("sites", sites);
        return new ModelAndView(viewName,model);

    }

}
