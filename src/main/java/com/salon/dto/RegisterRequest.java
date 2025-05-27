package com.salon.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    
    @NotBlank(message = "Nome è obbligatorio")
    private String name;

    @NotBlank(message = "Cognome è obbligatorio")
    private String surname;

    @Email(message = "Email deve essere valida")
    @NotBlank(message = "Email è obbligatoria")
    private String email;

    @NotBlank(message = "Password è obbligatoria")
    @Size(min = 6, message = "Password deve essere di almeno 6 caratteri")
    private String password;

    private String phoneNumber;
    private String address;
    private LocalDate birthdate;
}
