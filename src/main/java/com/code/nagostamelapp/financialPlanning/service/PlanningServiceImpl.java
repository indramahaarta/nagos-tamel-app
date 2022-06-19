package com.code.nagostamelapp.financialPlanning.service;

import com.code.nagostamelapp.dashboard.controller.ConverterToRupiah;
import com.code.nagostamelapp.financialPlanning.model.Planning;
import com.code.nagostamelapp.financialPlanning.model.dto.PlanningRequestDTO;
import com.code.nagostamelapp.financialPlanning.model.dto.PlanningResponseDTO;
import com.code.nagostamelapp.financialPlanning.repository.PlanningRepository;
import com.code.nagostamelapp.user.model.UserModel;
import com.code.nagostamelapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<PlanningResponseDTO> findByUserModel(UserModel userModel) {
        List<Planning> plan = planningRepository.findAllByUserModel(userModel);
        List<PlanningResponseDTO> dtos = new ArrayList<>();

        for(var p : plan) {
            PlanningResponseDTO dto  = new PlanningResponseDTO();
            dto.setAmount(p.getAmount());
            dto.setMonthly(p.getAmount()/p.getRange());
            dto.setRange(p.getRange());
            dto.setTarget(p.getTarget());
            dto.setAmountStr(ConverterToRupiah.convert(dto.getAmount()));
            dto.setMonthlyStr(ConverterToRupiah.convert(dto.getMonthly()));

            dtos.add(dto);
        }

        return dtos;
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
