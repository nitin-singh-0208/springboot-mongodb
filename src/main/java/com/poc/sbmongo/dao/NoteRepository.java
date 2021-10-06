package com.poc.sbmongo.dao;

import com.poc.sbmongo.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    List<Note> findByTitleContainingIgnoreCase(String title);

    List<Note> findByDescriptionContainingIgnoreCase(String description);

    List<Note> findByCreatedDate(Date date);
}
