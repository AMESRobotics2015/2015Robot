package org.usfirst.frc.team3243.robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Reader {
	public void readData(Recorder r){
		 Recorder reader= new Recorder();
		 
		try
	      {
			 r.Data0.clear();//clears recorder object data
	         r.Data1.clear();
	         r.Data2.clear();
	         r.ElevData.clear();
	         FileInputStream fileIn = new FileInputStream("/home/lvuser/auto/Recording " + Recorder.planNumber + ".JSON");//reads in file with #
	         ObjectInputStream in = new ObjectInputStream(fileIn);	         
	         reader = (Recorder) in.readObject();//sets reader object to read in object
	         in.close();
	         fileIn.close();
	         Recorder.isRead=true;//lets program know it's been read in
	         r.Data0 = reader.Data0;
	 		 r.Data1 = reader.Data1;
	 		 r.Data2 = reader.Data2;
	 		 r.ElevData = reader.ElevData;
	 		Recorder.isRead = true;
	      }catch(IOException i){}
		   catch(ClassNotFoundException c){}
		
		
	}
	public static int getCounter(){
		int reader=0;
		 
		try
	      {
	         FileInputStream fileIn = new FileInputStream("/home/lvuser/auto/Counter.JSON");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         reader = (int) in.readObject();
	         in.close();
	         fileIn.close();		         
	      }catch(IOException i){ return 1;}
		   catch(ClassNotFoundException c){return 1;}
		
		return reader;			
	}
}
