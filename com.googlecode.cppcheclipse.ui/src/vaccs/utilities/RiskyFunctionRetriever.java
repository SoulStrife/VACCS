package vaccs.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class RiskyFunctionRetriever {
	
	public static final String FILENAME = "/files/functionNames.txt";  
	
	/**
	 * Goes to the location specified by FILENAME and
	 * retrieves list of functions that must be watched out for.
	 * @return List<String> containing list of risky functions
	 */
	public static List<String> getRiskyFunctions(){
		File f=null;
		try {
			f = InternalFileRetriever.retrieveFile(FILENAME);
		} catch (IOException | URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(null==f)
			return null;

		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			return null;
		}

		List<String> insecureFunctions = new ArrayList<String>();
		while(scan.hasNextLine()){
			insecureFunctions.add(scan.nextLine());
		}
		scan.close();
		return insecureFunctions;
	}
	
	public static boolean isRisky(String functionName){
		List<String> keywords = getRiskyFunctions();
		if(null==keywords){
			return false;
		}
		if(keywords.contains(functionName))
			return true;
		else
			return false;
	}
}
