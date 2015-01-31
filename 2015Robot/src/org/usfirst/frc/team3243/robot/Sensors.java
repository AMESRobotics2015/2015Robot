package org.usfirst.frc.team3243.robot;
import edu.wpi.first.wpilibj.*;

public class Sensors {
	
	protected static Gyro G;
	
	public Sensors(){
		
		G = new Gyro(0);
		
	}
	
	public double gyread(){
		double gyreading = G.getAngle()%360;//gyro can go higher than 360 degrees so we check for the remainder
		gyreading = gyreading *Math.PI/180;
		return gyreading;
	}

}
