package com.dumb.tasks.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dumb.tasks.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
