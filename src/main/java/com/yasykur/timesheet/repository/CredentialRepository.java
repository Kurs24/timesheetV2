package com.yasykur.timesheet.repository;

import com.yasykur.timesheet.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Integer> {
    @Query(value = "SELECT new com.yasykur.timesheet.config.MyUserDetails(e.email, c.password, r.name, e.firstName) FROM Employee e JOIN e.role r JOIN e.credential c WHERE e.email = ?1")
    public Optional<UserDetails> login(String email);
}
