package com.vaccs.ui;


import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import vaccs.functiondetector.data.FunctionGroup;
import vaccs.functiondetector.visitors.ASTHandler;
import vaccs.utilities.RiskyFunctionHandler;


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

public class FunctionView extends ViewPart {
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.vaccs.function.ui.view";
	
	
	



	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		
		List<FunctionGroup> groups = RiskyFunctionHandler.getInstance().getGroups();
		for(int i = 0; i < groups.size(); i++) {
			Button group = new Button(parent, SWT.CHECK);
			group.setText(groups.get(i).getGroupName());
			group.setSelection(groups.get(i).isActive());
			group.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button) e.widget;
					List<FunctionGroup> groups = RiskyFunctionHandler.getInstance().getGroups();
					for(int i = 0; i < groups.size(); i++) {
						if(groups.get(i).getGroupName().equals(button.getText())) {
							groups.get(i).setActive(button.getSelection());
							ASTHandler ah = new ASTHandler();
							ah.detectInsecureFunctions();
						}
					}
				}
			});
			group.setLayoutData(data);
		}
	}


	@Override
	public void setFocus() {
		
	}
}
