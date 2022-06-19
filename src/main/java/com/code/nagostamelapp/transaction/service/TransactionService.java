package com.code.nagostamelapp.transaction.service;

import com.code.nagostamelapp.transaction.model.Transaction;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TransactionService {
    List<Transaction> sortByDate(String username);
    List<Transaction> findByUsername(String username);
    void saveTransaction(Transaction transaction, HttpSession httpSession);
}
