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
	
	//driv[0] is y axis
	//driv[1] is x axis
	
	public void driveomni(double[] driv){	
			A.set(driv[0]);	
			B.set(driv[1]);
			C.set(driv[2]);
			D.set(driv[3]);
				
	}
}
