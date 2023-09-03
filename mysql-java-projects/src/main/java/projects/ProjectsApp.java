package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	
	// @formatter:off
	private List<String> operations = List.of(
		"1) Add a project"
	);
	// @formatter:on
	/*
	 * the formatter is turned off here to prevent the Eclipse formatter from reformatting the list
	 */
	private ProjectService projectService = new ProjectService();
	private Scanner sc = new Scanner(System.in); // Scanner sc is outside the main method because it will be used in multiple methods

	public static void main(String[] args) {
		
		new ProjectsApp().processUserSelections();
		/*
		 * call a new ProjectsApp object and call method on it
		 */

	}

	private void processUserSelections() {
		boolean done = false;
		
		while(!done) { // while done is not true...
			try {
				int selection = getUserSelection(); // assign method's, getUserSelection, return value to an int
				
				switch(selection) {
				case -1:
					done = exitMenu();
					break;
				case 1:
					createProject();
					break;
				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
				}
			}
			catch(Exception e) {
				System.out.println("\nEror: " + e + " Try again."); // print the exception
			}
		}
	}
		
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}

	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if (Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Integer.parseInt(input); // will return input if the input is a valid Integer
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}

	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if (Objects.isNull(input)) {
			return null;
		}
		
		try {
			return new BigDecimal(input).setScale(2); // '.setScale(2)' is a method to set the decimal precision to two places. e.g: 1.22
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}

	private boolean exitMenu() {
		System.out.println("\nExiting the menu.");
		return true; // ends the while loop
	}

	private int getUserSelection() {
		printOperatons(); // will print the available operations
		
		Integer input = getUserInput("Enter a menu selection"); // the parameter
		
		return Objects.isNull(input) ? -1 : input; // says, "if variable 'input' is null, return -1 to exit the app, if not return the value of 'input'
	}

	private Integer getUserInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number");
		}
	}

	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = sc.nextLine();
		
		return input.isBlank() ? null : input.trim();
	}

	private void printOperatons() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:"); // prompts the user
		
		operations.forEach(line -> System.out.println("   " + line)); // Lambda is used to say that for each 'line' in the operations list of string,
//		print the line following an indent 
	}

}
