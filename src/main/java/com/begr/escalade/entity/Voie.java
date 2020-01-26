package com.begr.escalade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "voies")
@Indexed
@Analyzer(impl = FrenchAnalyzer.class)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Voie {

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


    @ManyToOne
    @JoinColumn(name="cotation_id")
    @ContainedIn
    @IndexedEmbedded
    private Cotation cotation;

    @ManyToOne
    @JoinColumn(name="secteur_id")
    @ContainedIn
    private Secteur secteur;

    @OneToMany(mappedBy = "voie")
    @IndexedEmbedded
    private List<Longueur> longueurs = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Voie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cotation=" + cotation +
                ", secteur=" + secteur +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
