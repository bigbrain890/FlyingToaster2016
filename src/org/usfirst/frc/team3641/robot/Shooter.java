package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
public class Shooter 
{
	private static Shooter instance;
	public static CANTalon flyWheel1, flyWheel2, shooter, cam;
	public static AnalogInput shooterPot;
	
	public Shooter()
	{
		flyWheel1 = new CANTalon(Constants.FLY_WHEEL_1);
		flyWheel2 = new CANTalon(Constants.FLY_WHEEL_2);
		shooter = new CANTalon(Constants.SHOOTER);
		cam = new CANTalon(Constants.CAM);
		shooterPot = new AnalogInput(Constants.SHOOTER_POT);
		
	}
	
	public static Shooter getInstance()
	{
		if(instance == null)
		{
			instance = new Shooter();
		}
		return instance;
	}
		
	public static void spinUpWheels()
	{
		flyWheel1.set(1);
		flyWheel2.set(1);
	}
	
	public static void hold()
	{
		
	}
	
	public static void fire()
	{
		
	}
	
	public static void anticipateBall()
	{
		
	}
}
