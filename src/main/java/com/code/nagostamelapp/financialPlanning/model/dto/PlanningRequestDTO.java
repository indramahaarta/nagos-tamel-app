package com.code.nagostamelapp.financialPlanning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanningRequestDTO {
    private Float amount;
    private String target;
    private Integer range;
    private String username;
}
