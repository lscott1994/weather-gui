import java.io.IOException;

public class TestReport extends Main {

	public TestReport(double[] d, double[] y) {
	super(d,y);	
	} 
	
	public static void main(String[] args) {
		  try{
			  readData();
			 }catch(IOException e) {
				e.printStackTrace();
			 }	    	    
	  }
	
}
