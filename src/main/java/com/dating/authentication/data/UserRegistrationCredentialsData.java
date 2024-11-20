package com.dating.authentication.data;

import lombok.Data;

@Data
public class UserRegistrationCredentialsData {
    private String userName;
    private String mail;
    private String password;
}
