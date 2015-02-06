
package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DriverStation;

import java.lang.System;
//import edu.wpi.first.wpilibj
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	private static InputManager IM;
	private static MotorControl MC;
	private static Sensors S;
	//protected static Watchdog WD;
	private Recorder R;
	private Writer WR;
	private Reader RE;
	
    public void robotInit() {
    	IM = new InputManager();
    	MC = new MotorControl();
    	S = new Sensors();
    	R = new Recorder();
    	WR = new Writer();
    	RE = new Reader(); 	    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(!Recorder.isRead){
    	RE.readData(R);
    	}
    	MC.driveomni(R.playBackNext());
    	//whateverfunctiondriveselevator(R.playBackElev());
    	 
   
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//while(true){
    		MC.driveomni(IM.getFinalAxis(S.gyread()));
    		//MC.driveomni(IM.getFinalAxis()); // this is in case our new drive code fails and we want to fall back on old stuff.
    		IM.record();
    		R.getDriveData(IM.getFinalAxis(S.gyread()));
    		if(RobotMap.writeToFile){
    			WR.writeData(R);
    		}
    		
    		double fin = S.gyread();
    		//System.out.println(fin);
    		if(IM.getGyroResetButton())
    		{
    			S.G.reset();
    		}
    		
    	//}
    	  
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }

	
    
}
