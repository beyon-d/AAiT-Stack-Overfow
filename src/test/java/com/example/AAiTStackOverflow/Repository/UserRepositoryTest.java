package com.example.AAiTStackOverflow.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.AAiTStackOverflow.Domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    // test methods go below
    @Test
    public void testCreateUser() {
        Account user = new Account();
        user.setEmail("fikirfikre@gmial");
        user.setPassword("1234567");
        user.setFirstName("fikir");
        user.setLastName("fikre");

        Account savedUser = repo.save(user);

        Account existUser = entityManager.find(Account.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
}