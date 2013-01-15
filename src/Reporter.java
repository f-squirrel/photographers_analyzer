/**
 * Created with IntelliJ IDEA.
 * User: ddanilov
 * Date: 1/11/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Reporter {
	public void report() throws Exception;
	public void setOutputData(Object outputData);
}
