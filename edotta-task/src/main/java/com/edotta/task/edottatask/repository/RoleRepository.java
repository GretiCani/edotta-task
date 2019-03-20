package com.edotta.task.edottatask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edotta.task.edottatask.model.Projects;
import com.edotta.task.edottatask.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Projects,Long> {
	
	@Query("SELECT r FROM Role r WHERE r.name = ?1")
	Role findByRoleName(String roleName);

}
