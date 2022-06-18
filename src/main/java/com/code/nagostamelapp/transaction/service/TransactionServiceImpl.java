package com.code.nagostamelapp.transaction.service;

import com.code.nagostamelapp.transaction.model.Transaction;
import com.code.nagostamelapp.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> sortByDate(String username) {
        List<Transaction> transactionsByDate = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        List<Transaction> transactionsByDateAndUsername = new ArrayList<>();
        for (Transaction transaction : transactionsByDate) {
            if (transaction.getUsername() == null) {
                continue;
            } else if (transaction.getUsername().equals(username)) {
                transactionsByDateAndUsername.add(transaction);
            }
        }
        return transactionsByDateAndUsername;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
