package com.example.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
   
    Optional<Account> findByUsername(String username);

    @Query(value = "SELECT * FROM account WHERE username=?1 AND password=?2", nativeQuery=true)
    public Optional<Account> findByUsernameAndPassword(String username, String password);
}
