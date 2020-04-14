package com.begr.escalade.entity;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Parameter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;


@Entity
@Indexed
@AnalyzerDef(name = "customanalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),

        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "French")
                })
        })
@Table(name = "sites")
public class Site extends Auditable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50)
    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    @Analyzer(definition = "customanalyzer")
    private String name;

    private String slug;

    @NotBlank
    @Size(min = 4, max = 5000)
    @Field(index=Index.YES, analyze= Analyze.YES, store= Store.NO)
    @Analyzer(definition = "customanalyzer")
    private String description;

    private Boolean officiel = false;

    private String access;

    @OneToMany(mappedBy = "site", orphanRemoval = true, cascade = CascadeType.PERSIST)
    @IndexedEmbedded
    private List<Secteur> secteurs = new ArrayList<>();

    @OneToMany(mappedBy = "site")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "sites")
    private List<Topo> topos = new ArrayList<>();

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

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }
    public void addSecteur(Secteur secteur){
        this.secteurs.add(secteur);
    }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Boolean getOfficiel() {
        return officiel;
    }


    public void setOfficiel(Boolean officiel) {
        this.officiel = officiel;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public void addComment(Comment theComment) {
        this.comments.add(theComment);
    }

    public void removeComment(Comment theComment) {
        this.comments.remove(theComment);
    }

    public List<Topo> getTopos() {
        return topos;
    }

    public void setTopos(List<Topo> topos) {
        this.topos = topos;
    }



    @PrePersist
    @PreUpdate
    public void sluggify() {
        Pattern NONLATIN = Pattern.compile("[^\\w_-]");
        Pattern SEPARATORS = Pattern.compile("[\\s\\p{Punct}&&[^-]]");
        String noseparators = SEPARATORS.matcher(this.name).replaceAll("-");
        String normalized = java.text.Normalizer.normalize(noseparators, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        this.slug = slug.toLowerCase(Locale.FRENCH)
                .replaceAll("-{2,}","-")
                .replaceAll("-de-","-")
                .replaceAll("^-|-$","");
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
