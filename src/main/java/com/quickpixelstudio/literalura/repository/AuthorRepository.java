package com.quickpixelstudio.literalura.repository;

import com.quickpixelstudio.literalura.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    @Query("SELECT a FROM AuthorEntity a WHERE :year between a.dateOfBirth AND a.dateOfDeath")
    List<AuthorEntity> findForYear(int year);
}