package org.usfirst.frc.team3243.robot;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import java.io.*;


public class Recorder implements java.io.Serializable {
	ArrayList<Double> Data0 = new ArrayList<Double>();//creates 3 object-specific arraylists
	ArrayList<Double> Data1 = new ArrayList<Double>();
	ArrayList<Double> Data2 = new ArrayList<Double>();	
	ArrayList<Double> ElevData = new ArrayList<Double>();//gets joystick input for elevator
	ArrayList<Double> GrabberData = new ArrayList<Double>();	
	static transient Timer stopRecord= new Timer(); //creates timer object to stop recording after 15 seconds
	static transient int counter = Reader.getCounter();//sets value of recording counter to the last recording value
	static transient int planNumber = 1;//number of plan to execute on playback
	static transient boolean isRead= false;//checks to see if the file was correctly read
	static transient boolean startRecord = false;
	static transient boolean writeToFile =false;
	transient InputManager IM = new InputManager();
	
	private class recordingTimer extends TimerTask{//creates task to run after15 seconds

		@Override
		public void run() {
			
			RobotMap.isRecording = false;//stops recording
			startRecord = false;			
			RobotMap.writeToFile = true;
			System.out.println("timer ran");
		}
		
	}	
	
	public void getDriveData(double[] array){//gets data from joystick array
		
		IM.record();
		if(RobotMap.clearData){//clears data if not already cleared
			this.Data0.clear();
			this.Data1.clear();
			this.Data2.clear();
			
			RobotMap.clearData = false;//stops clearing of data
		}else if (RobotMap.isRecording /*&& startRecord */){//starts recording if button is pressed and joystick has been changed
			
		this.Data0.add(array[0]);//records data to static arraylists
		this.Data1.add(array[1]);
		this.Data2.add(array[2]);
		//this.ElevData.add(array[3]);
		if (RobotMap.timerOn){//starts timer if told to do so
			stopRecord.schedule(new recordingTimer(), 15000);//schedules stop in 15 seconds     
			RobotMap.timerOn = false;//stops timer
			System.out.println("timer started");
			}
		}
		
	}
	
	
	
	public double[] playBackNext(){//plays back recording		
		//double[]playArray = new double[4];//creates array to return to drive method
		double[]playArray = new double[3];
		if(RobotMap.playIncrement > this.Data0.size()-1){//if it keeps reading larger than the size for any reason, this stops the robot
			playArray[0]=0;
			playArray[1]=0;
			playArray[2]=0;
			
		}else
		{
			playArray[0]=this.Data0.get(RobotMap.playIncrement);//sets array elements to saved ones
			playArray[1]=this.Data1.get(RobotMap.playIncrement);
			playArray[2]=this.Data2.get(RobotMap.playIncrement);
			
			++RobotMap.playIncrement;//increments element of arraylist
		}
		
		
		
		
		return playArray;
		}
		
		
}
