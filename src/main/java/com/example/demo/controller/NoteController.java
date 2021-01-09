package com.example.demo.controller;

import com.example.demo.dto.NoteDto;
import com.example.demo.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody NoteDto noteDto){
        Long id = noteService.register(noteDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDto> read(@PathVariable Long noteId){
        return new ResponseEntity<>(noteService.get(noteId), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDto>> getList(String email){
        return new ResponseEntity<>(noteService.getAllWithWriter(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{noteId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable Long noteId){
        noteService.remove(noteId);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

    @PutMapping(value = "/{noteId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(NoteDto noteDto){
        noteService.modify(noteDto);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }
}
