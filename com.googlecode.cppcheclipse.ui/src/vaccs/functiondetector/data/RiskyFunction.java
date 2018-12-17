package vaccs.functiondetector.data;

public class RiskyFunction {
	private String functionName;
	private String groupName;
	
	public RiskyFunction(String groupName, String functionName) {
		this.setFunctionName(functionName);
		this.setGroupName(groupName);
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
