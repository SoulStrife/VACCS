package com.googlecode.cppcheclipse.ui.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;


public class ResolutionGenerator implements IMarkerResolutionGenerator {
	public ResolutionGenerator() {
		
	}
	
	public IMarkerResolution[] getResolutions(IMarker marker) {
		return new IMarkerResolution[] { 
				new OpenVACCSResolution(),
				new SuppressProblemInLineResolution(),
				new SuppressProblemResolution(),
				new SuppressFileResolution(),
				new ReportBugResolution(),
				new CheckDescriptionResolution()
		};
	}

}
