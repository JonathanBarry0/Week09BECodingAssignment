package projects.service;

import projects.doa.ProjectDao;
import projects.entity.Project;

public class ProjectService {
	
	/*
	 * The service layer in this small application is implemented by a single file, ProjectService.java. Mostly this file acts as a 
	 * pass-through between the main application file that runs the menu (ProjectsApp.java) and the DAO (Data Access Object) file 
	 * in the data layer (ProjectDao.java).
	 */
	
	private ProjectDao projectDao = new ProjectDao();

	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}

}
