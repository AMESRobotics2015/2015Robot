package org.usfirst.frc.team3243.robot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
public class Writer implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int outputCounter = 0;
	public void writeData(Recorder r){//writes data to file
		//try
	     // {
			
			FileOutputStream FileOut;
			try {
				 FileOut = new FileOutputStream("/home/lvuser/auto/Recording " + Recorder.counter + ".JSON");//outputs recording and # to a json
		         ObjectOutputStream fileout = new ObjectOutputStream(FileOut);
		         fileout.writeObject(r);//writes recorder object to file
		         fileout.close();
		         FileOut.close();
		         RobotMap.writeToFile = false;
				++Recorder.counter;//increments # of recording
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
			
			/*FileOutputStream FileOut = new FileOutputStream("./Recording " + Recorder.counter + ".JSON");//outputs recording and # to a json
	         ObjectOutputStream fileout = new ObjectOutputStream(FileOut);
	         fileout.writeObject(r);//writes recorder object to file
	         fileout.close();
	         FileOut.close();
					
			++Recorder.counter;//increments # of recording
	         FileOutputStream Data0Out = new FileOutputStream("./" + "Data0-" + Recorder.counter + ".JSON");//outputs recording and # to a json
	         ObjectOutputStream Data0out = new ObjectOutputStream(Data0Out);
	         Data0out.writeObject(r.Data0);//writes recorder object to file
	         Data0out.close();
	         Data0Out.close();
	         
	         FileOutputStream Data1Out = new FileOutputStream("./" + "Data1-" + Recorder.counter + ".JSON");//outputs recording and # to a json
	         ObjectOutputStream Data1out = new ObjectOutputStream(Data1Out);
	         Data1out.writeObject(r.Data1);//writes recorder object to file
	         Data1out.close();
	         Data1Out.close();
	         
	         FileOutputStream Data2Out = new FileOutputStream("./" + "Data2-" + Recorder.counter + ".JSON");//outputs recording and # to a json
	         ObjectOutputStream Data2out = new ObjectOutputStream(Data2Out);
	         Data2out.writeObject(r.Data2);//writes recorder object to file
	         Data2out.close();
	         Data2Out.close();
	         
	         FileOutputStream ElevDataOut = new FileOutputStream("./" + "ElevData-" + Recorder.counter + ".JSON");//outputs recording and # to a json
	         ObjectOutputStream ElevDataout = new ObjectOutputStream(ElevDataOut);
	         ElevDataout.writeObject(r.ElevData);//writes recorder object to file
	         ElevDataout.close();
	         ElevDataOut.close();
	         RobotMap.clearData = true;//sets data to clear on next run  
	         Recorder.writeToFile = false;
	         */
	         
	       
	public void setCounter(){
		
		FileOutputStream counterOut;
		try {
			outputCounter = Recorder.counter;
			counterOut = new FileOutputStream("./Counter.JSON");
	         ObjectOutputStream counterFile = new ObjectOutputStream(counterOut);
	         counterFile.writeObject(outputCounter);
	         counterFile.close();
	         counterOut.close();
		} catch (FileNotFoundException e) {		
			
		}catch(IOException i){}
    
	}
	
}
