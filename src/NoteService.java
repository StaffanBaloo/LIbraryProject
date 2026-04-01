import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;

public class NoteService {
    NoteRepository noteRepository = new NoteRepository()

    public Note getById(int noteId){
        return noteRepository.getById(noteId);
    }

    public int getNumberUnreadNotesByUser(int userId){
        return noteRepository.getNumberUnreadNotesByUser(userId);

    }

    public int getNumberNotesByUser(int userId){
        return noteRepository.getNumberNotesByUser(userId);
    }

    public void markRead(int noteId){
        noteRepository.markRead(noteId);
    }

    public void markUnread(int noteId){
        noteRepository.markUnread(noteId);
    }

    public Note getNote(int noteId){
        return noteRepository.getNote(noteId);
    }

    public Note getOldestUnreadByUser(int userId){
        return noteRepository.getOldestUnreadByUser(userId);
    }

    public ArrayList<Note> getNotesByUser(int userId) {
        return noteRepository.getNotesByUser(userId);
    }

    public ArrayList<Note> getUnreadNotesByUser(int userId) {
        ArrayList<Note> notes = noteRepository.getNotesByUser(userId)
                .stream()
                .filter(!(Note::isRead()))
                .collect(Collectors.toList());

    }

}
