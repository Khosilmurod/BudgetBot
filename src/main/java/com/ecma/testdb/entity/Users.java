package com.ecma.testdb.entity;

import com.ecma.testdb.entity.enums.BotPage;
import com.ecma.testdb.entity.template.Defaults;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users extends Defaults {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String phoneNumber;
    @JsonIgnore
    private String password;
    @Column(unique = true)
    private String telegramId;
    private BotPage botPage;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public Users(String firstName, String lastName, String username, String telegramId, List<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.telegramId = telegramId;
        this.roles = roles;
    }
}
