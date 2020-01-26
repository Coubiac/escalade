package com.begr.escalade.repository;


import com.begr.escalade.entity.Voie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoieRepository extends JpaRepository<Voie, Long>, VoieRepositoryCustom {

}
