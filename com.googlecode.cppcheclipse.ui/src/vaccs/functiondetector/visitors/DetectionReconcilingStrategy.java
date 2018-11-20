package vaccs.functiondetector.visitors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;

public class DetectionReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension  {
	
	IDocument document = null;
	public void setDocument(IDocument document) {
		this.document=document;

	}

	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		reconcile();

	}
	public void reconcile(){
		ASTHandler ah = new ASTHandler();
		ah.detectInsecureFunctions();
	}

	public void reconcile(IRegion partition) {
		reconcile();
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialReconcile() {
		reconcile();
		
	}

}
