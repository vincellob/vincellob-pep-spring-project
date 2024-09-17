package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.repository.AccountRepository;
import com.example.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Account> createAccount(Account newAccount) {

        if (newAccount.getUsername() == null || newAccount.getUsername().trim().isEmpty() || newAccount.getPassword().length()<4) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Optional<Account> existingAccount = accountRepository.findByUsername(newAccount.getUsername());
        if (existingAccount.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        Account savedAccount = accountRepository.save(newAccount);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    public ResponseEntity<Account> login(Account account){
        Optional<Account> accountExists = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (accountExists.isPresent()){
            Account verify = accountExists.get();
            return new ResponseEntity<>(verify, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
