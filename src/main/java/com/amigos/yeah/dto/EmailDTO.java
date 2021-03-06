package com.amigos.yeah.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO {

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email obrigatório")
    private String email;

    public EmailDTO() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
