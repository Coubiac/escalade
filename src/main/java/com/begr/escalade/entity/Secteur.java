package com.begr.escalade.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

import java.util.List;

@Entity
@Table(name = "secteurs")
@Indexed
@Analyzer(impl = FrenchAnalyzer.class)
public class Secteur extends Auditable<String>{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50)
    @Field(index = Index.YES, analyze= Analyze.YES, store = Store.YES)
    private String name;

    @NotBlank
    @Size(min = 4, max = 250)
    @Field(index = Index.YES, analyze= Analyze.YES, store = Store.YES)
    private String description;

    private Boolean isEquipped;

    @ManyToOne
    @JoinColumn(name="site_id")
    @ContainedIn
    private Site site;

    @OneToMany(mappedBy = "secteur", orphanRemoval = true, cascade = CascadeType.PERSIST)
    @IndexedEmbedded
    private List<Voie> voies = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Boolean getEquipped() {
        return isEquipped;
    }

    public void setEquipped(Boolean equipped) {
        isEquipped = equipped;
    }

    public List<Voie> getVoies() {
        return voies;
    }

    public void setVoies(List<Voie> voies) {
        this.voies = voies;
    }

    @Override
    public String toString(){
        return "ID: " + getId() +
                "\nNAME: " + getName() +
                "\nDESCRIPTION: " + getDescription();
    }
}
