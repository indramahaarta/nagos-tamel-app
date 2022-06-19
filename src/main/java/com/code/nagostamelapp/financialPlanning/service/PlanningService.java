package com.code.nagostamelapp.financialPlanning.service;

import com.code.nagostamelapp.financialPlanning.model.Planning;
import com.code.nagostamelapp.financialPlanning.model.dto.PlanningRequestDTO;
import com.code.nagostamelapp.financialPlanning.model.dto.PlanningResponseDTO;
import com.code.nagostamelapp.user.model.UserModel;

import java.util.List;

public interface PlanningService {
    Planning findByAmountAndTaget(String target, Float amount);
    Planning findByTarget(String target);
    List<PlanningResponseDTO> findByUserModel(UserModel userModel);
    Planning savePlanning(PlanningRequestDTO dto);
}
