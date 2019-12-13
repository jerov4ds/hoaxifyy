package com.hoaxify.hoaxify.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "{hoaxify.constraints.username.NotNull.message}")
    @Size(min = 5, max = 25)
    @UniqueUsername
    private String username;
    @NotNull
    @Size(min = 5, max = 25)
    private String displayName;
    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
