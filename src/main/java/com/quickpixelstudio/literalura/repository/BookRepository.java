package com.quickpixelstudio.literalura.repository;

import com.quickpixelstudio.literalura.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT l FROM BookEntity l WHERE l.language = :language")
    List<BookEntity> findForLanguage(@Param("language") String language);
}