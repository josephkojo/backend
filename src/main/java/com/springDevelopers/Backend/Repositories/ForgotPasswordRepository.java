package com.springDevelopers.Backend.Repositories;

import com.springDevelopers.Backend.Entities.ForgotPassword;
import com.springDevelopers.Backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    @Query("SELECT fp FROM ForgotPassword fp WHERE fp.otp = ?1 AND fp.user = ?2")
    Optional<ForgotPassword> findConfirmationCodeAndEmail(Integer code, User user);
    @Query("SELECT fp FROM ForgotPassword fp WHERE fp.user = ?1")
    List<ForgotPassword> findForgotPasswordGeneratedByUser(User user);


}
