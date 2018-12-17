package vaccs.functiondetector.ui;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.parser.util.ASTPrinter;
import org.eclipse.cdt.ui.CDTUITools;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
//import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.handlers.HandlerUtil;

import vaccs.functiondetector.visitors.ASTHandler;
import vaccs.functiondetector.visitors.MethodVisitor;
import vaccs.utilities.InternalFileRetriever;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * Based on the Hello World Plugin Example.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ButtonHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ButtonHandler() {
	}
	/**
	 * Compares list of detected functions to list of functions to watch out for.
	 * @param detectedFunc List of detected functions from AST
	 */
//	public void printInsecure(List<String> detectedFunc){
//		List<String> insecureFunctions = RiskyFunctionRetriever.getRiskyFunctions(new ArrayList<String>());
//				
//		for(String func:detectedFunc){
//			if(insecureFunctions.contains(func))
//				System.out.println(func + " is insecure");
//		}
//		
//	}
	/**
	 * Function to find all invoked methods from AST and then print
	 * only the functions that we're looking for.
	 */
	public void detectInsecureFunctions(){
		
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		ITranslationUnit tu = (ITranslationUnit) CDTUITools.getEditorInputCElement(editor.getEditorInput());
		
		MethodVisitor visitor = new MethodVisitor();
		//ASTPrinter parser = new ASTPrinter();
	
		PrintStream out = null;
		try {
			IASTNode[] children = tu.getAST(null, ITranslationUnit.AST_SKIP_ALL_HEADERS).getChildren();
			File ASTFile = InternalFileRetriever.retrieveFile("/files/AST.txt");
		//	f0 = new FileWriter(ASTFile);
			// writer= new PrintWriter(f0);
			 out = new PrintStream(ASTFile);
			for(IASTNode node:children){
				
				
				ASTPrinter.print(node,out);
		//		writer.println("Offsets: " +node.getSyntax().getOffset() +" to "+ node.getSyntax().getEndOffset()+" Element: " +node.toString());
			//	writer.println("Node Syntax: "+node.getSyntax().toString());
			}
			
			
			tu.getAST(null, ITranslationUnit.AST_SKIP_ALL_HEADERS).accept(visitor);
			visitor.getDetectedFunctionNames();
			//printInsecure(visitor.getDetectedFunctionNames());
			
			//for(int i=0; i<children.length; i++){
			//	parser.print(children[368]);
			//}
		} catch (CoreException | IOException | URISyntaxException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!=null)
				out.close();
			//if(writer!=null)
			//	writer.close();
		}

	}
	
	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ASTHandler ah = new ASTHandler();
		ah.detectInsecureFunctions();
		/*MessageDialog.openInformation(
				window.getShell(),
				"VACCS",
				"Functions Detected");*/
		return null;
	}
}
