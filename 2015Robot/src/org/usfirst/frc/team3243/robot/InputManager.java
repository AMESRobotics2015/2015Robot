package org.usfirst.frc.team3243.robot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class InputManager{
	
	static double[] axis = new double [3];//holds the input value from our left/right controllers
	static double[] gameaxis = new double [2];
	
	protected static Joystick ps2controller;//our controller
	protected static Joystick gamecontroller;//game piece
	protected static int rampUpNum = 3;//used for ramp up
	protected static boolean recordButton;
	
	//initializes the controllers
	public InputManager() {
		
		ps2controller = new Joystick(0);
		gamecontroller = new Joystick (1);
		//rampUpNum = int
		
	}
	/**
	 * 
	 * It retrieves the getAxisValue class's values, deadzones, and then it calls the ramp method, which regresses it to be a cubu=ic function rather than a line
	 * @return
	 */
	public static double [] getFinalAxis(double gyro){
		return (ramp(adjustGetAxisValue(gyro)));
		//return (ramp(getAxisValue()));
		//three things happen in this class.
		//1)you get axis values
		//2)then you deadzone the values
		//3) You transform the deadzoned values into a cubic equation
	}
	
	public static double[] getAxisValue(){
		
		axis[0] = ps2controller.getRawAxis(3);//y axis 
		axis[1] = -ps2controller.getRawAxis(2);//x axis
		axis[2] = ps2controller.getRawAxis(0);//pivoting
		//axis = deadZone(axis);//transforms the array to deadzone to round values as necessary (ex. -0.03 to 0)
		return axis;
		
		
	}
	public static double[] adjustGetAxisValue( double gyroAngle){
		double mag; // this is the magnitude of our vector.
		double controllerAngle =0;
		axis[0] = ps2controller.getRawAxis(3);//y axis 
		axis[1] = ps2controller.getRawAxis(2);//x axis
		if (axis[1]<0){
			controllerAngle = Math.PI + Math.atan(axis[0]/axis[1]);//get the angle that the joystick is pointing facing, in case the angle is in the second or third quadrant
		}
		else{
			controllerAngle = Math.atan(axis[0]/axis[1]);//get angle if it's in the first or fourth quadrant
		}
		
		mag = Math.sqrt(Math.pow(axis[0], 2)*Math.pow(axis[1], 2));
		
		//System.out.println("joystick angle" + controllerAngle);
		axis[1] = mag*Math.cos(gyroAngle+controllerAngle); // using the equation kole gave where our final inputs include MAGNITUDE
		axis[0] = mag*Math.sin(gyroAngle+controllerAngle); 
		axis[2] = ps2controller.getRawAxis(0);//pivoting
		axis = deadZone(axis);//transforms the array to deadzone to round values as necessary (ex. -0.03 to 0)
		return axis;
		
		
	}
	
	public static boolean getGyroResetButton()
	{
		 return ps2controller.getRawButton(2);
		
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
		
			//if you want to graph it, it is y=0.66x^3+0.33x
		
		for(byte x = 0; x < 3 ; x++){
			axis[x] = (0.6667 * (Math.pow(axis[x], 3))+(0.333 * axis[x]));
		}
		
		return (axis);
	}	
	
	public void record(){
		
		if(ps2controller.getRawButton(4)){
			RobotMap.isRecording = true;
			System.out.println("Successful button press");
			RobotMap.timerOn = true;
		}
		
		
	}
	public static double[] getGameControllerAxis(){
		gameaxis[0] = gamecontroller.getRawAxis(3);//y axis 
		gameaxis[1] = gamecontroller.getRawAxis(2);//x axis
		gameaxis = deadZone(axis);//transforms the array to deadzone to round values as necessary (ex. -0.03 to 0)
		return gameaxis;
	}

}
