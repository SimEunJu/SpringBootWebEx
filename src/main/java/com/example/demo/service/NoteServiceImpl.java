package com.example.demo.service;

import com.example.demo.dto.NoteDto;
import com.example.demo.entity.Note;
import com.example.demo.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDto noteDto) {
        Note note = mapDto2Entity(noteDto);
        noteRepository.save(note);
        return note.getId();
    }

    @Override
    public NoteDto get(Long id) {
        Optional<Note> note = noteRepository.getWithWriter(id);
        if(note.isPresent()) return mapEntity2Dto(note.get());
        return null;
    }

    @Override
    public void modify(NoteDto noteDto) {
        Optional<Note> result = noteRepository.findById(noteDto.getId());
        if(result.isPresent()){
            Note note = result.get();
            note.changeContent(noteDto.getContent());
            note.changeTitle(noteDto.getTitle());
            noteRepository.save(note);
        }
    }

    @Override
    public void remove(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<NoteDto> getAllWithWriter(String writerEmail) {
        Optional<Note> notes = noteRepository.getList(writerEmail);
        return notes.stream().map(this::mapEntity2Dto).collect(Collectors.toList());
    }
}
