package com.edotta.task.edottatask.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Projects {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message = "description is required*")
	private String description;
	
	@NotEmpty(message = "Content is required*")
	@Lob
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "starting_date")
	private Date startingDate = new Date();
	
	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy = "projects")
	private Set<User> users = new HashSet<>();
	
	

	public Projects() {
		super();
	}

	public Projects(Long id, @NotEmpty(message = "description is required*") String description,
			@NotEmpty(message = "Content is required*") String content, @NotEmpty Date startingDate) {
		super();
		this.id = id;
		this.description = description;
		this.content = content;
		this.startingDate = startingDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Projects [id=" + id 
				+ ", description=" 
				+ description 
				+ ", content=" 
				+ content 
				+ ", startingDate="
				+ startingDate 
				+ ", users=" 
				+ users + "]";
	}
	
	
	

}
