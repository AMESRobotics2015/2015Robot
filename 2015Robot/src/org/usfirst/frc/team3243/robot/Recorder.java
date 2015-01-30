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
	ArrayList<Double> Data0 = new ArrayList<Double>();//creates 3 object-specific arraylists
	ArrayList<Double> Data1 = new ArrayList<Double>();
	ArrayList<Double> Data2 = new ArrayList<Double>();	
	static Timer stopRecord= new Timer(); //creates timer object to stop recording after 15 seconds
	int outputCounter = 0;
	static int counter = getCounter();//sets value of recording counter to the last recording value
	static int planNumber = 0;//number of plan to execute on playback
	static boolean isRead= false;//checks to see if the file was correctly read
	static boolean startRecord = false;
	
	private class recordingTimer extends TimerTask{//creates task to run after15 seconds

		@Override
		public void run() {
			writeData();//writes data to file
			RobotMap.isRecording = false;//stops recording
			setCounter();//saves counter
			startRecord = false;
		}
		
	}
	
	
	
	
	
	
	
	public void getData(double[] array){//gets data from joystick array
		if (array[0]!=0 || array[1]!=0 || array[2]!=0 ){//checks to see if joystick value != 0 
			startRecord = true;//sets recording to begin
			RobotMap.timerOn = true;//sets timer to begin
		}
		if(RobotMap.clearData){//clears data if not already cleared
			this.Data0.clear();
			this.Data1.clear();
			this.Data2.clear();
			RobotMap.clearData = false;//stops clearing of data
		}else if (RobotMap.isRecording && startRecord){//starts recording if button is pressed and joystick has been changed
			
		this.Data0.add(array[0]);//records data to static arraylists
		this.Data1.add(array[1]);
		this.Data2.add(array[2]);
		if (RobotMap.timerOn){//starts timer if told to do so
			stopRecord.schedule(new recordingTimer(), 15000);//schedules stop in 15 seconds
			RobotMap.timerOn = false;//stops timer
			}
		}
		
	}
	
	public void writeData(){//writes data to file
		try
	      {
					
			++counter;//increments # of recording
	         FileOutputStream fileOut = new FileOutputStream("./" + "Recording" + counter + ".JSON");//outputs recording and # to a json
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this);//writes recorder object to file
	         out.close();
	         fileOut.close();	
	         RobotMap.clearData = true;//sets data to clear on next run       
	         
	         
	      }catch(IOException i){}
		
	}
	public Recorder readData(){
		 Recorder reader= new Recorder();
		 
		try
	      {
	         FileInputStream fileIn = new FileInputStream("./" + "Recording" + planNumber + ".JSON");//reads in file with #
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.Data0.clear();//clears recorder object data
	         this.Data1.clear();
	         this.Data2.clear();
	         reader = (Recorder) in.readObject();//sets reader object to read in object
	         in.close();
	         fileIn.close();
	         isRead=true;//lets program know it's been read in
	      }catch(IOException i){}
		   catch(ClassNotFoundException c){}
		return reader;//returns read object
	}
	public double[] playBackNext(){//plays back recording
		
		
		double[]playArray = new double[3];//creates array to return to drive method
		if(RobotMap.playIncrement > readData().Data0.size()){//if it keeps reading larger than the size for any reason, this stops the robot
			playArray[0]=0;
			playArray[1]=0;
			playArray[2]=0;
		}else
		{
			playArray[0]=readData().Data0.get(RobotMap.playIncrement);//sets array elements to saved ones
			playArray[1]=readData().Data1.get(RobotMap.playIncrement);
			playArray[2]=readData().Data2.get(RobotMap.playIncrement);
			++RobotMap.playIncrement;//increments element of arraylist
		}
		
		
		
		return playArray;
		}
		public void setCounter(){
			
			FileOutputStream counterOut;
			try {
				outputCounter = counter;
				counterOut = new FileOutputStream("./Counter.JSON");
		         ObjectOutputStream counterFile = new ObjectOutputStream(counterOut);
    	         counterFile.writeObject(outputCounter);
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
