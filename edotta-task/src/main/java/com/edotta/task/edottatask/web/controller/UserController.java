package com.edotta.task.edottatask.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edotta.task.edottatask.model.Projects;
import com.edotta.task.edottatask.model.Role;
import com.edotta.task.edottatask.model.User;
import com.edotta.task.edottatask.repository.RoleRepository;
import com.edotta.task.edottatask.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class UserController {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	
	@Autowired
	public UserController(UserRepository userRepository, RoleRepository roleRepository) {

		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String usersList(Model model) {

		model.addAttribute("users", userRepository.findAll());

		return "user-list";

	}

	@RequestMapping("/gradAuthorityDeveloper")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String gradAuthDeveloper(@RequestParam("userEmail") String id, Model model) {

        User user = userRepository.findByEmail(id);
		
		Role role = roleRepository.findByRoleName("DEVELOPER");
		
		user.getRoles().add(role);
		
		userRepository.save(user);

		model.addAttribute("users", userRepository.findAll());
		return "/user-list";

	}

	
	@RequestMapping("/grandAuthorityTeamleader")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String gradAuthorityTeamleader(@RequestParam("userEmail") String id,Model model ) {
		
		User user = userRepository.findByEmail(id);
		
		Role role = roleRepository.findByRoleName("TEAM_LEADER");
		
		user.getRoles().add(role);
		
		userRepository.save(user);
		
		model.addAttribute("users",userRepository.findAll());
		
		return "/user-list";
	}
	
	@RequestMapping("/deleteUser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@RequestParam("userId")Long id, Model model) {
		
	    Optional<User> user = userRepository.findById(id);
	    User deletedUser = user.get();
	    for (Role tempRole : deletedUser.getRoles()) {
	    	tempRole.getUsers().remove(deletedUser);
	    }
	    for(Projects projects : deletedUser.getProjects()) {
	    	projects.getUsers().remove(deletedUser);
	    }
		userRepository.deleteById(id);
		model.addAttribute("users",userRepository.findAll());
		return "/user-list";
				
	}
	
	

}
