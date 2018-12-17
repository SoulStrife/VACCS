package vaccs.functiondetector.data;

public class FunctionGroup {
	private String groupName;
	private boolean active;
	
	public FunctionGroup(String groupName, boolean isActive) {
		this.groupName = groupName;
		this.active = isActive;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
