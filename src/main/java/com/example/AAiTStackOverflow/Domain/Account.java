package com.example.AAiTStackOverflow.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uc_account_email", columnNames = {"email"})
})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First Name can't be empty")
    @Size(message = "First Name can't be more 15 characters", max = 15)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last Name can't be empty")
    @Size(message = "Last Name can't be more 15 characters", max = 15)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email")
    @NotBlank(message = "Email can't be empty")


    private String email;

    @Column(nullable = false, length = 64)
    @NotBlank(message = "Password can't be empty")
    @Size(message = "Password Can't be less than 6 characters.", min = 6)
    private String password;


    @OneToMany(mappedBy = "author", cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Question> questions;

 

    public static enum Role{
        USER, ADMIN;
        private Role(){

        }
    }
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;


}
