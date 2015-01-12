package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.*;

public class MotorControl {
	
	protected static Victor A;
	protected static Victor B;
	protected static Victor C;
	protected static Victor D;
	
	public void init(){
		
		A = new Victor(0);
		B = new Victor(1);
		C = new Victor(2);
		D = new Victor(3);
		
	}
	
	public void driveomni(double[] driv){
		
	}
}
