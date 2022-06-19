package com.code.nagostamelapp.financialPlanning.repository;

import com.code.nagostamelapp.financialPlanning.model.Planning;
import com.code.nagostamelapp.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
    Planning findByAmountAndTarget(String Target, Float Amount);
    Planning findByTarget(String Target);
    List<Planning> findAllByUserModel(UserModel userModel);
}