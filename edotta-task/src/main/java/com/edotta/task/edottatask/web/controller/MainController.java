package com.edotta.task.edottatask.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.edotta.task.edottatask.repository.ProjectsRepository;

@Controller
public class MainController {
	
	@Autowired
	private  ProjectsRepository projectRepository;
	
	@GetMapping("/")
    public String root(Model model) {
		model.addAttribute("projects", projectRepository.findAll());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

  
		
}
