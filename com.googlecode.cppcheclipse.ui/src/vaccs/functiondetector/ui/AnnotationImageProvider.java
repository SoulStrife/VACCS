package vaccs.functiondetector.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.texteditor.IAnnotationImageProvider;

import com.googlecode.cppcheclipse.ui.Activator;

public class AnnotationImageProvider implements IAnnotationImageProvider {

	public AnnotationImageProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Image getManagedImage(Annotation annotation) {
		
		return getImageDescriptor("").createImage();
	}

	@Override
	public String getImageDescriptorId(Annotation annotation) {
		return "skull";
	}

	@Override
	public ImageDescriptor getImageDescriptor(String imageDescritporId) {
		return Activator.getImageDescriptor("/icons/skull.png");
				
	}

}
