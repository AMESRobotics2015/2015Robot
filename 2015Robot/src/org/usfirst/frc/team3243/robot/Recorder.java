package org.usfirst.frc.team3243.robot;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Recorder implements java.io.Serializable {
	static ArrayList<Double> Data0 = new ArrayList<Double>();
	static ArrayList<Double> Data1 = new ArrayList<Double>();
	static ArrayList<Double> Data2 = new ArrayList<Double>();
	static int counter = 0;
	static int planNumber = 0;
	static boolean isRead= false;
	
	
	
	
	
	public void getData(double[] array){
		
		Data0.add(array[0]);
		Data1.add(array[1]);
		Data2.add(array[2]);			
	}
	
	public void writeData(){
		Recorder writer = new Recorder();
		try
	      {
			counter++;
	         FileOutputStream fileOut =
	         new FileOutputStream("./" + counter + ".JSON");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(writer);
	         out.close();
	         fileOut.close();	         
	      }catch(IOException i){}
		
	}
	public Recorder readData(){
		 Recorder reader= new Recorder();
		 
		try
	      {
	         FileInputStream fileIn = new FileInputStream("./" + planNumber + ".JSON");
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
			playArray[0]=Data0.get(RobotMap.playIncrement);
			playArray[1]=Data1.get(RobotMap.playIncrement);
			playArray[2]=Data2.get(RobotMap.playIncrement);
			++RobotMap.playIncrement;
		}
		
		
		
		return playArray;
		}
}
