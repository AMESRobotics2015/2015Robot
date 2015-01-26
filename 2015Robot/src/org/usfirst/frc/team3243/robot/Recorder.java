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
	ArrayList<Double> IOData0 = new ArrayList<Double>();//creates 3 object-specific arraylists
	ArrayList<Double> IOData1 = new ArrayList<Double>();
	ArrayList<Double> IOData2 = new ArrayList<Double>();
	static ArrayList<Double> Data0 = new ArrayList<Double>();//creates 3 static arraylists
	static ArrayList<Double> Data1 = new ArrayList<Double>();
	static ArrayList<Double> Data2 = new ArrayList<Double>();
	Recorder IO = new Recorder();//create class-wide object for I/O
	static Timer stopRecord= new Timer(); //creates timer object to stop recording after 15 seconds
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
			Data0.clear();
			Data1.clear();
			Data2.clear();
			RobotMap.clearData = false;//stops clearing of data
		}else if (RobotMap.isRecording && startRecord){//starts recording if button is pressed and joystick has been changed
			
		Data0.add(array[0]);//records data to static arraylists
		Data1.add(array[1]);
		Data2.add(array[2]);
		if (RobotMap.timerOn){//starts timer if told to do so
			stopRecord.schedule(new recordingTimer(), 15000);//schedules stop in 15 seconds
			RobotMap.timerOn = false;//stops timer
			}
		}
		
	}
	
	public void writeData(){//writees data to file
		try
	      {
			
				IO.IOData0 = Data0;//sets object arraylists tostatic ones
				IO.IOData1 = Data1;
				IO.IOData2 = Data2;
			
			++counter;//increments # of recording
	         FileOutputStream fileOut =
	         new FileOutputStream("./" + "Recording" + counter + ".JSON");//outputs recording and # to a json
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(IO);//writes IO recorder object to file
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
	         IO.IOData0.clear();//clears IO object data
	         IO.IOData1.clear();
	         IO.IOData2.clear();
	         reader = (Recorder) in.readObject();//sets reader object to read in object
	         in.close();
	         fileIn.close();
	         isRead=true;//lets program know it's been read in
	      }catch(IOException i){}
		   catch(ClassNotFoundException c){}
		return reader;//returns read object
	}
	public double[] playBackNext(){//plays back recording
		if(!isRead){//reads data to IO if it isn't read
			IO = readData();//sets IO to the read object
			}
		double[]playArray = new double[3];//creates array to return to drive method
		if(RobotMap.playIncrement > IO.Data0.size()){//if it keeps reading larger than the size for any reason, this stops the robot
			playArray[0]=0;
			playArray[1]=0;
			playArray[2]=0;
		}else{
			playArray[0]=readData().IO.Data0.get(RobotMap.playIncrement);//setsarray elements to saved ones
			playArray[1]=readData().IO.Data1.get(RobotMap.playIncrement);
			playArray[2]=readData().IO.Data2.get(RobotMap.playIncrement);
			++RobotMap.playIncrement;//increments element of arraylist
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
