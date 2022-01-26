package com.example.AAiTStackOverflow;

import com.example.AAiTStackOverflow.Domain.Account;
import com.example.AAiTStackOverflow.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AAiTStackOverflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(AAiTStackOverflowApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(UserRepository userRepo, PasswordEncoder encoder) {

		Account admin1 = new Account();
		admin1.setFirstName("Birehan");
		admin1.setLastName("Anteneh");
		admin1.setEmail("birehananteneh4@gmail.com");
		admin1.setPassword(encoder.encode("1234567"));
		admin1.setRole(Account.Role.ADMIN);

		Account admin2 = new Account();
		admin2.setFirstName("Bereket");
		admin2.setLastName("Demissie");
		admin2.setEmail("bereketdemissie4382@gmail.com");
		admin2.setPassword(encoder.encode("1234567"));
		admin2.setRole(Account.Role.ADMIN);

		Account admin3 = new Account();
		admin3.setFirstName("Fikir");
		admin3.setLastName("Fikre");
		admin3.setEmail("fikirfikre15@gmail.com");
		admin3.setPassword(encoder.encode("1234567"));
		admin3.setRole(Account.Role.ADMIN);

		Account admin4 = new Account();
		admin4.setFirstName("Aksumawit");
		admin4.setLastName("Yemane");
		admin4.setEmail("mebrhit765@gmail.com");
		admin4.setPassword(encoder.encode("1234567"));
		admin4.setRole(Account.Role.ADMIN);

		Account admin5 = new Account();
		admin5.setFirstName("Tsedalemariam");
		admin5.setLastName("Asrate");
		admin5.setEmail("asratet@gmail.com");
		admin5.setPassword(encoder.encode("1234567"));
		admin5.setRole(Account.Role.ADMIN);

		List<Account> admins = new ArrayList<>();
		admins.add(admin1);
		admins.add(admin2);
		admins.add(admin3);
		admins.add(admin4);
		admins.add(admin5);
		return args ->  userRepo.saveAll(admins);
	}

}
