package com.code.nagostamelapp.financialPlanning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanningResponseDTO {
    private Float amount;
    private String target;
    private Integer range;
    private Float monthly;
    private String amountStr;
    private String monthlyStr;
}
