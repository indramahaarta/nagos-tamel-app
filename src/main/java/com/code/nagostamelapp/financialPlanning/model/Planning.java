package com.code.nagostamelapp.financialPlanning.model;

import com.code.nagostamelapp.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "PLANNING_TB")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Planning implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="usermodel_id", nullable = false)
    private UserModel userModel;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "target")
    private String target;

    @Column(name = "range")
    private Integer range;
}
