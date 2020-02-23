package com.begr.escalade.entity;

import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "voies")
@Indexed
@Analyzer(impl = FrenchAnalyzer.class)
public class Voie extends Auditable<String>{

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

    private boolean equiped;

    @ManyToOne
    @JoinColumn(name="cotation_id")
    @ContainedIn
    @IndexedEmbedded
    private Cotation cotation;

    @ManyToOne
    @JoinColumn(name="secteur_id")
    @ContainedIn
    private Secteur secteur;

    @OneToMany(mappedBy = "voie", orphanRemoval = true, cascade = CascadeType.PERSIST)
    @IndexedEmbedded
    private List<Longueur> longueurs = new ArrayList<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public boolean isEquiped() {
        return equiped;
    }

    public void setEquiped(boolean equiped) {
        this.equiped = equiped;
    }

    public Cotation getCotation() {
        return cotation;
    }

    public void setCotation(Cotation cotation) {
        this.cotation = cotation;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public List<Longueur> getLongueurs() { return longueurs; }

    public void setLongueurs(List<Longueur> longueurs) { this.longueurs = longueurs; }


    @Override
    public String toString() {
        return "Voie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cotation=" + cotation +
                ", secteur=" + secteur +
                '}';
    }
}
