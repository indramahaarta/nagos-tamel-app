package com.code.nagostamelapp.user.model;

import com.code.nagostamelapp.financialPlanning.model.Planning;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users_table")
public class UserModel {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "planning_set")
    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL)
    private Set<Planning> planningSet;

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
