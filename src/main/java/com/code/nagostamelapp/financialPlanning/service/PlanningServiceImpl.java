package com.code.nagostamelapp.financialPlanning.service;

import com.code.nagostamelapp.financialPlanning.model.Planning;
import com.code.nagostamelapp.financialPlanning.model.dto.PlanningRequestDTO;
import com.code.nagostamelapp.financialPlanning.repository.PlanningRepository;
import com.code.nagostamelapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanningServiceImpl implements PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private UserService userService;

    @Override
    public Planning findByAmountAndTaget(String target, Float amount) {
        return planningRepository.findByAmountAndTarget(target, amount);
    }

    @Override
    public Planning findByTarget(String target) {
        return planningRepository.findByTarget(target);
    }

    @Override
    public Planning savePlanning(PlanningRequestDTO dto) {
        Planning planning = new Planning();

        planning.setAmount(dto.getAmount());
        planning.setTarget(dto.getTarget());
        planning.setRange(dto.getRange());
        planning.setUserModel(userService.getUserByUsername(dto.getUsername()));
        planningRepository.save(planning);

        return planning;
    }
}
