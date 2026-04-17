package member;

import java.util.ArrayList;
import java.util.Scanner;
import Main;
import IO;
import ANSI;
import exceptions.CantCreateMemberException;
import exceptions.MemberNotFoundException;
import fine.Fine;
import fine.FineService;
import loan.Loan;
import loan.LoanService;
import org.apache.commons.validator.routines.EmailValidator;


public class MemberController {
    MemberService memberService = new MemberService();
    Scanner scanner = new Scanner(System.in);

    public void showMenu(){
        boolean active = true;
        int choice;

        while (active) {
            System.out.println("""
                    What do you want to do?
                    1. View membership information.
                    2. Change personal information.
                    3. Change membership type.
                    0. Go back.""");
            choice=Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> showMemberInfo(Main.loggedInUser);
                case 2 -> editMemberInfo(Main.loggedInUser);
                case 3 -> changeMembershipType(Main.loggedInUser);
                case 0: active = false;
            }
        }
    }

    public void showLibrarianMenu(){
        boolean active = true;
        int choice;

        while (active) {
            System.out.println("""
                    What do you want to do?
                    1. Show all members.
                    2. Show all active members.
                    3. Show all suspended members.
                    4. Show all expired members.
                    5. View specific member.
                    6. Create new member account.
                    0. Go back.""");
            choice=IO.inputNumber();

            switch (choice) {
                case 1 -> showAllMembers();
                case 2 -> showAllMembersByStatus("active");
                case 3 -> showAllMembersByStatus("suspended");
                case 4 -> showAllMembersByStatus("expired");
                case 5->{
                    Member member = askForMember();
                    if (!(member==null)){
                        showLibrarianMemberMenu(member);
                    }
                }
                case 6 -> createMember();
                case 0 -> active = false;
            }
        }
    }

    public void showLibrarianMemberMenu(Member member){
        boolean active = true;
        while (active) {
            System.out.println("You are viewing the account of " + member.getFullName());
            System.out.println("""
                    What do you want to do?
                    1. View membership information.
                    2. Change personal information.
                    3. Change membership type.
                    4. Change membership status.
                    5. View member's active loans.
                    6. View all of member's loans.
                    7. View member's unpaid fines.
                    8. Pay/cancel member fine.
                    0. Go back.""");
            int choice = IO.inputNumber();
            switch (choice) {
                case 1 -> showMemberInfo(member);
                case 2 -> editMemberInfo(member);
                case 3 -> changeMembershipType(member);
                case 4 -> changeMembershipStatus(member);
                case 5 -> showActiveLoans(member);
                case 6 -> showAllLoans(member);
                case 7 -> showUnpaidFines(member);
                case 8 -> payFine(member);
                case 0 -> active = false;
                default -> System.out.println("Please enter a valid choice.");
            }
        }
    }

    public void showAllMembers(){
        ArrayList<Member> members = memberService.getAllMembers();
        if(members.isEmpty()) {
            System.out.println("There are no members.");
        } else {
            System.out.println("ID | Name | Email | Status");
            for (Member member : members) {
                System.out.println(member.getMemberId() + " | " + member.getFullName() + " | " + member.getEmail() + " | " + member.getStatus());
            }
        }
    }

    public void showAllMembersByStatus(String status){
        ArrayList<Member> members = memberService.getAllMembersByStatus(status);
        if(members.isEmpty()) {
            System.out.println("There are no "+status+" members.");
        } else {
            System.out.println("ID | Name | Email");
            for (Member member : members) {
                System.out.println(member.getMemberId() + " | " + member.getFullName() + " | " + member.getEmail());
            }
        }
    }

    public void showMemberInfo(Member member) {
        System.out.println("First name: " + member.getFirstName());
        System.out.println("Last name: " + member.getLastName());
        System.out.println("E-mail address: " + member.getEmail());
        System.out.println("Membership type: " + member.getMembershipType());
        System.out.println("Membership status: " + member.getStatus());
        System.out.println("Member since: " + member.getMembershipDate());

    }

    public void createMember(){
        String firstName = askForFirstName();
        String lastName = askForLastName();
        String email = askForEmail();
        String status = askForStatus();
        Member member = new Member(firstName, lastName, email, status);
        try {
            memberService.addMember(member);
            System.out.println("Member "+member.getFullName() + " created with ID "+member.getMemberId()+".");
        } catch (CantCreateMemberException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editMemberInfo(Member member){
        boolean active = true;
        int choice;

        while (active){
            System.out.println("Which information do you want to edit?");
            System.out.println("1. First name: " + member.getFirstName());
            System.out.println("2. Last name: " + member.getLastName());
            System.out.println("3. E-mail address: " + member.getEmail());
            System.out.println("9. Save and exit.");
            System.out.println("0. Exit without saving.");
            choice=Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    String newName = askForFirstName();
                    member.setFirstName(newName);
                }
                case 2 -> {
                    String newName = askForLastName();
                    member.setLastName(newName);
                }
                case 3-> {
                    String newMail = askForEmail();
                    member.setEmail(newMail);
                }
                case 9 ->{
                    active = false;
                    memberService.save(member);
                }
                case 0 -> active=false;
            }
        }
    }

    public void changeMembershipStatus(Member member){
        boolean active = true;
        while (active) {
            System.out.println("Current membership status is: " + member.getStatus());
            System.out.println("Do you wish to change it to:");
            if(!(member.getStatus()=="active")) {
                System.out.println("1. Active.");
            }
            if(!(member.getStatus()=="suspended")) {
                System.out.println("2. Suspended.");
            }
            if(!(member.getStatus()=="expired")) {
                System.out.println("3. Expired.");
            }
            System.out.println("0. Go back.");
            int choice = IO.inputNumber();
            switch (choice){
                case 1 -> {
                    member.setStatus("active");
                    memberService.save(member);
                    active = false;
                }
                case 2 -> {
                    member.setStatus("suspended");
                    memberService.save(member);
                    active = false;
                }
                case 3 -> {
                    member.setStatus("expired");
                    memberService.save(member);
                    active = false;
                }
                case 0 -> active = false;
                default -> System.out.println("Please enter a valid choice.");
            }
        }
    }

    public void changeMembershipType(Member member) {
        System.out.println("Current membership type is: " + member.getMembershipType());
        switch (member.getMembershipType()) {
            case "standard" -> {
                boolean active = true;
                while(active) {
                    System.out.println("Do you wish to change it to premium (Y/N)?");
                    String choice = scanner.nextLine().trim();
                    switch (choice.toLowerCase()) {
                        case "y", "yes" -> {
                            member.setMembershipType("premium");
                            memberService.save(member);
                            System.out.println("Membership type changed.");
                            active = false;
                        }
                        case "n", "no" -> {
                            System.out.println("No changes made.");
                            active = false;
                        }
                        default -> System.out.println("Not a valid choice.");
                    }
                }
            }
            case "premium" -> {
                boolean active = true;
                while(active) {
                    System.out.println("Do you wish to change it to standard (Y/N)?");
                    String choice = scanner.nextLine().trim();
                    switch (choice.toLowerCase()) {
                        case "y", "yes" -> {
                            member.setMembershipType("standard");
                            memberService.save(member);
                            System.out.println("Membership type changed.");
                            active = false;
                        }
                        case "n", "no" -> {
                            System.out.println("No changes made.");
                            active = false;
                        }
                        default -> System.out.println("Not a valid choice.");
                    }
                }
            }
        }
    }

    public void showActiveLoans(Member member){
        LoanService loanService = new LoanService();
        ArrayList<Loan> loans = loanService.getCurrentLoansByMember(member);
        if (loans.isEmpty()){
            System.out.println(member.getFullName() + " has no current loans.");
        } else {
            System.out.println("ID | Title | Due date");
            for(Loan loan : loans){
                if(loan.isOverdue()){
                    System.out.println(ANSI.color("bright_red") + loan.getId() + " | " + loan.getBook().getTitle() + " | " + loan.getDueDate() + ANSI.reset());
                } else {
                    System.out.println(loan.getId() + " | " + loan.getBook().getTitle() + " | " + loan.getDueDate());
                }
            }
        }
    }

    public void showAllLoans(Member member){
        LoanService loanService = new LoanService();
        ArrayList<Loan> loans = loanService.getAllLoansByMember(member);
        if (loans.isEmpty()){
            System.out.println(member.getFullName() + " has no loans.");
        } else {
            System.out.println("ID | Title | Due date | Return date");
            for(Loan loan : loans){
                if(loan.isOverdue()){
                    System.out.println(ANSI.color("bright_red") + loan.getId() + " | " + loan.getBook().getTitle() + " | " + loan.getDueDate() + (loan.getReturnDate()==null ? "" : loan.getReturnDate()) + ANSI.reset());
                } else {
                    System.out.println(loan.getId() + " | " + loan.getBook().getTitle() + " | " + loan.getDueDate() + (loan.getReturnDate()==null ? "" : loan.getReturnDate()));
                }
            }
        }
    }

    public void showUnpaidFines(Member member) {
        FineService fineService = new FineService();
        ArrayList<Fine> fines = fineService.getAllUnpaidFinesForMember(member);
        if(fines.isEmpty()){
            System.out.println(member.getFullName() + " has no unpaid fines.");
        } else {
            System.out.println("ID | Title | Amount | Issued");
            for(Fine fine : fines) {
                System.out.println(fine.getId() + " | " + fine.getLoan().getBook().getTitle() + " | " + fine.getAmount() + " kr | " + fine.getIssuedDate());
            }
        }
    }

    public void payFine(Member member) {
        FineService fineService = new FineService();
        boolean active = true;
        while (active) {
            System.out.println("Please enter fine ID (or 0 to go back):");
            int id = IO.inputNumber();
            if (id == 0){
                active = false;
            } else {
                if (fineService.exists(id)) {
                    Fine fine = fineService.getFineById(id);
                    if(fine.getLoan().getMember().getMemberId() == member.getMemberId()){
                        fineService.payFine(fine);
                        active = false;
                    } else {
                        System.out.println("Fine " + id + " does not belong to " + member.getFullName() +".");
                    }

                } else {
                    System.out.println("Could not find fine ID.");
                }
            }
        }
    }

    public String askForFirstName(){
        boolean active = true;
        String firstName = "";
        while (active) {
            System.out.println("Please enter the member's first name.");
            firstName = scanner.nextLine().trim();
            if(firstName.length()>0) {
                active=false;
            } else {
                System.out.println("Can't accept an empty name.");
            }
        }
        return firstName;
    }

    public String askForLastName(){
        boolean active = true;
        String lastName = "";
        while (active) {
            System.out.println("Please enter the member's last name.");
            lastName = scanner.nextLine().trim();
            if(lastName.length()>0) {
                active=false;
            } else {
                System.out.println("Can't accept an empty name.");
            }
        }
        return lastName;
    }

    public String askForEmail(){
        boolean active = true;
        String newMail ="";
        EmailValidator emailValidator = EmailValidator.getInstance();
        while(active) {
            System.out.println("Please enter new email address:");
            newMail = scanner.nextLine().trim();
            if (emailValidator.isValid(newMail)) {
                active = false;
            } else {
                System.out.println("The email address " +newMail+ " is not valid.");
            }
        }
        return newMail;
    }

    public String askForStatus(){
        boolean active = true;
        String status = "";
        while (active) {
            System.out.println("Please enter member status (standard or premium).");
            status = scanner.nextLine().trim().toLowerCase();
            if(!(status=="standard"||status=="premium")) {
                active=false;
            } else {
                System.out.println("Not a valid value.");
            }
        }
        return status;
    }

    public Member askForMember(){
        boolean active = true;
        Member user = null;
        MemberService memberService = new MemberService();
        EmailValidator emailValidator = EmailValidator.getInstance();
        while(active){
            System.out.println("Please enter member ID or e-mail address (or 0 to go back):");
            String input=scanner.nextLine().trim();

            if (IO.isNumeric (input)){
                int id = Integer.parseInt(input);
                if (memberService.exists(id)) {
                    user = memberService.getById(Integer.parseInt(input));
                    active = false;
                } else {
                    System.out.println("Member " + input + " could not be found.");
                }
            } else if (emailValidator.isValid(input)) {
                try {
                    user = memberService.getByEmail(input);
                    active=false;
                } catch (MemberNotFoundException e) {
                    System.out.println("Could not find any member with the email "+input + ".");
                }

            } else {
                System.out.println("Invalid ID or e-mail address.");
            }
        }
        return user;
    }

}
