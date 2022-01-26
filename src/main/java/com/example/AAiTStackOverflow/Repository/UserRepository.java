package com.example.AAiTStackOverflow.Repository;

import com.example.AAiTStackOverflow.Domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.AbstractCollection;

public interface UserRepository extends JpaRepository<Account, Long> {
    @Query("SELECT u FROM Account u WHERE u.email = ?1")
    public Account findByEmail(String email);



}
