package vaccs.functiondetector.visitors;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.ui.CDTUITools;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import vaccs.functiondetector.data.FunctionRegions;
import vaccs.functiondetector.ui.RiskyMarkers;
import vaccs.utilities.RiskyFunctionRetriever;

public class ASTHandler {
	private  static List<IMarker> markers;
	/**
	 * Function to find all invoked methods from AST and then print
	 * only the functions that we're looking for.
	 */
	public  void detectInsecureFunctions(){
		IEditorPart editor;
		try{
			 editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		}catch(NullPointerException e){
			return;
		}
		ITranslationUnit tu = (ITranslationUnit) CDTUITools.getEditorInputCElement(editor.getEditorInput());
		
		MethodVisitor visitor = new MethodVisitor();
		//ASTPrinter parser = new ASTPrinter();
		resetMarkers();
		PrintStream out = null;
		try {
//			IASTNode[] children = tu.getAST(null, ITranslationUnit.AST_SKIP_ALL_HEADERS).getChildren();
			//File ASTFile = InternalFileRetriever.retrieveFile("/files/AST.txt");
		//	f0 = new FileWriter(ASTFile);
			// writer= new PrintWriter(f0);
			 //out = new PrintStream(ASTFile);
			//for(IASTNode node:children){
				
				
	//			ASTPrinter.print(node,out);
		//		writer.println("Offsets: " +node.getSyntax().getOffset() +" to "+ node.getSyntax().getEndOffset()+" Element: " +node.toString());
			//	writer.println("Node Syntax: "+node.getSyntax().toString());
		//	}
			
			
			IASTTranslationUnit ast= tu.getAST(null, ITranslationUnit.AST_SKIP_ALL_HEADERS);
			if(null==ast)
				return;
			ast.accept(visitor);
			List<FunctionRegions> fRegions = visitor.getDetectedFunctionNames();
			List<String> insecureFunctions = RiskyFunctionRetriever.getRiskyFunctions();
			IDocument doc = getDocument();
			if(null==doc)
				return;
			if(null==markers)
				markers = new ArrayList<IMarker>();
			for(FunctionRegions func:fRegions){
				if(insecureFunctions.contains(func.getFunctionName())){
					markers.add(RiskyMarkers.createMarker(doc, func.getOffSet(), func.getLength()));
					
				}
				
			}
		} catch (CoreException e) {// | IOException | URISyntaxException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!=null)
				out.close();
			//if(writer!=null)
			//	writer.close();
		}

	}
	public  void resetMarkers(){
		if(null==markers){
			markers=  new ArrayList<IMarker>();
		}
		else{
			for(IMarker m:markers){
				try {
					if(m!=null)
						m.delete();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			markers=new ArrayList<IMarker>();
		}
	}
	public  IDocument getDocument(){
		
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		if (editor instanceof ITextEditor)
		 {
		   ITextEditor textEditor = (ITextEditor)editor;

		   IDocumentProvider provider = textEditor.getDocumentProvider();

		   IEditorInput input = editor.getEditorInput();

		   IDocument document = provider.getDocument(input);
		   
		   return document;
		 }
		else{
			return null;
		}
	}
	/**
	 * Compares list of detected functions to list of functions to watch out for.
	 * @param detectedFunc List of detected functions from AST
	 */
	public  void printInsecure(List<String> detectedFunc){

		List<String> insecureFunctions = RiskyFunctionRetriever.getRiskyFunctions();
				
		for(String func:detectedFunc){
			if(insecureFunctions.contains(func))
				System.out.println(func + " is insecure");
		}
		
	}
}
