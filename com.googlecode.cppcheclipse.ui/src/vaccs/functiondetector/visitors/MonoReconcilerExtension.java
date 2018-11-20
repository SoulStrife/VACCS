package vaccs.functiondetector.visitors;

import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.MonoReconciler;

public class MonoReconcilerExtension extends MonoReconciler {

	public MonoReconcilerExtension(IReconcilingStrategy strategy, boolean isIncremental) {
		super(strategy, isIncremental);
		
	}

}
