package com.quickpixelstudio.literalura.client;

import com.quickpixelstudio.literalura.entity.AuthorEntity;
import com.quickpixelstudio.literalura.entity.BookEntity;
import com.quickpixelstudio.literalura.models.DataConversor;
import com.quickpixelstudio.literalura.models.Response;
import com.quickpixelstudio.literalura.repository.AuthorRepository;
import com.quickpixelstudio.literalura.repository.BookRepository;
import com.quickpixelstudio.literalura.services.ConsumptAPI;

import java.util.List;
import java.util.Scanner;

public class LiteraluraClient {
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner keyboard = new Scanner(System.in);
    private ConsumptAPI consumptApi = new ConsumptAPI();
    private DataConversor conversor = new DataConversor();

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public LiteraluraClient(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }
    public void showMenu(){
        var option = -1;
        while (option != 0) {
            var menu = """
                    
                    Choose an option via number:
                    
                    1 - Search books by Title
                    2 - Registered books List
                    3 - List of registered Authors
                    4 - List of living authors by Year
                    5 - List books by Language
                    
                    0 - Exit
                    """;
            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option){
                case 1:
                    searchWebBook();
                    break;
                case 2:
                    searchBooks();
                    break;
                case 3:
                    searchAuthors();
                    break;
                case 4:
                    searchLivingAuthors();
                    break;
                case 5:
                    searchByLanguages();
                    break;
                case 0:
                    System.out.println("Exit application...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    private void searchBooks(){
        List<BookEntity> books = bookRepository.findAll();
        if(!books.isEmpty()){
            for(BookEntity book : books) {
                System.out.println("\n\n******** BOOK ********\n");
                System.out.println(" Title: " + book.getTitle());
                System.out.println(" Author: " + book.getAuthor().getName());
                System.out.println(" Language: " + book.getLanguage());
                System.out.println(" Downloads: " + book.getDownloads());
                System.out.println("\n***************************\n\n");
            }
        } else {
            System.out.println("\n\n ******** NO RESULTS FOUND! ******** \n\n");
        }
    }
    private void searchAuthors(){
        List<AuthorEntity> authors = authorRepository.findAll();
        if(!authors.isEmpty()){
            for (AuthorEntity author : authors){
                System.out.println("\n\n******** AUTHOR ********\n");
                System.out.println(" Name: " + author.getName());
                System.out.println(" Date of Birth: " + author.getDateOfBirth());
                System.out.println(" Date of Death: " + author.getDateOfDeath());
                System.out.println(" Books: " + author.getBooks().getTitle());
                System.out.println("\n***************************\n\n");
            }
        } else {
            System.out.println("\n\n ******** NO RESULTS FOUND! ******** \n\n");
        }
    }
    private void searchLivingAuthors(){
        System.out.println("Write the year author lived");
        var year = keyboard.nextInt();
        keyboard.nextLine();

        List<AuthorEntity> authors = authorRepository.findForYear(year);

        if(!authors.isEmpty()){
            for(AuthorEntity author : authors){
                System.out.println("\n\n******** LIVING AUTHORS ********\n");
                System.out.println(" Name: " + author.getName());
                System.out.println(" Date of Birth: " + author.getDateOfBirth());
                System.out.println(" Date of Death: " + author.getDateOfDeath());
                System.out.println(" Books: " + author.getBooks().getTitle());
                System.out.println("\n***************************\n\n");
            }
        } else {
            System.out.println("\n\n ******** NO RESULTS FOUND! ******** \n\n");
        }
    }
    private void searchByLanguages(){
        var menu = """
                Select a Language:
                1 - Spanish
                2 - English
                """;
        System.out.println(menu);
        var language = keyboard.nextInt();
        keyboard.nextLine();

        String selection = "";

        if(language == 1) {
            selection = "es";
        } else if(language == 2){
            selection = "en";
        }

        List<BookEntity> books = bookRepository.findForLanguage(selection);

        if(!books.isEmpty()){
            for(BookEntity book : books){
                System.out.println("\n\n******** BOOKS BY LANGUAGE ********\n");
                System.out.println(" Title: " + book.getTitle());
                System.out.println(" Author: " + book.getAuthor().getName());
                System.out.println(" Language: " + book.getLanguage());
                System.out.println(" Downloads: " + book.getDownloads());
                System.out.println("\n***************************\n\n");
            }
        } else {
            System.out.println("\n\n ******** NO RESULTS FOUND! ******** \n\n");
        }
    }
    private void searchWebBook(){
        Response data = getDataSeries();

        if(!data.results().isEmpty()) {
            BookEntity book = new BookEntity(data.results().get(0));
            book = bookRepository.save(book);
        }
        System.out.println("Data: ");
        System.out.println(data);
    }
    private Response getDataSeries(){
        System.out.println("Type the name of the book you want to search for: ");
        var title = keyboard.nextLine();
        title = title.replace(" ", "%20");
        System.out.println("Title : " + title);
        System.out.println(URL_BASE + title);
        var json = consumptApi.getData(URL_BASE + title);
        System.out.println(json);
        Response data = conversor.getData(json, Response.class);
        return data;
    }
}