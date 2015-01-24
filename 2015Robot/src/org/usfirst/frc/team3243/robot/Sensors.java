package org.usfirst.frc.team3243.robot;
import edu.wpi.first.wpilibj.*;

public class Sensors {
	
	protected static Gyro G;
	
	public Sensors(){
		
		G = new Gyro(3);
		
	}
	
	public double gyread(){
		double gyreading = G.getAngle();
		return gyreading;
	}

}
