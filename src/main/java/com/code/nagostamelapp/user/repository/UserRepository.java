package com.code.nagostamelapp.user.repository;

import com.code.nagostamelapp.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    UserModel findByUsernameAndPassword(String username, String password);
    UserModel findByUsername(String username);
}
