package Model;

import java.io.Serializable;

public class Role implements Changeable,Synchronized, Serializable{
	protected String departmentName;
	protected String roleName;
	protected String EmpInThisRole;
	protected boolean isChangable;
	protected boolean isSynchronize;
	protected boolean cWFromHome;
	
	public Role(String departmentName, String roleName, String empInThisRole, boolean isChangable,
			boolean isSynchronize, boolean cWFromHome) {
		this.departmentName = departmentName;
		this.roleName = roleName;
		this.EmpInThisRole = empInThisRole;
		this.isChangable = isChangable;
		this.isSynchronize = isSynchronize;
		this.cWFromHome = cWFromHome;
	}

	public boolean iscWFromHome() {
		return cWFromHome;
	}

	public String getDepName() {
		return departmentName;
	}

	public String getRoleName() {
		return roleName;
	}

	@Override
	public boolean getIsSynchronized() {
		return isSynchronize;
	}

	@Override
	public boolean getIsChangeable() {
		return isChangable;
	}

	public void setChangable(boolean isChangable) {
		this.isChangable = isChangable;
	}

	public void setSynchronize(boolean isSynchronize) {
		this.isSynchronize = isSynchronize;
	}

	public String getEmpInThisRole() {
		return EmpInThisRole;
	}

	@Override
	public String toString() {
		return "Role "+ roleName + ":\n Department: " + departmentName +"\n Is this department synchronize? "+ isSynchronize + "\n Employee name: " + EmpInThisRole
				+ "\n Is this role work method can be change? " + isChangable+ " ";
	}
	
	


}
