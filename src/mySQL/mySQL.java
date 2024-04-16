package mySQL;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.sql.*;
import Model.CompanyUI;
import Model.Department;
import Model.Employee;
import Model.EmployeeBase;
import Model.EmployeeBaseSales;
import Model.EmployeePerHours;
import Model.EmployeePreference;
import Model.ManagerDecision;
import Model.Role;
import View.ViewInterface;


public class mySQL {										   //DONT FORGET TO :
	public static String serverPass = "FILL THE PASSWORD HERE!"; // Enter between "" your password !!!!

    public static int addDepartmentToDB(Department dep) {
    	

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);
			Statement stmt;
			stmt = conn.createStatement();
			int r;
			String depName=dep.getName();
			try {
				//checking if there is duplicate rows 
				ResultSet rs = stmt.executeQuery("SELECT * FROM departments WHERE depName ='"+depName+"'");
			    if(rs.next())
			    {
			    	//System.out.println("duplicate Department...updating ");
			    	PreparedStatement update = conn.prepareStatement("UPDATE departments SET isChangable = ?, isSynchronize = ? WHERE depName= ?");
			    	update.setString(3, dep.getName());
			    	update.setBoolean(1, dep.getIsChangeable());
			    	update.setBoolean(2, dep.getIsSynchronized());
			    	update.executeUpdate();
			    }else {
				
				PreparedStatement stm = conn.prepareStatement("insert INTO departments (depName,isChangable,isSynchronize) values(?, ?, ?) ");
				stm.setString(1, dep.getName());
				stm.setBoolean(2, dep.getIsChangeable());
				stm.setBoolean(3, dep.getIsSynchronized());
	            return stm.executeUpdate();
			    }
	            
			}catch (SQLException e) {
				
				System.out.println(e.getMessage());
			}finally {
	            conn.close();
	        }
		}catch (Exception e1) {
			throw new Error("Error driver didnt load",e1);
		}
    	return 0;
    }
    
    public static int addRoleToDB(Role role, String salaryType, String empName, EmployeePreference employeePreference, int salesPresent, int hoursNum, int payPerHour) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);
			Statement stmt;
			stmt = conn.createStatement();
			int r;
			try {				
				PreparedStatement stm = conn.prepareStatement("insert INTO roles (roleName,empInThisRole,departmentName, isChangable, isSynchronize, cWFromHome) values(?, ?, ?, ?, ? ,?)");
				stm.setString(1, role.getRoleName());
				stm.setString(2, empName);
				stm.setString(3, role.getDepName());
				stm.setBoolean(4, role.getIsChangeable());
				stm.setBoolean(5, role.getIsSynchronized());
				stm.setBoolean(6, role.iscWFromHome());

	            return stm.executeUpdate();
	            
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}finally {
	            conn.close();
	        }
		}catch (Exception e1) {
			throw new Error("Error driver didnt load",e1);
		}
    	return 0;
    }
    
    
    
    public static int addEmployeeToDB(Role role, String salaryType, String empName, int salesPresent, int hoursNum,int payPerHour) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);
			Statement stmt;
			stmt = conn.createStatement();
			try {
				PreparedStatement stm = conn.prepareStatement("insert INTO Employees (empName,roleOfEmployee,salaryType) values(?, ?, ?)");
				stm.setString(1, empName);
				stm.setString(2, role.getRoleName());
				stm.setString(3, salaryType);
				
				return stm.executeUpdate();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}finally {
	            conn.close();
	        }
		}catch (Exception e1) {
			throw new Error("Error driver didnt load",e1);
		}
    	return 0;
    }
    
    public static int addEmpTypeToDB(String empName, String salaryType, int salesPresent, int hoursNum,int payPerHour) {
    	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);
			Statement stmt;
			stmt = conn.createStatement();
			try {
				PreparedStatement stm = null;
				if (salaryType.equalsIgnoreCase("base")) {
					stm = conn.prepareStatement("insert INTO employeesbase (empName) value(?)");
					stm.setString(1, empName);
				}
				if (salaryType.equalsIgnoreCase("baseSales")) {
					//System.out.println("we are trying to add "+ empName+ " to baseSales");
					stm = conn.prepareStatement("insert INTO employeesbasesales (empName, salesPresent) values(?, ?)");
					stm.setString(1, empName);
					stm.setInt(2, salesPresent);
				}
				if (salaryType.equalsIgnoreCase("perHours")) {
					stm = conn.prepareStatement("insert INTO employeesperhours (empName, hoursNum,payPerHour) values(?, ?, ?)");
					stm.setString(1, empName);
					stm.setInt(2, hoursNum);
					stm.setInt(3, payPerHour);
				}
	            return stm.executeUpdate();

			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}finally {
	            conn.close();
	        }
		}catch (Exception e1) {
			throw new Error("Error driver didnt load",e1);
		}
    	return 0;    }
    
    
    
    
    public static int addEmpPrefToDB(String empName, EmployeePreference empPref) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);
			Statement stmt;
			stmt = conn.createStatement();
			try {
				PreparedStatement stm = conn.prepareStatement("insert INTO employeepreferences (empName,employeePreference,movingHours) values(?, ?, ?)");
				stm.setString(1, empName);
				stm.setString(2, empPref.getEmployeePreference());
				stm.setInt(3, empPref.getMovingHours());
				return stm.executeUpdate();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}finally {
	            conn.close();
	        }
		}catch (Exception e1) {
			throw new Error("Error driver didnt load",e1);
		}
    	return 0;
    }
    
    public static int addDepManagerDecDB(String depName, ManagerDecision maDec) {


		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);
			Statement stmt;
			stmt = conn.createStatement();
			try {
				//checking if there is duplicate rows 
				ResultSet rs = stmt.executeQuery("SELECT * FROM depmanagerdecisions WHERE depName ='"+depName+"'");
			    if(rs.next())
			    {
			    	//System.out.println("duplicate dep ManagerDec...updating ");
			    	PreparedStatement update = conn.prepareStatement("UPDATE depmanagerdecisions SET managerDecision = ?, movingHours = ? WHERE depName= ?");
			    	update.setString(3, depName);
			    	update.setString(1, maDec.getManagerDecision());
			    	update.setInt(2, maDec.getMovingHours());
			    	update.executeUpdate();
			    }else {
				
					PreparedStatement stm = conn.prepareStatement("insert INTO depmanagerdecisions (depName,managerDecision,movingHours) values(?, ?, ?)");
					stm.setString(1, depName);
					stm.setString(2, maDec.getManagerDecision());
					stm.setInt(3, maDec.getMovingHours());
					return stm.executeUpdate();
			    }
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}finally {
	            conn.close();
	        }
		}catch (Exception e1) {
			throw new Error("Error driver didnt load",e1);
		}
    	return 0;
    } 
    
    
    public static int addRoleManagerDecDB(String roleempName, ManagerDecision maDec) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);
			Statement stmt;
			stmt = conn.createStatement();
			try {
				//checking if there is duplicate rows 
				ResultSet rs = stmt.executeQuery("SELECT * FROM empmanagerdecisions WHERE roleempName ='"+roleempName+"'");
			    if(rs.next())
			    {
			    	//System.out.println("duplicate emp ManagerDec...updating ");
			    	PreparedStatement update = conn.prepareStatement("UPDATE empmanagerdecisions SET managerDecision = ?, movingHours = ? WHERE roleempName= ?");
			    	update.setString(3, roleempName);
			    	update.setString(1, maDec.getManagerDecision());
			    	update.setInt(2, maDec.getMovingHours());
			    	update.executeUpdate();
			    	
			    }else {
				
			        // If the value does not exist, insert new data into the table
						PreparedStatement stm = conn.prepareStatement("insert INTO empmanagerdecisions (roleempName,managerDecision,movingHours) values(?, ?, ?)");
						stm.setString(1, roleempName);
						stm.setString(2, maDec.getManagerDecision());
						stm.setInt(3, maDec.getMovingHours());
						return stm.executeUpdate();
			    }
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}finally {
	            conn.close();
	        }
		}catch (Exception e1) {
			throw new Error("Error driver didnt load",e1);
		}
    	return 0;
    }
    
    
    public static ArrayList<Department> readDepFromDB(ViewInterface view) {
        try {
            // Connect to the database
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);

            // Retrieve all departments
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM departments");
            ArrayList<Department> allDepartments = new ArrayList<Department>();

            while (rs.next()) {
            	String DepName= rs.getString("depName");
            	boolean DepIsChangable= rs.getBoolean("isChangable");
            	boolean DepIsSynchronized= rs.getBoolean("isSynchronize");
                Department dep = new Department(DepName,DepIsChangable,DepIsSynchronized);
                allDepartments.add(dep);
            }
            return allDepartments;
        }catch (Exception e1) {
			throw new Error("Error driver didnt load XXXXX",e1);
        }
    }
    
    //function to read all the data from MYSQL in the beginning of the the program    
    public static ArrayList<Employee> readEmpFromDB(ViewInterface view, CompanyUI model) {
        try {
            // Connect to the database
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);

            // Retrieve all emp
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
			ArrayList<Employee> allEmployeesFromFile = new ArrayList<Employee>();
			EmployeePreference empPrefFromDB = null;
			Role roleFromDB = null;
			String empName= null;
			String roleOfEmployee=null;
			String salaryType=null;
			String depName= null;
			int salesPresent = 0;
			int hoursNum = 0;
			int payPerHour =0;
			
            while (rs.next()) {
            	empName= rs.getString("empName");
				roleOfEmployee= rs.getString("roleOfEmployee");
				salaryType= rs.getString("salaryType");
				
				//reading and initializing Role from the Data
				roleFromDB = readRoleFromDB(empName);   
				
				depName=roleFromDB.getDepName();
				 
				//reading and initializing employee preference from the Data	
				empPrefFromDB = readEmpPrefFromDB(empName);
            
				//we are checking in 3 steps what is the salary type to create new Employee
    		if (salaryType.equalsIgnoreCase("baseSales")) {

    				 	Statement stmt1 = con.createStatement();
    					String query = String.format("SELECT * FROM employeesbasesales WHERE empName = '%s';", empName);
    					ResultSet rs1= stmt1.executeQuery(query);
    					
    					while(rs1.next()) {
    						salesPresent= rs1.getInt("salesPresent");
    						Employee newEmp = null;
    		    			newEmp =new EmployeeBaseSales(roleFromDB, salaryType, roleFromDB.getEmpInThisRole(), empPrefFromDB,salesPresent);
    		    			allEmployeesFromFile.add(newEmp);
    					}
    					
    		}
    		else if (salaryType.equalsIgnoreCase("perHours")) {
    			Statement stmt1 = con.createStatement();
				String query = String.format("SELECT * FROM employeesperhours WHERE empName = '%s';", empName);
				ResultSet rs1= stmt1.executeQuery(query);
				
				while(rs1.next()) {
					hoursNum = rs1.getInt("hoursNum");
					payPerHour = rs1.getInt("payPerHour");
	                
	                Employee newEmp = null;
	    			newEmp =new EmployeePerHours(roleFromDB, salaryType, roleFromDB.getEmpInThisRole(), empPrefFromDB, hoursNum, payPerHour) ; 
	    			allEmployeesFromFile.add(newEmp);
				}
    			
    		}else if (salaryType.equalsIgnoreCase("base")) {
    			Employee newEmp = null;
    			
    			newEmp =new EmployeeBase(roleFromDB, salaryType, roleFromDB.getEmpInThisRole(), empPrefFromDB);
    			allEmployeesFromFile.add(newEmp);
    		}
    		
    		model.addRoleWithEmployeeFromDB1(roleFromDB, empPrefFromDB,
           		 salesPresent, hoursNum, salaryType,
					payPerHour);
	
    		view.addRoleOfDepToView(model.getIndexOfDep(roleFromDB.getDepName()), roleFromDB.getRoleName());
 			view.addToListviewRoleEmp(allEmployeesFromFile);
    		view.addToComboRole( roleFromDB.getRoleName());
            }
            return allEmployeesFromFile;
            
        }catch (Exception e1) {
			throw new Error("Error driver didnt load",e1);
        }
    }
    
    
    public static EmployeePreference readEmpPrefFromDB(String empName) {
        try {
            // Connect to the database
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);

		 	Statement stmt = con.createStatement();
			String query = String.format("SELECT * FROM EmployeePreferences WHERE empName = '%s';", empName);
			ResultSet rs= stmt.executeQuery(query);
			
			while(rs.next()) {
			String employeepreference= rs.getString("employeepreference");
			int movingHours= rs.getInt("movingHours");
			//create new employee preference from the data that we read.
			EmployeePreference empPref = new EmployeePreference(employeepreference, movingHours);
			return empPref;
			}
			
        }catch (Exception e1) {
			throw new Error("Error driver didnt load ",e1);
        }
        //emp didnt founded !
		return null;
    }
    
    public static Role readRoleFromDB(String empName) {
        
    	try {
            // Connect to the database
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db", "root", serverPass);

            Statement stmt = con.createStatement();
			
            String query = String.format("SELECT * FROM roles WHERE empInThisRole = '%s';", empName);
			ResultSet rs= stmt.executeQuery(query);
			
			while(rs.next()) {
			String roleName= rs.getString("roleName");
			String departmentName= rs.getString("departmentName");
			Boolean isChangable= rs.getBoolean("isChangable");
			Boolean isSynchronize= rs.getBoolean("isSynchronize");
			Boolean cWFromHome= rs.getBoolean("cWFromHome");
			//create new role from the data that we read.
			Role role = new Role(departmentName,roleName, empName,  isChangable, isSynchronize, cWFromHome);

			return role;
			}
        }catch (Exception e1) {
			throw new Error("Error driver didnt load XXXXX",e1);
        }
        //emp didnt founded !
		return null;
    }
 

}
