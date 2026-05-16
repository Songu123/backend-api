package com.son.userauthapi.repository;

import com.son.userauthapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<com.son.userauthapi.entity.User, Long> {
    Optional<User> findByUsername(String username);
}
