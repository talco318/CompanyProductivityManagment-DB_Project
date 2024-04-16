package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface CompanyUI {

//	public void saveToFile() throws FileNotFoundException, IOException;
	
//	public boolean readFromFile(TalCohen_ShaniAmos.View.ViewInterface view);
	
	public boolean readDepFromDB(View.ViewInterface view);
	
	public ArrayList<Department> getAllDepartments();

	public int getIndexOfDep(String depName);

	public boolean addDepartment(String name, boolean isChangable, boolean isSynchronize);

	public void setDepArr(ArrayList<Department> depFromDB);
	
	public boolean addRoleWithEmployee(String departmentName, String roleName, String empInThisRole,
			boolean isChangable, boolean isSynchronize, int salesPresent, int hoursNum, String salaryType,
			int payPerHour, String empPreference, int movingHours, boolean cWFromHome);

//	public boolean addRoleWithEmployeeFromDB(String departmentName, String roleName, String empInThisRole,
//			boolean isChangable, boolean isSynchronize, int salesPresent, int hoursNum, String salaryType,
//			int payPerHour, String empPreference, int movingHours, boolean cWFromHome);
	
	public boolean addRoleWithEmployeeFromDB1(Role roleFromDB,EmployeePreference empPrefFromDB, /*String empInThisRole= empName */
			  int salesPresent, int hoursNum, String salaryType,int payPerHour);
	
	public boolean isDepChangeable(String depName);

	public boolean isRoleChangeable(String roleName);
	
	public boolean isDepSync(String depName);

	public boolean changeWorkMethodByDep(String depName, String managerDecision, int movingHours);

	public ArrayList<Employee> getAllEmployees();

	public boolean changeWorkMethodByRole(String depName, String empName, String managerDecision, int movingHours);

	public String toStringResults();

	public Employee creatEmpBaseSalesDB(Employee newEmp, EmployeePreference empPrefFromDB, Role roleFromDB,
			String salaryType, int salesPresent);
	public Employee creatEmpBase(Employee newEmp, EmployeePreference empPrefFromDB,Role roleFromDB, String salaryType, int salesPresent);
}
