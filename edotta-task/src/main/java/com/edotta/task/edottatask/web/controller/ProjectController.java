package com.edotta.task.edottatask.web.controller;



import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edotta.task.edottatask.model.Projects;
import com.edotta.task.edottatask.model.User;
import com.edotta.task.edottatask.repository.ProjectsRepository;
import com.edotta.task.edottatask.repository.UserRepository;
import com.edotta.task.edottatask.security.IAuthenticationFacade;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	private final ProjectsRepository projectRepository;
	
    private final UserRepository userRepository;
	
	@Autowired
	public IAuthenticationFacade authentificationFacade;

	@Autowired
	public ProjectController(ProjectsRepository projectRepository,UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/list")
	public String projectsList(Model model) {
		model.addAttribute("projects", projectRepository.findAll());
		return "projects-list";
	}

	
	@GetMapping("/addprojects")
	@PreAuthorize("hasAuthority('TEAM_LEADER')")
	public String addProject(Projects project) {

		return "add-project";
	}

	@PostMapping("/save")
	public String addProject(@Valid Projects project, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-project";
		}
        
		Authentication authentication = authentificationFacade.getAuthentication();
		User currentUser  = userRepository.findByEmail(authentication.getName());
	
		
        project.getUsers().add(currentUser);
        currentUser.getProjects().add(project);
        
		projectRepository.save(project);
		
		model.addAttribute("projects", projectRepository.findAll());

		return "index";
	}

	@GetMapping("/viewProject")
	public String viewProject(@RequestParam("projectId")Long id,Model model) {
		Projects project = projectRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
		
		model.addAttribute("projects", project);
		return "project-view";
		
	}
	
	
	@GetMapping("participateInProject")
	public String participateInProject(@RequestParam("projectId")Long id,Model model) {
		
		Authentication authentication = authentificationFacade.getAuthentication();
		User user = userRepository.findByEmail(authentication.getName());
		Optional<Projects> project = projectRepository.findById(id);
		Projects currentProject = project.get();
		
		currentProject.getUsers().add(user);
		user.getProjects().add(currentProject);
		
		projectRepository.save(currentProject);
		
		model.addAttribute("projects",projectRepository.findAll());
		
		return "index";
	}
	
	@GetMapping("/edit")
	@PreAuthorize("hasAuthority('TEAM_LEADER')")
	public String showUpdateForm(@RequestParam("projectId") Long id, Model model) {

		Projects project = projectRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));

		model.addAttribute("projects", project);
		
		return "add-project";
	}

	@PostMapping("/update/{id}")
	public String updateProject(@PathVariable("id") Long id, @Valid Projects project, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "add-project";
		}

		projectRepository.save(project);
		model.addAttribute("projects", projectRepository.findAll());
		return "projects-list";

	}

	@GetMapping("/delete/{id)")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteProjects(@PathVariable("id") Long id, Model model) {

		Projects project = projectRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));

		projectRepository.delete(project);
		model.addAttribute("projects", projectRepository.findAll());

		return "projects-list";
	}

}
