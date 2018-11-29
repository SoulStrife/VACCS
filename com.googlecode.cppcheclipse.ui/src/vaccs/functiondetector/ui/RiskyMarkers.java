package vaccs.functiondetector.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

public class RiskyMarkers  {
	
	public static IMarker createMarker(IDocument doc, int offset,int length){
		
		IMarker m=null;
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		IFileEditorInput input = (IFileEditorInput)editor.getEditorInput();
		IFile file = input.getFile();

		try{
			String text =doc.get(offset, length);
			m=file.createMarker("VACCS.FunctionDetector.RiskyMarker");
			m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			m.setAttribute(IMarker.LOCATION, doc.getLineOfOffset(offset)+1);
			m.setAttribute(IMarker.MESSAGE, text+" is a Risky Function");
			m.setAttribute(IMarker.LINE_NUMBER, doc.getLineOfOffset(offset)+1);
			m.setAttribute(IMarker.CHAR_START, offset);
			m.setAttribute(IMarker.CHAR_END, offset+length);
			
		}catch(CoreException | BadLocationException e){
			e.printStackTrace();
		}
		return m;
	}
	
}
