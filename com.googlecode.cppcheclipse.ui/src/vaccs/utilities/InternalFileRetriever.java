package vaccs.utilities;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.cdt.utils.Platform;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

/**
 * This class is needed because normal file access does not work
 * when working with a plugin. It's easier if we standardize the way 
 * we access our files.
 * Internal plugin files are stored as
 * Platform:/plugin/plugin-name/blah/blah/blah.txt
 * @author Hisham
 *
 */
public class InternalFileRetriever {
	
	/**
	 * This function will take as input the relative filename as you normally would use it
	 * i.e. "/files/file.txt/"
	 * It will return the file or throw an IOException
	 * @param filename in normal format, i.e "/file/file.txt/"
	 * @return your file as a java.io.File or null if file is not found
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static File retrieveFile(String filename) throws IOException, URISyntaxException{
		Bundle bundle = Platform.getBundle("VACCS");
		URL fileURL = bundle.getEntry(filename);
		if(null==fileURL) {
			System.out.println("fileURL null");
			return null;
		}
		
		File f =null;
		URL resolvedFileURL=FileLocator.toFileURL(fileURL);
		if(null==resolvedFileURL) {
			System.out.println("resolvedFileURL null");
			return null;
		}
		URI resolvedURI = new URI(resolvedFileURL.getProtocol(),resolvedFileURL.getPath(), null);
		f = new File(resolvedURI);
		
		return f;
	}
	public static URL retrieveURL(String filename) {
		Bundle bundle = Platform.getBundle("VACCS");
		URL fileURL = bundle.getEntry(filename);
		if(null==fileURL)
			return null;
		try {
			return FileLocator.toFileURL(fileURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String readFileIntoString(String filePath) {
		try {
			URL url = FileLocator.toFileURL(FileLocator.find(Platform.getBundle("VACCS"), new Path(filePath), null));
			return new String(Files.readAllBytes(Paths.get(new URI(url.toString().replaceAll(" ", "%20")))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} 
	}
	
	public static String retrieveStringURL(String filename){
		URL url = retrieveURL(filename);
		if(null==url)
			return null;
		return url.toString();
	}
	
	public static String retrieveHTMLStaticURL(String problemID) {
		String filename = "/html/Static/"+problemID+".html";
		return retrieveStringURL(filename);
	}
	
	public static String retrieveHTMLHelpURL(String functionName){
		String filename = "/html/Help/"+functionName+".html";
		return retrieveStringURL(filename);
		
	}
	public static String retrieveHTMLSummaryHelpURL(String functionName){
		//return retrieveHTMLHelpURL(functionName);
		String filename = "/html/HelpSummary/"+ functionName+".html";
		return retrieveStringURL(filename);
	}
}
