package com.example.demo.repository;

import com.example.demo.entity.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @EntityGraph(attributePaths = "writer", type= EntityGraph.EntityGraphType.LOAD)
    @Query("select n from Note n where n.id = :id")
    Optional<Note> getWithWriter(Long id);

    @EntityGraph(attributePaths = "writer", type= EntityGraph.EntityGraphType.LOAD)
    @Query("select n from Note n where n.writer.email = :email")
    Optional<Note> getList(String email);
}
