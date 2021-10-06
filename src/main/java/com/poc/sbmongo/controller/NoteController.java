package com.poc.sbmongo.controller;

import com.poc.sbmongo.dao.NoteRepository;
import com.poc.sbmongo.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NoteController {
    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/note")
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String description) {
        try {
            List<Note> notes = new ArrayList<Note>();
            if (title == null && description == null) {
                notes.addAll(noteRepository.findAll());
            } else if (title != null && description != null) {
                notes.addAll(noteRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(title, description));
            } else if (title == null) {
                notes.addAll(noteRepository.findByDescriptionContainingIgnoreCase(description));
            } else {
                notes.addAll(noteRepository.findByTitleContainingIgnoreCase(title));
            }

            if (notes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(notes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String id) {
        Optional<Note> noteData = noteRepository.findById(id);

        return noteData.map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/note")
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        try {
            Note _note = noteRepository.save(new Note(note.getTitle(), note.getPrimaryTopic(), note.getDescription(), note.getResources()));
            return new ResponseEntity<>(_note, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/note/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") String id, @RequestBody Note note) {
        Optional<Note> noteDate = noteRepository.findById(id);

        if (noteDate.isPresent()) {
            Note _note = noteDate.get();
            _note.setTitle(note.getTitle());
            _note.setTitle(note.getPrimaryTopic());
            _note.setDescription(note.getDescription());
            _note.setResources(note.getResources());
            return new ResponseEntity<>(noteRepository.save(_note), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") String id) {
        try {
            noteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/note")
    public ResponseEntity<HttpStatus> deleteAllNotes() {
        try {
            noteRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
