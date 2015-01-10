package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.*;


public class InputManager extends Robot {
	
	static double[] axis = new double [2];
	
	protected static Joystick ps2controller;
	
	protected static RobotMap R;
	
	public void init() {
		
		ps2controller = new Joystick(1);
		R = new RobotMap();
		
	}
	
	public static double [] getFinalAxis(){
		double[] mtrval = new double[2];
		mtrval = ramp(getAxisValue());
		return mtrval;
	}
	
	public static double[] getAxisValue(){
		
		axis[0] = ps2controller.getRawAxis(2);//y axis 
		axis[1] = ps2controller.getRawAxis(1);//x axis
		axis[2] = ps2controller.getRawAxis(3);
		axis = deadZone(axis);
		return axis;
		
		
	}
	
	public static double[] deadZone(double[] axis){
		for (byte si = 0; si< axis.length; si++){
			if ((axis[si] <= 0.05) && (axis[si] >= -0.05)) {
				axis[si] = 0;
			}
		}
		return axis;
	}
	
	public static double[] ramp(double[] axis){
		for (byte si = 0; si<2; si++){
			
			axis[si] = (((.667)*(Math.pow(axis[si], 3)))+((.333)*(axis[si])));
			
		}
		return (axis);
	}

}
