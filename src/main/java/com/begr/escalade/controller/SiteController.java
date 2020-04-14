package com.begr.escalade.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.begr.escalade.entity.Comment;
import com.begr.escalade.entity.User;
import com.begr.escalade.repository.CommentRepository;
import com.begr.escalade.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.begr.escalade.entity.Site;
import com.begr.escalade.repository.SiteRepository;
import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/site")
public class SiteController {
    private final Logger logger = (Logger) LoggerFactory.getLogger(SiteController.class);


    @Resource
    SiteRepository siteRepository;

    @Resource
    CommentRepository commentRepository;

    @Resource
    UserRepository userRepository;



    @GetMapping("/list")
    public ModelAndView getSiteList() {

        String viewName= "site/siteList";
        List<Site> sites = siteRepository.findAll();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("sites", sites);
        model.put("numberOfSites", sites.size());

        return new ModelAndView(viewName,model);

    }

    @GetMapping("/view")
    public ModelAndView viewOneSiteFiInformations(Integer id){
        String viewName = "site/siteView";
        Map<String,Object> model = new HashMap<String,Object>();
        Comment newComment = new Comment();
        Site existingSite = siteRepository.getOne(Long.valueOf(id));
        if (existingSite == null) {
            RedirectView redirect = new RedirectView();
            redirect.setUrl("/site/list");
            return new ModelAndView(redirect);
        }
        else {
            model.put("site", existingSite);
            model.put("newComment", newComment);
            return new ModelAndView(viewName, model);
        }
    }

    //affichage du formulaire
    @PreAuthorize("hasAuthority('USER')")
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
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView submitSiteItemForm(@Valid Site site) {
        if (site.getId() != null){
            Site existingItem = siteRepository.getOne(site.getId());
            if (existingItem == null) {
                siteRepository.save(site);
            }
            else {
                existingItem.setName(site.getName());
                existingItem.setDescription(site.getDescription());
                existingItem.setOfficiel(site.getOfficiel());
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
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView deleteSiteItem(Site site) {
        siteRepository.delete(site);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/");
        return new ModelAndView(redirect);
    }

    @GetMapping("/find")
    public ModelAndView findSite(@RequestParam(required = false, value = "searchQuery") String searchQuery){
        String viewName= "site/siteList";

        if(searchQuery.equals("")){
            RedirectView redirect = new RedirectView();
            redirect.setUrl("/site/list");
            return new ModelAndView(redirect);
        }
        Map<String,Object> model = new HashMap<String,Object>();
        List<Site> sites = siteRepository.findFulltext(searchQuery);
        if (sites.size() == 1){

            Site theSite = sites.get(0);
            RedirectView redirect = new RedirectView();
            redirect.setUrl("view");
            redirect.addStaticAttribute("id", theSite.getId());
            return new ModelAndView(redirect);
        }
        model.put("sites", sites);
        model.put("numberOfSites", sites.size());

        return new ModelAndView(viewName,model);

    }

    //Ajout d'un commentaire
    @PostMapping("/addComment")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView submitCommentItemForm(@Valid Comment theComment, Principal principal) {
        String username = principal.getName();
        User author = userRepository.findByUsername(username);
        theComment.setAuthor(author);
        System.out.println(theComment.toString());
        commentRepository.save(theComment);
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/site/view");
        redirect.addStaticAttribute("id", theComment.getSite().getId());
        return new ModelAndView(redirect);
    }

    @GetMapping("/editComment")
    @PreAuthorize("hasAuthority('MEMBER')")
    public RedirectView editComment(@RequestParam(required = true) Integer commentId, @RequestParam(required = true) String commentValue){
        Comment theComment = commentRepository.getOne(commentId.longValue());
        theComment.setValue(commentValue);
        commentRepository.save(theComment);
        Integer theSiteId = theComment.getSite().getId().intValue();

        RedirectView redirect = new RedirectView();
        redirect.addStaticAttribute("id", theSiteId);
        redirect.setUrl("view");
        return redirect;
    }

    @GetMapping("/commentDelete")
    @PreAuthorize("hasAuthority('MEMBER')")
    public RedirectView deleteCommentItem(@RequestParam(required = true) Integer id) {
        Comment theComment = commentRepository.getOne(id.longValue());
        Integer theSiteId = theComment.getSite().getId().intValue();
        commentRepository.delete(theComment);
        RedirectView redirect = new RedirectView();
        redirect.addStaticAttribute("id", theSiteId);
        redirect.setUrl("view");
        return redirect;
    }
}
