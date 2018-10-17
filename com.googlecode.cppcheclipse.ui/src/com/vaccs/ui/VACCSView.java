package com.vaccs.ui;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.cdt.utils.Platform;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.googlecode.cppcheclipse.core.CppcheclipsePlugin;
import com.googlecode.cppcheclipse.ui.Utils;
import com.vaccs.model.ViewContext;


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

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.vaccs.ui.view";

	private Label errorLabel;
	private Button gotoURL;
	
	private CTabFolder tabFolder;
	private CTabItem cTabItem[] = new CTabItem[4];
	
	private Browser explanationBrowser;
	private Browser exampleBrowser;
	private Browser exploitBrowser;
	


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
		          if(tabFolder.getSelection().equals(cTabItem[3])) {
		        	  String url = null;
					  try {
						Field field = VideoURL.class.getField(ViewContext.getInstance().getProblemId());
						url = (String) field.get(null);
					  } catch (Exception ex) {
						url = VideoURL.defaultVideo;
					  }
										
					  try {
						Utils.openUrl(url);
					  } catch (Exception ex) {
						CppcheclipsePlugin.logError("Could not open video page", ex);
					  }
		          }
		        }
		      });
		
		setupTabFolder();
//		initializeInformation();
		

		// Create the help context id for the viewer's control
//		getSite().setSelectionProvider(viewer);
//		makeActions();
//		hookContextMenu();
//		hookDoubleClickAction();
//		contributeToActionBars();
	}
	
	private String readFileIntoString(String filePath) {
		
		
		try {
			URL url = FileLocator.toFileURL(FileLocator.find(Platform.getBundle("com.googlecode.cppcheclipse.ui"), new Path(filePath), null));
			return new String(Files.readAllBytes(Paths.get(new URI(url.toString().replaceAll(" ", "%20")))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} 
	}

	private void setupTabFolder() {
		
		cTabItem[0] = new CTabItem(tabFolder, SWT.NONE);
		cTabItem[0].setText("What is this?");
		try {
			explanationBrowser = new Browser(tabFolder, SWT.NONE);
			cTabItem[0].setControl(explanationBrowser);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			Text text = new Text(tabFolder, SWT.BORDER);
	        text.setText("Could not load browser");
	        cTabItem[0].setControl(text);
			return;
		}
        
        cTabItem[1] = new CTabItem(tabFolder, SWT.NONE);
        cTabItem[1].setText("Examples");
        try {
        	exampleBrowser = new Browser(tabFolder, SWT.NONE);
        	cTabItem[1].setControl(exampleBrowser);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			Text text = new Text(tabFolder, SWT.BORDER);
	        text.setText("Could not load browser");
	        cTabItem[1].setControl(text);
			return;
		}
        
        cTabItem[2] = new CTabItem(tabFolder, SWT.NONE);
        cTabItem[2].setText("Exploits");
        try {
        	exploitBrowser = new Browser(tabFolder, SWT.NONE);
        	cTabItem[2].setControl(exploitBrowser);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			Text text = new Text(tabFolder, SWT.BORDER);
	        text.setText("Could not load browser");
	        cTabItem[2].setControl(text);
			return;
		}
        
        cTabItem[3] = new CTabItem(tabFolder, SWT.NONE);
        cTabItem[3].setText("Video");
	}
	
	
	public void dataChanged() {
		System.out.printf("problemId %s line %d fileName %s\n", ViewContext.getInstance().getProblemId(), ViewContext.getInstance().getLineNumber(), ViewContext.getInstance().getFile().getName());
		errorLabel.setText(ViewContext.getInstance().getProblemId());
		explanationBrowser.setText(readFileIntoString("./html/explanation/" + ViewContext.getInstance().getProblemId() + ".html"));
		exampleBrowser.setText(readFileIntoString("./html/example/" + ViewContext.getInstance().getProblemId() + ".html"));
		exploitBrowser.setText(readFileIntoString("./html/exploits/" + ViewContext.getInstance().getProblemId() + ".html"));
		tabFolder.setSelection(cTabItem[0]);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
