package com.begr.escalade.entity;


import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cotations")
@Indexed
@Analyzer(impl = FrenchAnalyzer.class)
public class Cotation {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Field(index = Index.YES, analyze= Analyze.YES, store = Store.YES)
    private String value;

    @OneToMany(mappedBy = "cotation")
    private List<Voie> voies = new ArrayList<>();

    @OneToMany(mappedBy = "cotation")
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Voie> getVoies() {
        return voies;
    }

    public void setVoies(List<Voie> voies) {
        this.voies = voies;
    }

    public List<Longueur> getLongueurs() {
        return longueurs;
    }

    public void setLongueurs(List<Longueur> longueurs) {
        this.longueurs = longueurs;
    }

    @Override
    public String toString() {
        return "Cotation{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
