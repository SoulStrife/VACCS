package com.vaccs.model;

import java.io.File;

public class ViewContext {
	private static ViewContext instance = null;
	
	public static final int FUNCTION = 0;
	public static final int STATIC = 1;
	public static final int DYNAMIC = 1;
	
	private int category;
	private String problemId;
	private int lineNumber;
	private File file;
	
	private ViewContext() {
		
	}
	
	public static ViewContext getInstance() {
		if(instance == null)
			instance = new ViewContext();
		
		return instance;
	}
	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
}
