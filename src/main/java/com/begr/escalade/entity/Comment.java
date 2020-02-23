package com.begr.escalade.entity;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;



@Entity
public class Comment {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String value;


    @ManyToOne(targetEntity = User.class)
    private User author;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;


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

    @ManyToOne
    @JoinColumn(name="author_id")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", author=" + author +
                ", site=" + site +
                '}';
    }
}
