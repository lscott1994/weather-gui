
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
public static  double[] readData;
public static double[] yearAv;
public double count = 0;
public String[] month;
	
public Main(double[] readData, double[] yearAv)
{
	this.readData = readData;
	this.yearAv = yearAv;
}
 public double[] getData() {
        return readData;
    }

   public double[] getAv() {
        return yearAv;
    }

	
public static void readData() throws IOException 
 {
	 double[] readData = new double [13];
	 double[] yearAv = new double [13];
	 double count = 0;
	 double[] getData = new double[13];
	 
	  File file = new File("CSI203_F16_Project_03_data.txt");
	  Scanner input = new Scanner(file);
	  
			  try{
				  while(true){
					  if(input.hasNextLine() == false ){
						  input.close();
						  throw new EOFException();
					
						 }
					  else{
					  for(int i = 0; i < 13; i++){
						 readData[i] = input.nextDouble();
						
						 yearAv[i] += readData[i]; 
						 	if(readData[i] >= 1000)
							 {count++;}
					  }
					  }
				 }
					
			  }catch(EOFException e){
			  input.close();
				  		throw e;
				  		
			  }finally{
				 
				  for(int i = 1; i < yearAv.length; i++)
				  {
					  yearAv[i] = yearAv[i]/count;
					 
				  }
				  for(int i = 0; i < yearAv.length; i++)
				  {
					  getData[i] = readData[i];
				  }
			  
				  input.close();
				  
				  JFrame f = new JFrame();
				  f.setTitle("Weather Report");
				  f.setSize(800, 500);
				  f.add(new Main(getData, yearAv));
				  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				  f.setVisible(true);
				 
			}
			  
			  
 			}

private static double round (double value) {
    int rounded = (int) Math.pow(10, 1);
    return (double) Math.round(value * rounded) / rounded;
}
 

@Override
protected void paintComponent (Graphics g){
	 
		super.paintComponent(g);
	    double minVal = 0;
	    double maxVal = 0;
	    for (int i = 1; i < readData.length; i++) {
	      if (minVal > readData[i])
	        minVal = readData[i];
	      if (maxVal < readData[i])
	        maxVal = readData[i];
	    }
	    
	    //setting up months for the x axis
	    String[] month = new String[12];
	    month[0] = "JAN";
	    month[1] = "FEB";
	    month[2] = "MAR";
	    month[3] = "APR";
	    month[4] = "MAY";
	    month[5] = "JUN";
	    month[6] = "JUL";
	    month[7] = "AUG";
	    month[8] = "SEP";
	    month[9] = "OCT";
	    month[10] = "NOV";
	    month[11] = "DEC";
	    
	    //Coordinates for x and y axes
	    int xAx1 = 100;
	    int xAx2 = 700;
	    int yAx1 = 100;
	    int yAx2 = 400;
	    
	    //chart width and height
	    int chartW = xAx2 - xAx1;
	    int chartH = yAx2 - yAx1;
	    
	    int barW = chartW/(readData.length * 2);
	    String title = "" +(int)readData[0];
	
	    Font titleFont = new Font("SansSerif", Font.BOLD, 20);
	    FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
	    Font labelFont = new Font("SansSerif", Font.PLAIN, 5);
	    FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);
	 
	   
	    
	    //Add title centered to the graph
	    int half = (chartW) / 2; 
	    int x = half + yAx1;
	    g.setFont(titleFont);
	    
	    String titlelength = "Rainfall for " + title;
		int wide = titleFontMetrics.stringWidth(titlelength);
		int moveBy = wide/2;
	    g.drawString("Rainfall for " + title, x-moveBy, yAx1);   
	    g.drawLine(xAx1, yAx1, xAx1, yAx2);
	    g.drawLine(xAx1, yAx2, xAx2, yAx2);


	    int top = titleFontMetrics.getHeight();
	    int bottom = labelFontMetrics.getHeight();
	    
	    double scale = (chartH - top - bottom) / (maxVal - minVal);
	   
	    //Draw current year's data
	    int base = (int)((maxVal * scale - (top + bottom)) - yAx1);
	    
	    for (int i = 1; i < readData.length; i++) {
	        int valueX = (i * barW*2);
	        int valueY = base;
	        int height = (int) (readData[i] * scale);
	        if (readData[i] >= 0)
	          valueY += (int) ((maxVal - readData[i]) * scale);
	        else {
	          valueY += (int) (maxVal * scale);
	          height = -height;
	        }
	        g.setColor(Color.red);
	        g.fillRect(valueX + xAx1, valueY, barW-2, height);
	        g.setColor(Color.black);
	        
	        //Add actual value on top of the rectangle (cosmetics)
	    	Font data = new Font("SansSerif", Font.PLAIN, 10);
	    	g.setColor(Color.BLACK);
	    	g.setFont(data);
	    	g.drawString(""+readData[i], valueX+xAx1, valueY -10 );
	   
	    	//Draw averaged data rectangles
	        int vX = (i * barW * 2 ) +xAx1;
		    int h = (int) (yearAv[i] * scale);
		 
		    int vY = base;
		       if (yearAv[i] >= 0){
		    	   vY += (int) ((maxVal - yearAv[i]) * scale);
		       }
		       else {
		    	   vY += (int) (maxVal * scale);
		    	   h = -h;
		        } 
		       
		       double[] rounded = new double [yearAv.length];
		       for (int k = 0; k < yearAv.length; k++)
		       {
		    	 rounded[k] =  (int)round(yearAv[k]);
		       }
		       
		       
		       DecimalFormat df = new DecimalFormat("#.00");
		      
		       
		       
		    g.setColor(Color.blue);
		    g.fillRect(vX + barW, vY, barW-2, h); 
	    	g.drawString(""+df.format(yearAv[i]), vX+barW, vY -10 );
	      }
	    
	    
	    //creates labels for the months
	    for(int i = 0; i < month.length; i ++)
	    {
	    	Font label = new Font("SansSerif", Font.PLAIN, 10);
	    	 g.setColor(Color.BLACK);
	    	 g.setFont(label);
	    	 g.drawString(month[i], ((i+1)*barW*2)+xAx1, 425);
	    }
	    
	    //Draw min and max value on y axis
	    int maxScaled = (int)scale *(int)maxVal;
	    int maxAdj = maxScaled - (top-bottom);
	    int yLabel = maxAdj - yAx1;
	    g.drawString(""+maxVal, xAx1-20, (yLabel));
	    g.drawString("0", xAx1-20, 400);
	    
	    
	    //Draw the rest of the values on y axis 
	    for (int i = 1; i <= 12; i++)
	    {
	    	int scaled = ((int)scale *i);
	    	
	    	int scaleAdj = (scaled);
	    	int yLabel2 = (400 - scaleAdj);
	    	g.drawString(""+i, xAx1-20, yLabel2);
	    }
	    
	    //Legend for graph 
	    g.setColor(Color.red);
	    g.fillRect(600, 50, 25, 25);
	    g.drawString("Actual amount of rainfall", 630, 65);
	   
	    g.setColor(Color.blue);
	    g.fillRect(600, 80, 25, 25);
	    g.drawString("Average rainfall", 630, 95);
	    
}


}
