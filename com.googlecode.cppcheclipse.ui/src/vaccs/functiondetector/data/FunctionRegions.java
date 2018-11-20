package vaccs.functiondetector.data;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

/**
 * Holder class used to store information about functions found
 * in AST including offsets, length, and others.
 * @immutable Objects of this class cannot be modified.
 * @author Hisham
 *
 */
public class FunctionRegions {
	
	private String functionName;
	private int offSet;
	private int length;
	private IRegion region;
	
	/**
	 * @param functionName
	 * @param offSet
	 * @param length
	 */
	public FunctionRegions(String functionName, int offSet, int length) {
		
		this.functionName = functionName;
		this.offSet = offSet;
		this.length = length;
		region = new Region(offSet, length);
	}


	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	
	/**
	 * @return the offSet
	 */
	public int getOffSet() {
		return offSet;
	}


	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	
	public IRegion getRegion(){
		return region; 
	}
	
	
	
	
	

}
