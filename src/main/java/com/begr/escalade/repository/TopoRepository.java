package com.begr.escalade.repository;

import com.begr.escalade.entity.Site;
import com.begr.escalade.entity.Topo;
import com.begr.escalade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopoRepository extends JpaRepository<Topo, Long>, TopoRepositoryCustom {

}
