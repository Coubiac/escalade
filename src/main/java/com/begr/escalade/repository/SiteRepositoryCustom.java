package com.begr.escalade.repository;

import com.begr.escalade.entity.Site;

import java.util.List;

public interface SiteRepositoryCustom {
    List<Site> findFulltext(String searchQuery);
}
