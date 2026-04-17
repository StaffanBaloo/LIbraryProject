package note;

import loan.LoanService;
import Main;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class NoteController {
    NoteService noteService = new NoteService();
    Scanner scanner = new Scanner(System.in);

    public NoteController() {

    }

    public void showMenu() {
        boolean active = true;
        int unreadNotes;
        while (active){
            System.out.println("Notifications menu");
            unreadNotes = noteService.getNumberUnreadNotesByMember(Main.loggedInUser);
            if(unreadNotes>0){
                System.out.println("You have "+unreadNotes+ " unread notifications.");
            }
            System.out.println("""
                    1. Read oldest unread notification.
                    2. Read specific notification.
                    3. List unread notifications.
                    4. List all notifications.
                    0. Back.""");
            int choice=Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: {
                    readOldestUnread();
                    break;
                }
                case 2: {
                    read();
                    break;
                }
                case 3: {
                    listUnread();
                    break;
                }
                case 4: {
                    listAll();
                    break;
                }
                case 0: {
                    active=false;
                    break;
                }
            }
        }
    }

    void readOldestUnread(){
        Note note = noteService.getOldestUnreadByMember(Main.loggedInUser);
        System.out.println("Notification type: " + note.getType() +".");
        System.out.println("Sent on: " + note.getSentDate() +".");
        if(!Objects.isNull(note.getLoan())) {
            System.out.println("Applies to:");
            System.out.println(note.getLoan().toString());
        }
        System.out.println("Message:");
        System.out.println(note.getMessage());
        noteService.markRead(note);
    }

    void read(){
        boolean active = true;
        while (active) {
            System.out.println("Please enter notification ID:");
            int noteId=Integer.parseInt(scanner.nextLine().trim());
            Note note = noteService.getNote(noteId);
            if (note.getMember().getMemberId() == Main.loggedInUser.getMemberId()) {
                System.out.println("Notification type: " + note.getType() +".");
                System.out.println("Sent on: " + note.getSentDate() +".");
                if(!Objects.isNull(note.getLoan())) {
                    System.out.println("Applies to:");
                    System.out.println(note.getLoan().toString());
                }
                System.out.println("Message:");
                System.out.println(note.getMessage());
                noteService.markRead(note);
                active=false;
            } else {
                System.out.println("That notification is not yours!");
            }
        }
    }

    void listUnread(){
        if(noteService.getNumberUnreadNotesByMember(Main.loggedInUser)>0){
            System.out.println("You have the following unread notifications:");
            ArrayList<Note> notes = noteService.getUnreadNotesByMember(Main.loggedInUser);
            for (Note note: notes) {
                System.out.println(note.toString());
            }
        }else {
            System.out.println("You do not have any unread notifications.");
        }
    }

    void listAll(){
        if(noteService.getNumberNotesByMember(Main.loggedInUser)>0){
            System.out.println("You have the following  notifications:");
            ArrayList<Note> notes = noteService.getNotesByMember(Main.loggedInUser);
            for (Note note: notes) {
                System.out.println(note.toString());
            }
        }else {
            System.out.println("You do not have any notifications.");
        }
    }
}
