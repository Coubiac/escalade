package com.begr.escalade.repository;


import com.begr.escalade.entity.Topo;

import java.util.List;


public interface TopoRepositoryCustom {
    List<Topo> findAllBySiteId(Integer siteId);

    List<Topo> findAllDispo();

    List<Topo> findAllByOwnerId(Integer ownerId);
}
