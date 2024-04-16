package Model;

import java.io.Serializable;

public class EmployeePreference implements Serializable{
	protected String employeePreference;
	protected int movingHours;

	public EmployeePreference(String employeePreference, int movingHours) {
		this.employeePreference = employeePreference;
		if (employeePreference.equalsIgnoreCase("early")
				|| employeePreference.equalsIgnoreCase("later")) {
		this.movingHours = movingHours;
		}
		else {
			this.movingHours = 0;
		}
	}

	public String getEmployeePreference() {
		return employeePreference;
	}

	public void setManagerDecision(String employeePreference) {
		this.employeePreference = employeePreference;
	}

	public int getMovingHours() {
		return movingHours;
	}

	public void setMovingHours(int movingHours) {
		this.movingHours = movingHours;
	}

	@Override
	public String toString() {
		if (employeePreference.equalsIgnoreCase("early")
				|| employeePreference.equalsIgnoreCase("later")) {
		return "\n Employee's preference: " + employeePreference + "\n How many hours? " + movingHours;
		}
		return "\n Employee's preference: " + employeePreference;
	}

}
