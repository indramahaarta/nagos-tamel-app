package com.code.nagostamelapp.transaction.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "direction")
    private String direction;

    @Column(name = "transaction_user")
    private String username;

    public Transaction(Date date, float amount, String description, String status, String direction) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                ", date=" + date +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
