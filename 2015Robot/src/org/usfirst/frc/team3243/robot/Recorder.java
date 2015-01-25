package org.usfirst.frc.team3243.robot;

import java.util.ArrayList;
import java.util.TimerTask;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;


public class Recorder implements java.io.Serializable {
	ArrayList<Double> Data0 = new ArrayList<Double>();
	ArrayList<Double> Data1 = new ArrayList<Double>();
	ArrayList<Double> Data2 = new ArrayList<Double>();
	static Timer stopRecord= new Timer();
	static int counter = getCounter();
	static int planNumber = 0;
	static boolean isRead= false;
	
	private class recordingTimer extends TimerTask{

		@Override
		public void run() {
			writeData();
			RobotMap.isRecording = false;
			setCounter();
		}
		
	}
	
	
	
	
	
	
	
	public void getData(double[] array){
		if(RobotMap.clearData){
			Robot.getR().Data0.clear();
			Robot.getR().Data1.clear();
			Robot.getR().Data2.clear();
			RobotMap.clearData = false;
		}else if (RobotMap.isRecording){
			
		Robot.getR().Data0.add(array[0]);
		Robot.getR().Data1.add(array[1]);
		Robot.getR().Data2.add(array[2]);
		if (RobotMap.timerOn){
			stopRecord.schedule(new recordingTimer(), 15000);
			RobotMap.timerOn = false;
			}
		}
	}
	
	public void writeData(){
		try
	      {
			counter++;
	         FileOutputStream fileOut =
	         new FileOutputStream("./" + "Recording" + counter + ".JSON");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(Robot.getR());
	         out.close();
	         fileOut.close();	
	         RobotMap.clearData = true;       
	         
	         
	      }catch(IOException i){}
		
	}
	public Recorder readData(){
		 Recorder reader= new Recorder();
		 
		try
	      {
	         FileInputStream fileIn = new FileInputStream("./" + "Recording" + planNumber + ".JSON");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         reader = (Recorder) in.readObject();
	         in.close();
	         fileIn.close();
	         isRead=true;
	      }catch(IOException i){}
		   catch(ClassNotFoundException c){}
		return reader;
	}
	public double[] playBackNext(){
		if(!isRead){
			readData();
			}
		double[]playArray = new double[3];
		if(RobotMap.playIncrement > Data0.size()){
			playArray[0]=0;
			playArray[1]=0;
			playArray[2]=0;
		}else{
			playArray[0]=readData().Data0.get(RobotMap.playIncrement);
			playArray[1]=readData().Data1.get(RobotMap.playIncrement);
			playArray[2]=readData().Data2.get(RobotMap.playIncrement);
			++RobotMap.playIncrement;
		}
		
		
		
		return playArray;
		}
		public void setCounter(){
			
			FileOutputStream counterOut;
			try {
				counterOut = new FileOutputStream("./Counter.JSON");
		         ObjectOutputStream counterFile = new ObjectOutputStream(counterOut);
    	         counterFile.writeObject(counter);
    	         counterFile.close();
    	         counterOut.close();
			} catch (FileNotFoundException e) {		
				
			}catch(IOException i){}
	    
		}
		public static int getCounter(){
			int reader=0;
			 
			try
		      {
		         FileInputStream fileIn = new FileInputStream("./Counter.JSON");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         reader = (int) in.readObject();
		         in.close();
		         fileIn.close();		         
		      }catch(IOException i){}
			   catch(ClassNotFoundException c){}
			return reader;
			
		}
}
