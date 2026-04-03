package Fine;

import Loan.Loan;


import java.time.LocalDate;

public class Fine {
    private int id;
    private Loan loan;
    private int amount;
    private LocalDate issuedDate, paidDate;
    private String status;

    public Fine(Loan loan, int amount) {
        this.loan = loan;
        this.amount = amount;
        this.issuedDate = LocalDate.now();
        this.status = "pending";
    }

    public Fine(int id, Loan loan, int amount, LocalDate issuedDate, LocalDate paidDate, String status) {
        this.id = id;
        this.loan = loan;
        this.amount = amount;
        this.issuedDate = issuedDate;
        this.paidDate = paidDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaid() {
        status = "paid";
    }
}
