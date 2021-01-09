package com.example.demo.service;

import com.example.demo.dto.NoteDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.Note;

import java.util.List;

public interface NoteService {
    Long register(NoteDto noteDto);
    NoteDto get(Long id);
    void modify(NoteDto noteDto);
    void remove(Long id);
    List<NoteDto> getAllWithWriter(String writerEmail);
    default Note mapDto2Entity(NoteDto noteDto){
        Note note = Note.builder()
                .id(noteDto.getId())
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .writer(Member.builder().email(noteDto.getWriterEmail()).build())
                .build();
        return note;
    }
    default NoteDto mapEntity2Dto(Note note){
        NoteDto noteDto = NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter().getEmail())
                .regDate(note.getRegDate())
                .modDate(note.getModDate())
                .build();
        return noteDto;
    }

}
