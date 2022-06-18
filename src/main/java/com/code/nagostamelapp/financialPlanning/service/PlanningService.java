package com.code.nagostamelapp.financialPlanning.service;

import com.code.nagostamelapp.financialPlanning.model.Planning;
import com.code.nagostamelapp.financialPlanning.model.dto.PlanningRequestDTO;

public interface PlanningService {
    Planning findByAmountAndTaget(String target, Float amount);
    Planning findByTarget(String target);
    Planning savePlanning(PlanningRequestDTO dto);
}
