package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.*;


public class InputManager extends Robot {
	
	static double[] axis = new double [2];//holds the input value from our left/right controllers
	static double[] mota = new double [2];
	static double[] motb = new double [2];
	static double[] motc = new double [2];
	static double[] motd = new double [2];
	static double[] fnlaxis = new double [3];
	
	protected static Joystick ps2controller;//our controller
	
	protected static int rampUpNum = 3;//used for ramp up
	
	//initializes the controller
	public void init() {
		
		ps2controller = new Joystick(1);
		//rampUpNum = int
		
	}
	/**
	 * 
	 * It retrieves the getAxisValue class's values, deadzones, and then it calls the ramp method, which regresses it to be a cubu=ic function rather than a line
	 * @return
	 */
	public static double [] getFinalAxis(){
		return (ramp(getAxisValue()));//can add ramp later...
		//three things happen in this class.
		//1)you get axis values
		//2)then you deadzone the values
		//3) You transform the deadzoned values into a cubic equation
	}
	
	public static double[] getAxisValue(){
		
		axis[0] = ps2controller.getRawAxis(2);//y axis 
		axis[1] = ps2controller.getRawAxis(1);//x axis
		axis[2] = ps2controller.getRawAxis(3);//pivioting
		axis = deadZone(axis);//transforms the array to deadzone to round values as necessary (ex. -0.03 to 0)
		return axis;
		
		
	}
	
	/**
	 * transforms the array to deadzone to round values as necessary (ex. 0.02 to 0)
	 * @param axis
	 * @return
	 */
	public static double[] deadZone(double[] axis){
		for (int i = 0; i< axis.length; i++){
			if ((axis[i] <= 0.05) && (axis[i] >= -0.05)) {//i will iterate to 0,1,2 since axis array size is 3
				axis[i] = 0;//if the condition is satisfies, round it to zero
			}
		}
		return axis;
	}
	/**
	 * transforms the values in the array to it's coresponding cubica values, based on formulas
	 * @param axis
	 * @return
	 */
	public static double[] ramp(double[] axis){
		
		double fina, finb, finc, find;
		
		for (int i = 0; i<2; i++){
			for(byte num = 0; num < 4; num++) {
				if(num==0){
				mota[0] = (((.667)*(Math.pow(axis[i], 3)))+((.333)*(axis[i])));
				mota[1] = (((.667)*(Math.pow(axis[i], 3)))+((.333)*(axis[i])));
				mota[2] = mota[0] + mota[1];
				}else if(num ==1){
				motb[0] = (((.667)*(Math.pow(axis[i], 3)))+((.333)*(axis[i])));
				motb[0] = (((.667)*(Math.pow(axis[i], 3)))+((.333)*(axis[i])));
				motb[2] = motb[0] + motb[1];
				finb = motb[2];
				}else if(num ==2){
				motc[0] = (((.667)*(Math.pow(axis[i], 3)))+((.333)*(axis[i])));
				motc[0] = (((.667)*(Math.pow(axis[i], 3)))+((.333)*(axis[i])));
				motc[2] = motc[0] + motc[1];
				finc = motc[2];
				}else if(num ==3){
				motd[0] = (((.667)*(Math.pow(axis[i], 3)))+((.333)*(axis[i])));
				motd[0] = (((.667)*(Math.pow(axis[i], 3)))+((.333)*(axis[i])));
				motd[2] = motd[0] + motd[1];	
				find = motd[2];
				}
			//if you want to graph it, it is y=0.66x^3+0.33x
			}
		}
		
		fnlaxis[0] = mota[2];
		fnlaxis[1] = motb[2];
		fnlaxis[2] = motc[2];
		fnlaxis[3] = motd[2];
		
		return (fnlaxis);
	}
	
	public static double[] limit(double[] axis){
		
	}
	

}
