package application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Users {
	private ArrayList<User> users = new ArrayList<User>();
	private String fileName = "src/application/UserInfo";
	protected Integer numUsers = setOriginalNumUsers();
	protected Boolean isEmpty = true;
	
	public int setOriginalNumUsers() {
		
		Integer numUsers = 0;
		
		// reads first number in file which indicates # of users on file
		try(Scanner scan = new Scanner(new File(fileName))) {
			numUsers = Integer.parseInt(scan.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return numUsers;
		
	}
	
	public void setNumUsers(int num) {
		this.numUsers = num;
	}
	
	public Integer getNumUsers() {
		return this.numUsers;
	}
	
	public void uploadUsers() throws FileNotFoundException {
		try(Scanner scan = new Scanner(new File(fileName))) {
			scan.nextLine();
			
			// if no users written to file, alert and exit
			if (numUsers == 0) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("No Users.");
				errorAlert.setContentText("There are no users currently on file.");
				errorAlert.show();
				return;
			}
			
			// otherwise, load users into array
			String name = "";
			String type = "";
			String email = "";
			String password = "";
			String date = "";
			
			for(int i = 0; i < numUsers; i++) {
				for(int j = 0; j < 5; j++) {
					if(j == 0) {
						name = scan.nextLine();
					} else if (j == 1) {
						type = scan.nextLine();
					} else if (j == 2) {
						email = scan.nextLine();
					} else if (j == 3) {
						password = scan.nextLine();
					} else if (j == 4) {
						date = scan.nextLine();
					}
				}
				User loadUser = new User(name, type, email, password, date);
				users.add(loadUser);
				
				this.isEmpty = false;
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error: File not found.");
		}
	}
	
	public User searchUsersByEmail(String searchKey) {
		User noFoundUser = new User();

		// if searchKey matches user email, return the user
		for(int i = 0; i < numUsers; i++) {
			if(searchKey.contentEquals(users.get(i).getEmail())) {
				return users.get(i);
			}
		}
		
		// otherwise, return an empty user to indicate failure
		return noFoundUser;
	}
	
	public void addUser(String name, String type, String email, String password, String date) {
		File tempFile = new File("tempFile.txt");
		File currentFile = new File(fileName);
		String textToAdd = String.format("%s%n%s%n%s%n%s%n%s%n", name, type, email, password, date);
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			
			String currentLine;
			
			// changes # of users
			if((currentLine = reader.readLine()) != null) {
				if(currentLine.matches("[0-9]+")) {
					Integer num = Integer.valueOf(currentLine);
					num++;
					currentLine = num.toString();
				}
				writer.write(String.format("%s%n", currentLine));
			}
			
			// makes a new file that skips the deleted user
			while((currentLine = reader.readLine()) != null) {
				writer.write(String.format("%s%n", currentLine));
			}
			writer.write(textToAdd);
			
			writer.close();
			reader.close();
			
			Files.deleteIfExists(Paths.get(fileName));
			tempFile.renameTo(currentFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User addedUser = new User(name, type, email, password, date);
		users.add(addedUser);
		this.setNumUsers(numUsers++);
		System.out.println("User added.");
	}
	
	public void deleteUser(String email) {
		for(int i = 0; i < users.size(); i++) {
			if((users.get(i).email).contentEquals(email) == true) {
				File tempFile = new File("tempFile.txt");
				File currentFile = new File(fileName);
				try {
					BufferedReader reader = new BufferedReader(new FileReader(fileName));
					BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
					
					String name = users.get(i).getName();
					String currentLine;
					
					// changes # of users
					if((currentLine = reader.readLine()) != null) {
						if(currentLine.matches("[0-9]+")) {
							Integer num = Integer.valueOf(currentLine);
							num--;
							currentLine = num.toString();
						}
						writer.write(String.format("%s%n", currentLine));
					}
					
					// makes a new file that skips the deleted user
					while((currentLine = reader.readLine()) != null) {
						if(currentLine.equals(name)) {
							reader.readLine();
							reader.readLine();
							reader.readLine();
							reader.readLine();
							continue;
						}
						writer.write(String.format("%s%n", currentLine));
					}
					
					writer.close();
					reader.close();
					
					Files.deleteIfExists(Paths.get(fileName));
					tempFile.renameTo(currentFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				users.remove(i);
				System.out.println("User deleted.");
				return;
			}
				
		}
		
		System.out.println("No such user found.");
	}
	
	public void changeUser(String email, String name,  String joinDate,  String password, String role) {
		for(int i = 0; i < users.size(); i++) {
			if((users.get(i).email).contentEquals(email) == true) {
				File tempFile = new File("tempFile.txt");
				File currentFile = new File(fileName);
				try {
					BufferedReader reader = new BufferedReader(new FileReader(fileName));
					BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
					
					String currentLine;
					
					// changes # of users
//					if((currentLine = reader.readLine()) != null) {
//						writer.write(String.format("%s%n", currentLine));
//					}
					
					// makes a new file that skips the deleted user
					while((currentLine = reader.readLine()) != null) {
						if(currentLine.equals(name)) {
							writer.write(String.format(name + "%n"));
							reader.readLine();
							writer.write(String.format(role + "%n"));
							reader.readLine();
							writer.write(String.format(email + "%n"));
							reader.readLine();
							writer.write(String.format(password + "%n"));
							reader.readLine();
							writer.write(String.format(joinDate + "%n"));
							continue;
						}
						writer.write(String.format("%s%n", currentLine));
					}
					
					writer.close();
					reader.close();
					
					Files.deleteIfExists(Paths.get(fileName));
					tempFile.renameTo(currentFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				users.get(i).accountType = role;
				users.get(i).email = email;
				users.get(i).joinDate = joinDate;
				users.get(i).name = name;
				users.get(i).password = password;
				System.out.println("User changed.");
				return;
			}
		}
		
		System.out.println("No such user found.");
	}
	
	public void printUsers() {
		for(int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).name);
		}
	}
	
	public User getUser(Integer i) {
		return users.get(i);
	}
	
	//need to add the following information for the admin page...
    public int getNumBuyers() {
        int numBuyers = 0;
        for (int i = 0; i < numUsers; i++) {
            if (users.get(i).accountType == "Buyer") {
                numBuyers += 1;
            }
        }
        return numBuyers;
    }
    
    public int getNumSellers() {
        int numSellers = 0;
        for (int i = 0; i < numUsers; i++) {
            if (users.get(i).accountType == "Seller") {
                numSellers += 1;
            }
        }
        return numSellers;
    }
    
    public int getNumAdmins() {
        int numAdmins = 0;
        for (int i = 0; i < numUsers; i++) {
            if (users.get(i).accountType == "Admin") {
                numAdmins += 1;
            }
        }
        return numAdmins;
    }
        
    //end of admin additions

}

