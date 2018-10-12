package com.vaccs.ui;

import org.eclipse.osgi.util.NLS;

public class VideoURL extends NLS {
	private static final String BUNDLE_NAME = "com.vaccs.ui.videoErrorURLs"; //$NON-NLS-1$
	
	public static String arrayIndexOutOfBounds;
	public static String defaultVideo;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, VideoURL.class);
	}

	private VideoURL() {
	}
}
