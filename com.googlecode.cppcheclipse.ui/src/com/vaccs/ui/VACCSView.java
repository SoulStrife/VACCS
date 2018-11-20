package com.vaccs.ui;


import java.lang.reflect.Field;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.googlecode.cppcheclipse.core.CppcheclipsePlugin;
import com.googlecode.cppcheclipse.ui.Utils;
import com.vaccs.model.ViewContext;

import vaccs.utilities.InternalFileRetriever;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class VACCSView extends ViewPart {
	private static final int BROWSER = 0;
	private static final int VIDEO = 1;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.vaccs.ui.view";

	private Label errorLabel;
	
	private CTabFolder tabFolder;
	private CTabItem cTabItem[] = new CTabItem[2];
	
	private Browser browser;
	private Label videoLabel;
	
	


	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		@Override
		public Image getImage(Object obj) {
			return null;
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		errorLabel = new Label(parent,0);
		errorLabel.setLayoutData(data);
		
		tabFolder = new CTabFolder(parent, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		tabFolder.setLayoutData(data);
		tabFolder.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		          if(tabFolder.getSelection().equals(cTabItem[VIDEO])) {
		        	  String url = null;
					  try {
						Field field = VideoURL.class.getField(ViewContext.getInstance().getProblemId());
						url = (String) field.get(null);
					  } catch (Exception ex) {
						url = VideoURL.defaultVideo;
					  }
					  if(url != null) {
						  videoLabel.setText("Loading Video for " + ViewContext.getInstance().getProblemId());
						  try {
								Utils.openUrl(url);
							  } catch (Exception ex) {
								CppcheclipsePlugin.logError("Could not open video page", ex);
							  }  
					  } else {
						  videoLabel.setText("Video for " + ViewContext.getInstance().getProblemId() + " could not be found");
					  }
		          }
		        }
		      });
		
		setupTabFolder();
	}

	private void setupTabFolder() {
		
		cTabItem[BROWSER] = new CTabItem(tabFolder, SWT.NONE);
		cTabItem[BROWSER].setText("What is this?");
		try {
			browser = new Browser(tabFolder, SWT.NONE);
			cTabItem[BROWSER].setControl(browser);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			Text text = new Text(tabFolder, SWT.BORDER);
	        text.setText("Could not load browser");
	        cTabItem[BROWSER].setControl(text);
			return;
		}
        
        cTabItem[VIDEO] = new CTabItem(tabFolder, SWT.NONE);
        cTabItem[VIDEO].setText("Video");
        videoLabel = new Label(tabFolder, SWT.NONE);
        cTabItem[BROWSER].setControl(videoLabel);
	}
	
	
	public void dataChanged() {
		System.out.printf("problemId %s line %d fileName %s\n", ViewContext.getInstance().getProblemId(), ViewContext.getInstance().getLineNumber(), ViewContext.getInstance().getFile().getName());
		errorLabel.setText(ViewContext.getInstance().getProblemId());
		browser.setText(InternalFileRetriever.readFileIntoString("./html/explanation/" + ViewContext.getInstance().getProblemId() + ".html"));
		tabFolder.setSelection(cTabItem[BROWSER]);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
