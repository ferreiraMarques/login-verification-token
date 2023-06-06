package com.login.verification.app.appuser.repository;

import com.login.verification.app.appuser.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends  JpaRepository<AppUser, Long>{
    Optional<AppUser> findEmail(String email);
}
