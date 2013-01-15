import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: ddanilov
 * Date: 1/11/13
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileReporter implements Reporter{
	private final String filename;
	private Object outputData;

	public FileReporter(String filename) {
		this.filename = filename;
		outputData = null;
	}

	public void report() throws Exception {
		File file = new File(filename);
		FileOutputStream f = new FileOutputStream(file);
		ObjectOutputStream s = new ObjectOutputStream(f);
		s.writeObject(outputData);
		s.flush();
	}

	public String getFilename() {
		return filename;
	}

	public void setOutputData(Object outputData) {
		this.outputData = outputData;
	}

	public Object getOutputData() {
		return outputData;
	}
}
