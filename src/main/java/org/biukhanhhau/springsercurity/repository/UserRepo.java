package org.biukhanhhau.springsercurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.biukhanhhau.springsercurity.model.User;
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}