package com.dumb.tasks.repositorys;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.dumb.tasks.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
