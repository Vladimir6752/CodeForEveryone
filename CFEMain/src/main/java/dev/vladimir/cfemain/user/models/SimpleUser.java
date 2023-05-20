package dev.vladimir.cfemain.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class SimpleUser {
    private String username;
    private String password;
}
