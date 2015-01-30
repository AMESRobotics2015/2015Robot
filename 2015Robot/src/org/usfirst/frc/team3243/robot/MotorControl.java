package org.usfirst.frc.team3243.robot;

import edu.wpi.first.wpilibj.*;
import java.lang.System;
public class MotorControl {
	
	protected static Victor A;
	protected static Victor B;
	protected static Victor C;
	protected static Victor D;
	
	static double[] drv= new double[4];
	
	public MotorControl(){
		
		A = new Victor(0);
		B = new Victor(1);
		C = new Victor(2);
		D = new Victor(3);
		
	}
	
	public void driveomni(double[] driv){
		
		finaldrv(driv);
		
			A.set(drv[0]);
			B.set(drv[1]);
			C.set(drv[2]);
			D.set(drv[3]);
		
	}
	
	public double[] finaldrv(double[] driv){
		/*
		drv[0] = (driv[0] * .5) - (driv[1] * .5) + (driv[2] * .6);
		drv[1] = (driv[0] * .5) + driv[1] * .5 + (driv[2] * .6);
		drv[2] = -(driv[0] * .5) + (driv[1] * .5) + (driv[2] * .6);
		drv[3] = -(driv[0] * .5) - (driv[1] * .5) + (driv[2] * .6);
		*/
		/*
		drv[0] = (driv[2]);
		drv[1] = (driv[2]);
		drv[2] = (driv[2]);
		drv[3] = (driv[2]);
		*/
		
		drv[0] = (driv[0] * .75) - (driv[1] * .75) + (driv[2]);
		System.out.println("A: " + drv[0]);
		drv[1] = ((driv[0] * .75) + driv[1] * .75 + (driv[2])) * .96;
		System.out.println("B: " + drv[1]);
		drv[2] = -(driv[0] * .75) + (driv[1] * .75) + (driv[2]);
		System.out.println("C: " + drv[2]);
		drv[3] = (-(driv[0] * .75) - (driv[1] * .75) + (driv[2])) * .96;
		System.out.println("D: " + drv[3]);
		return drv;
	}
}
