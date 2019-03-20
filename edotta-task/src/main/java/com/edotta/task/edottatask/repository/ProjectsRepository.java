package com.edotta.task.edottatask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edotta.task.edottatask.model.Projects;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects,Long> {
	
	

}
