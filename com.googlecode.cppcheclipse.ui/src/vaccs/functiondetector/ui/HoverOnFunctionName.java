package vaccs.functiondetector.ui;

import java.util.List;

import org.eclipse.cdt.ui.text.c.hover.ICEditorTextHover;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

import vaccs.utilities.InternalFileRetriever;
import vaccs.utilities.RiskyFunctionRetriever;

public class HoverOnFunctionName implements ITextHover, ICEditorTextHover, ITextHoverExtension, ITextHoverExtension2
											{

//	private IEditorPart editor;
//	private List<String> keywords;
	
	
	
	@Override
	public void setEditor(IEditorPart editor) {
//		this.editor = editor;

	}
	
	

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		return String.valueOf(getHoverInfo2(textViewer, hoverRegion));
	}
	@Override
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		String text = null;
		try {
			
			text = textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null==text){
			return null;
		}
		else if(RiskyFunctionRetriever.isRisky(text)){
			String URL =null;
			URL = InternalFileRetriever.retrieveHTMLSummaryHelpURL(text);
			return URL;
		}else{
			return null;
		}
	}
	
	@Override
	public IInformationControlCreator getHoverControlCreator() {
		return new IInformationControlCreator() {
	        public IInformationControl createInformationControl(Shell parent) {
	            return new HoverInformationControl(parent);
	        }
	    };
	}

	
	
	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return TokenFinder.getToken(textViewer.getDocument(), offset);
	}
	
	private static class TokenFinder{
		static List<String> keywords;
		static int currentKeyword = 0;
		private static String getNextToken(){
			if(null==keywords){
				keywords = RiskyFunctionRetriever.getRiskyFunctions();
			}
			String token = null;
			try{
				 token = keywords.get(currentKeyword);
			}catch(IndexOutOfBoundsException e){
				return null;
			}
			currentKeyword++;
			return token;
			
			
			
		}
		public static IRegion getToken(IDocument document, int offset){
			  
			
			// extract relevant characters  
			IRegion lineRegion;  
			String candidate;  
			try {  
				lineRegion = document.getLineInformationOfOffset(offset);  
				candidate = document.get(lineRegion.getOffset(), lineRegion.getLength());  
			} catch (BadLocationException ex) {  
				return null;  
			}  

			// look for keyword  
			String token = getNextToken();
			if(null==token)
				return null;
			IRegion targetRegion = null;
			int index = candidate.indexOf(token);  
			if(index!=-1){
				targetRegion = new Region(lineRegion.getOffset() + index, token.length());
			}
			return targetRegion;
		}
		
	}

	

	
	

}
