package com.dating.authentication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "credentials")
public class UserCredentialsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "mail", nullable = false)
    private String userMail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "registration_time")
    private Date registrationTime;
}
