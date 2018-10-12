package com.googlecode.cppcheclipse.ui.marker;

import java.io.File;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.googlecode.cppcheclipse.ui.Messages;
import com.vaccs.model.ViewContext;
import com.vaccs.ui.VACCSView;

public class OpenVACCSResolution implements IMarkerResolution {

	public String getLabel() {
		return Messages.OpenVACCSResolution_Label;
	}

	public void run(IMarker marker) {
		System.out.println("Error is opened in VACCS");
			String problemId = marker.getAttribute(ProblemReporter.ATTRIBUTE_ID, ""); //$NON-NLS-1$
			int line = marker.getAttribute(ProblemReporter.ATTRIBUTE_ORIGINAL_LINE_NUMBER, 0);
			File file = new File(marker.getAttribute(ProblemReporter.ATTRIBUTE_FILE, "")); //$NON-NLS-1$
			System.out.printf("problemId %s line %d fileName %s\n", problemId, line, file.getName());
			ViewContext.getInstance().setProblemId(problemId);
			ViewContext.getInstance().setLineNumber(line);
			ViewContext.getInstance().setFile(file);
			try {
				VACCSView view = (VACCSView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("com.vaccs.ui.view");
				view.dataChanged();
			} catch (PartInitException e) {
				System.out.println("Unable to open view");
				e.printStackTrace();
			}
			
		
		
	}

}
