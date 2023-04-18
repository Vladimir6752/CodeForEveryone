package dev.vladimir.cfemain.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleUser {
    private String username;
    private String password;
}
