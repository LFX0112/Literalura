package com.quickpixelstudio.literalura.entity;

import com.quickpixelstudio.literalura.models.Author;
import com.quickpixelstudio.literalura.models.Book;
import com.quickpixelstudio.literalura.services.CharacterLength;
import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String language;
    private Integer downloads;
    @OneToOne(mappedBy = "books", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AuthorEntity author;

    public BookEntity(){

    }
    public BookEntity(Book book){
        this.title = CharacterLength.limitLength(book.title(), 200);
        this.downloads = book.download();
        if(!book.languages().isEmpty())
            this.language = book.languages().get(0);
        if(!book.authors().isEmpty()){
            for(Author author : book.authors()){
                this.author = new AuthorEntity(author);
                break;
            }
        }
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getLanguage(){
        return language;
    }
    public void setLanguage(String language){
        this.language = language;
    }
    public Integer getDownloads(){
        return downloads;
    }
    public void setDownloads(Integer downloads){
        this.downloads = downloads;
    }
    @Override
    public String toString(){
        return "BookEntity [id=" + id + ", title=" + title + ", language=" + language + ", downloads=" + downloads + ", authors=" + author + "]";
    }
    public AuthorEntity getAuthor() {
        return author;
    }
    public void setAuthor(AuthorEntity author){
        this.author = author;
    }
}