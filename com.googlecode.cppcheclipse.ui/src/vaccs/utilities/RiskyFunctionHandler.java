package vaccs.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vaccs.functiondetector.data.FunctionGroup;
import vaccs.functiondetector.data.RiskyFunction;

public class RiskyFunctionHandler {
	
	public static final String FILENAMEFUNCTIONS = "/files/functionNames.txt";  
	public static final String FILENAMEGROUPS = "/files/functionGroups.txt";  
	
	private static RiskyFunctionHandler instance = null;
	private List<RiskyFunction> riskyFunctions;
	private List<FunctionGroup> groups;
	
	private RiskyFunctionHandler() {
		readInRiskyFunctions();
		readInGroups();
	}
	
	public static RiskyFunctionHandler getInstance() {
		if(instance == null) {
			instance = new RiskyFunctionHandler();
		} 
		return instance;
	}
	
	private void readInRiskyFunctions() {
		try {
			File f = InternalFileRetriever.retrieveFile(FILENAMEFUNCTIONS);
			Scanner scan = new Scanner(f);
			
			riskyFunctions = new ArrayList<RiskyFunction>();
			
			while(scan.hasNextLine()){
				
				RiskyFunction rf = new RiskyFunction(scan.next(), scan.next());
				riskyFunctions.add(rf);
			}
			
			scan.close();
		} catch (Exception e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		} 
	}
	
	private void readInGroups() {
		try {
			File f = InternalFileRetriever.retrieveFile(FILENAMEGROUPS);
			Scanner scan = new Scanner(f);
			
			groups = new ArrayList<FunctionGroup>();
			
			while(scan.hasNextLine()){
				groups.add(new FunctionGroup(scan.nextLine(), true));
			}
			scan.close();
		} catch (Exception e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		} 
	}
		
		
	
	/**
	 * Goes to the location specified by FILENAMEFUNCTIONS and
	 * retrieves list of functions that must be watched out for.
	 * @return List<String> containing list of risky functions
	 */
	public List<RiskyFunction> getRiskyFunctions(){
		List<RiskyFunction> insecureFunctions = new ArrayList<RiskyFunction>();
		for(int i = 0; i < riskyFunctions.size(); i++) {
			for(int x = 0; x < groups.size(); x++) {
				if(riskyFunctions.get(i).getGroupName().equals(groups.get(x).getGroupName())) {
					if(groups.get(x).isActive()) {
						insecureFunctions.add(riskyFunctions.get(i));
					} else {
						break;
					}
				}
			}
		}
		return insecureFunctions;
	}
	
	/**
	 * Goes to the location specified by FILENAMEGROUPS and
	 * retrieves list of functions that must be watched out for.
	 * @return List<String> containing list of risky functions
	 */
	public List<FunctionGroup> getGroups(){
		return groups;
	}
	
	public boolean isRisky(String functionName){
		for(int i = 0; i < riskyFunctions.size(); i++) {
			if(riskyFunctions.get(i).getFunctionName().equals(functionName)) {
				for(int x = 0; x < groups.size(); x++) {
					if(riskyFunctions.get(i).getGroupName().equals(groups.get(x).getGroupName())) {
						return groups.get(x).isActive();
					}
				}
			}
		}
		return false;
	}
}
