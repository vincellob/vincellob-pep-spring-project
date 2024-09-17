package com.example.service;

import org.springframework.web.bind.annotation.*;
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

    public ResponseEntity<Account> createAccount(Account account) {
        if (!accountRepository.findByUsername(account.getUsername()))
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        if (account.getUsername().length()>0 && account.getPassword().length()>=4) {
            Account newAccount = accountRepository.save(account);
            return new ResponseEntity<>(newAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
