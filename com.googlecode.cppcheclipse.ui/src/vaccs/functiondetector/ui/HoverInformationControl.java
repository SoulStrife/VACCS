package vaccs.functiondetector.ui;

import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.vaccs.model.ViewContext;
import com.vaccs.ui.VACCSView;

//import vaccs.functiondetector.views.FunctionInformationView;

public class HoverInformationControl extends AbstractInformationControl implements IInformationControlExtension2 {

	 /**
     * A wrapper used to deliver content to the hover control, either 
     * as marked-up text or as a URL.
     */
    public interface IHTMLHoverInfo {
    	
    	
    	/**
         * @return true if the String returned by getHTMLString() represents a URL; 
         * false if the String contains marked-up text.
         */

        public boolean isURL();

       /**
        * @return The input string to be displayed in the Browser widget
        * (either as marked-up text, or as a URL.)
        */
        public String getHTMLString();
        
    }
	
	private Browser hoverBrowser;
//	private boolean isURL;
	
	public HoverInformationControl(Shell parentShell) {
		
		super(parentShell, (String)null);
		
		create();
	}
	
	@Override
	public boolean hasContents() {
		return true;
		/*if(hoverBrowser!=null){
			
			boolean hasContents = false;
			try{
				hasContents = hoverBrowser.getText().length()>0;
			}catch(SWTException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			return hasContents;
		}
		else
			return false;*/
	}

	@Override
	public void setInput(Object input) {
//		 isURL = false;
	        final String inputString;
	        
	        /*if (input instanceof IHTMLHoverInfo) {
	            // Get the input string, then see whether it's a URL
	            IHTMLHoverInfo inputInfo = (IHTMLHoverInfo) input;
	            inputString = inputInfo.getHTMLString();
	            isURL= inputInfo.isURL();
	        }
	        else*/ if (input instanceof String) {
	            // Treat the String as marked-up text to be displayed.
	            inputString = (String) input;
	        }
	        else {
	            // For any other kind of object, just use its string 
	            // representation as text to be displayed.
	            inputString = input.toString();
	        }
	        setInformation(inputString);
	}

	@Override
	protected void createContent(Composite parent) {
		try{
			hoverBrowser = new Browser(getShell(), SWT.NONE);
			
		}catch(SWTError e){
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
            messageBox.setMessage("Browser cannot be initialized."); //$NON-NLS-1$
            messageBox.setText("Error");                             //$NON-NLS-1$
            messageBox.open();
        }
		
	}
	
	public void setInformation(String content){
		hoverBrowser.setBounds(getShell().getClientArea());
		hoverBrowser.setUrl(content);
		
		hoverBrowser.addLocationListener(new LocationListener(){
			String currentURL=null;
			@Override
			public void changing(LocationEvent event) {
				currentURL =hoverBrowser.getUrl();
				
				if(!currentURL.equalsIgnoreCase("about:blank")){
					ViewContext.getInstance().setCategory(ViewContext.FUNCTION);
					String[] s= event.location.split("/");
					String functionName = s[s.length-1];
					if(functionName.endsWith(".html")){
						functionName=functionName.substring(0, functionName.length()-5);
					}
					ViewContext.getInstance().setProblemId(functionName);
					ViewContext.getInstance().setLineNumber(-1);
					ViewContext.getInstance().setFile(null);
					try {
						VACCSView view = (VACCSView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("com.vaccs.ui.view");
						view.dataChanged();
					} catch (PartInitException e) {
						System.out.println("Unable to open view");
						e.printStackTrace();
					}
				}
				
				
			}

			@Override
			public void changed(LocationEvent event) {
			
			}
			
		});
	}
	
	public Point computeSizeHint(){
		
		return getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
	}
	
	@Override
	public IInformationControlCreator getInformationPresenterControlCreator(){
		return new IInformationControlCreator(){
			public IInformationControl createInformationControl(Shell parent){
				return new HoverInformationControl(parent);
			}
		};
	}
	
	

	

}
