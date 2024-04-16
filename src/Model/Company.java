package Model;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import View.ViewInterface;
import mySQL.mySQL;

public class Company implements CompanyUI, Serializable {
	protected String name;
	protected ArrayList<Department> allDepartments = new ArrayList<Department>();
	protected ArrayList<Employee> allEmployees = new ArrayList<Employee>();
	protected ArrayList<Role> allRoles = new ArrayList<Role>();
	protected double compHoursProfit;
	public static final String F_NAME = "Simulation.txt"; // file name

//	public void saveToFile() throws FileNotFoundException, IOException {
//		try {
//			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(F_NAME));
//			output.writeObject(allDepartments);
//			output.writeObject(allEmployees);
//			output.writeObject(allRoles);
//			output.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	
	
	public boolean readDepFromDB(View.ViewInterface view) {
			ArrayList<Department> allDepFromFile = mySQL.readDepFromDB(view);
			
			//System.out.println(allDepFromFile);
			allDepartments = allDepFromFile;
			for (int i = 0; i < allDepFromFile.size(); i++) {
				//System.out.println(allDepFromFile.get(i).getName());
				view.addToComboDep(allDepFromFile.get(i).getName());
				view.addToListviewDep(allDepartments.get(i).getName(), allDepartments.get(i).getIsChangeable(), allDepartments.get(i).getIsSynchronized());
				view.addToArrEmpsInDepCombo();
			}
			//System.out.println(allDepFromFile);
			//do the same to all values we need
			
			
			return true;
	}

	public int getIndexOfDep(String depName) {
		for (int i = 0; i < allDepartments.size(); i++) {
			if (allDepartments.get(i).getName().equalsIgnoreCase(depName)) {
				return i;
			}
		}
		return -1;
	}

	public boolean isRoleChangeable(String roleName) {
		for (int i = 0; i < allEmployees.size(); i++) {
			if (allEmployees.get(i).getRoleOfEmployee().getRoleName().equals(roleName)) {
				return allEmployees.get(i).getRoleOfEmployee().isChangable;
			} 
		}
		return false;
	}

	public boolean isDepChangeable(String depName) {
		for (int i = 0; i < allDepartments.size(); i++) {
			if (allDepartments.get(i).getName().equalsIgnoreCase(depName)) {
				return allDepartments.get(i).getIsChangeable();
			}
		}
		return false;
	}

	public boolean isDepSync(String depName) {
		for (int i = 0; i < allDepartments.size(); i++) {
			if (allDepartments.get(i).getName().equals(depName)) {
				boolean result = allDepartments.get(i).getIsSynchronized();
				return result;
			}
		}
		return false;
	}

	public ArrayList<Department> getAllDepartments() {
		return allDepartments;
	}

	public boolean addDepartment(String name, boolean isChangable, boolean isSynchronize) {
		Department newDep = new Department(name, isChangable, isSynchronize);
		allDepartments.add(newDep);
		//add dep to db
		mySQL.addDepartmentToDB(newDep);

		return true;
	} 

	public boolean addRoleWithEmployee(String departmentName, String roleName, String empInThisRole,
			boolean isChangable, boolean isSynchronize, int salesPresent, int hoursNum, String salaryType,
			int payPerHour, String empPreference, int movingHours, boolean cWFromHome) {
		Role newRole = new Role(departmentName, roleName, empInThisRole, isChangable, isSynchronize, cWFromHome);
		EmployeePreference empPre = new EmployeePreference(empPreference, movingHours);
		
		//Add data to sql server
		mySQL.addEmployeeToDB(newRole, salaryType, empInThisRole,salesPresent, hoursNum,payPerHour  );
		mySQL.addRoleToDB(newRole, salaryType, empInThisRole, empPre, salesPresent,hoursNum, payPerHour );
		mySQL.addEmpTypeToDB(empInThisRole, salaryType, salesPresent, hoursNum,payPerHour);
		mySQL.addEmpPrefToDB(empInThisRole, empPre);
		
		Department currentDep = null;
		allRoles.add(newRole);
		for (int i = 0; i < allDepartments.size(); i++) {
			if (allDepartments.get(i).getName().equals(departmentName)) {
				currentDep = allDepartments.get(i);
				allDepartments.get(i).updateFlag(newRole);
			}
		}
		if (salaryType.equalsIgnoreCase("base")) {
			EmployeeBase newEmployeeBase = new EmployeeBase(newRole, salaryType, empInThisRole, empPre);
			allEmployees.add(newEmployeeBase);
			currentDep.addEmp(newEmployeeBase);
			return true;
		}
		if (salaryType.equalsIgnoreCase("baseSales")) {
			EmployeeBaseSales newEmployeeBaseSales = new EmployeeBaseSales(newRole, salaryType, empInThisRole, empPre,
					salesPresent);
			allEmployees.add(newEmployeeBaseSales);
			currentDep.addEmp(newEmployeeBaseSales);
			return true;
		}
		if (salaryType.equalsIgnoreCase("perHours")) {
			EmployeePerHours newEmployeePerHours = new EmployeePerHours(newRole, salaryType, empInThisRole, empPre,
					hoursNum, payPerHour);
			allEmployees.add(newEmployeePerHours);
			currentDep.addEmp(newEmployeePerHours);
			return true;
		}

		return false;
	}

	
	public boolean addRoleWithEmployeeFromDB1(Role roleFromDB,EmployeePreference empPrefFromDB, 
			  				   int salesPresent, int hoursNum, String salaryType,int payPerHour) {

		
		
		Department currentDep = null;
		allRoles.add(roleFromDB);
		//System.out.println(allRoles);
		for (int i = 0; i < allDepartments.size(); i++) {
			if (allDepartments.get(i).getName().equals(roleFromDB.getDepName())) {
				currentDep = allDepartments.get(i);
				allDepartments.get(i).updateFlag(roleFromDB);
			}
		}
		//Check what is the salary type 
		if (salaryType.equalsIgnoreCase("base")) {
			EmployeeBase newEmployeeBase = new EmployeeBase(roleFromDB, salaryType, roleFromDB.getEmpInThisRole(), empPrefFromDB);
			allEmployees.add(newEmployeeBase);
			currentDep.addEmp(newEmployeeBase);
			return true;
		}
		if (salaryType.equalsIgnoreCase("baseSales")) {
			EmployeeBaseSales newEmployeeBaseSales = new EmployeeBaseSales(roleFromDB, salaryType, roleFromDB.getEmpInThisRole(), empPrefFromDB,
					salesPresent);
			allEmployees.add(newEmployeeBaseSales);
			currentDep.addEmp(newEmployeeBaseSales);
			return true;
		}
		if (salaryType.equalsIgnoreCase("perHours")) {
			EmployeePerHours newEmployeePerHours = new EmployeePerHours(roleFromDB, salaryType, roleFromDB.getEmpInThisRole(), empPrefFromDB,
					hoursNum, payPerHour);
			allEmployees.add(newEmployeePerHours);
			currentDep.addEmp(newEmployeePerHours);
			return true;
		}

		return false;
	}	
	
	public boolean changeWorkMethodByDep(String depName, String managerDecision, int movingHours) {
		for (int i = 0; i < allDepartments.size(); i++) {
			if (allDepartments.get(i).getName().equals(depName)) {
				if (allDepartments.get(i).isBlackFlag()) {
					return false;
				}
				ManagerDecision decision = new ManagerDecision(managerDecision, movingHours);
				allDepartments.get(i).setManagerDecisionDep(decision);
				//add manager decision to DB
				mySQL.addDepManagerDecDB(depName, decision);
				return true;
			}
		}
		return false;
	}
	

	public boolean changeWorkMethodByRole(String depName, String roleName, String managerDecision, int movingHours) {
		Department dep = null;
		for (int i = 0; i < allDepartments.size(); i++) {
			if (allDepartments.get(i).getName().equals(depName)) {
				dep = allDepartments.get(i);
				break;
			}
		}
		for (int j = 0; j < dep.getAllEmployees().size(); j++) {
			if (dep.allEmployee.get(j).isThisIsTheCorrectRole(roleName)) {
				ManagerDecision decision = new ManagerDecision(managerDecision, movingHours);
				dep.allEmployee.get(j).setManagerDecisionEmp(decision);
				//set setManagerDecision to emp to the DB
				mySQL.addRoleManagerDecDB(roleName, decision);
				return true;
			} 
		}
		return false;
	}

	public ArrayList<Employee> getAllEmployees() {
		return allEmployees;
	}

	public void addEmployee(Employee newEmployee) {
		allEmployees.add(newEmployee);
	}

	public double getHoursProfit() {
		return compHoursProfit;
	}

	public void setCompHoursProfit(double compHoursProfit) {
		this.compHoursProfit = compHoursProfit;
	}

	
	public void setDepArr(ArrayList<Department> depFromDB) {
		this.allDepartments = depFromDB;
	} 
	
	public String getName() {
		return name;
	}

	public double sumOfDepProfit() {
		compHoursProfit= 0;
		for (int i = 0; i < allDepartments.size(); i++) {
			compHoursProfit += allDepartments.get(i).getDepHoursProfit();
		}
		return compHoursProfit;
	}

	public void updateEmpProfit(String EmpName) {
		for(int i=0; i<allEmployees.size(); i++) {
			if(allEmployees.get(i).getEmpName().equalsIgnoreCase(EmpName)) {
				String DepName = allEmployees.get(i).getRoleOfEmployee().getDepName();
				updateEmpProfInDep(DepName);
			}
		}
	}
	public void updateEmpProfInDep(String depName) {
		for(int i=0; i<allDepartments.size(); i++) {
			if(allDepartments.get(i).getName().equalsIgnoreCase(depName)) {
				allDepartments.get(i).updateProfForEmp(depName);
			}
		}
	}
	
	public String toStringResults() {
		
		StringBuffer str = new StringBuffer("");
		for (int i = 0; i < allDepartments.size(); i++) {
			str.append(allDepartments.get(i).toStringResults() + "\n");
		}
		str.append("All company Profit: " + sumOfDepProfit() + "\n");
		return str.toString();
	}
	
	public Employee creatEmpBaseSalesDB(Employee newEmp, EmployeePreference empPrefFromDB,Role roleFromDB, String salaryType, int salesPresent) {
		
		newEmp=new EmployeeBaseSales(roleFromDB, salaryType, roleFromDB.getEmpInThisRole(), empPrefFromDB,salesPresent);
		
	return  newEmp;
		
	}
	
public Employee creatEmpBase(Employee newEmp, EmployeePreference empPrefFromDB,Role roleFromDB, String salaryType, int salesPresent) {
		
		newEmp=new EmployeeBase(roleFromDB, salaryType, roleFromDB.getEmpInThisRole(), empPrefFromDB);
		
	return  newEmp;
		
	}

}
