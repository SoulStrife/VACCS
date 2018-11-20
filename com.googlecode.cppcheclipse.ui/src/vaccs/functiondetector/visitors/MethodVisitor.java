package vaccs.functiondetector.visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTName;

import vaccs.functiondetector.data.FunctionRegions;

public class MethodVisitor extends ASTVisitor {
	{shouldVisitNames = true;}
	{shouldVisitDeclarations = true;}
	private List<FunctionRegions> detectedFunctionNames = new ArrayList<FunctionRegions>();
	
	public List<FunctionRegions> getDetectedFunctionNames(){
		return detectedFunctionNames;
		
	}
	
	
	@Override
	public int visit(IASTName name) {
		int offset = 0;
		int length = 0;
		IASTFileLocation loc = name.getFileLocation();
		if(null!=loc)
		{
			offset= loc.getNodeOffset();
			length=loc.getNodeLength();
			detectedFunctionNames.add(new FunctionRegions(name.toString(), offset, length));
		}
		return super.visit(name);
	}
	@Override
	public int leave(IASTName name) {
		// TODO Auto-generated method stub
		return super.leave(name);
	}


}
