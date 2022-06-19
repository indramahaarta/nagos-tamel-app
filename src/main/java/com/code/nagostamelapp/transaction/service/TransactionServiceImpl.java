package com.code.nagostamelapp.transaction.service;

import com.code.nagostamelapp.transaction.model.Transaction;
import com.code.nagostamelapp.transaction.repository.TransactionRepository;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

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
    public List<Transaction> findByUsername(String username) {
        return transactionRepository.findAllByUsername(username);
    }

    @Override
    public void saveTransaction(Transaction transaction, HttpSession httpSession) {
        String username = userService.getUsernameFromSession(httpSession);
        transaction.setUsername(username);
        UserModel user = userService.getUserByUsername(username);
        if (transaction.getDirection().equals("in")) {
            user.setCashBalance(user.getCashBalance() + transaction.getAmount());
        } else {
            user.setCashBalance(user.getCashBalance() - transaction.getAmount());
        }

        transactionRepository.save(transaction);
    }
}
