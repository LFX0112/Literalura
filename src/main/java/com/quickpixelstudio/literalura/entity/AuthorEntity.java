package com.quickpixelstudio.literalura.entity;

import com.quickpixelstudio.literalura.models.Author;
import com.quickpixelstudio.literalura.services.CharacterLength;
import jakarta.persistence.*;

@Entity
@Table(name = "Author")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer dateOfBirth;
    private Integer dateOfDeath;

    @OneToOne
    @JoinTable(name = "Book",
    joinColumns = @JoinColumn(name = "author_id"),
    inverseJoinColumns = @JoinColumn(name = "id"))
    private BookEntity books;

    public AuthorEntity(){

    }
    public AuthorEntity(Author author){
        this.name = CharacterLength.limitLength(author.name(), 200);
        if(author.birthYear() == null)
            this.dateOfBirth = 1980;
        else this.dateOfBirth = author.birthYear();
        if (author.deathYear() == null)
            this.dateOfDeath = 3022;
        else
            this.dateOfDeath = author.deathYear();
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(Integer dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    public Integer getDateOfDeath(){
        return dateOfDeath;
    }
    public void setDateOfDeath(Integer dateOfDeath){
        this.dateOfDeath = dateOfDeath;
    }
    @Override
    public String toString(){
        return "AuthorEntity [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", dateOfDeath=" + dateOfDeath + ", book=" + "]";
    }
    public BookEntity getBooks(){
        return books;
    }
    public void setBooks(BookEntity books){
        this.books = books;
    }
}