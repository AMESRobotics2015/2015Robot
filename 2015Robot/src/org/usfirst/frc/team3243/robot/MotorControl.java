package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.*;

public class MotorControl {
	
	protected static Victor FrLe;
	protected static Victor FrRi;
	protected static Victor BaLe;
	protected static Victor BaRi;
	
	public void init(){
		FrLe = new Victor(3);
		FrRi = new Victor(0);
		BaLe = new Victor(2);
		BaRi = new Victor(1);
	}
	
	public 
}
