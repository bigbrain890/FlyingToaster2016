package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
public class Intake {

	private static Intake instance;
	public static CANTalon rollers;
	
	public Intake()
	{
		rollers = new CANTalon(Constants.ROLLERS);
	}
	
	public static Intake getInstance()
	{
		if(instance == null)
		{
			instance = new Intake();
		}
		return instance;
	}
	
	public static void intakeBall()
	{
		rollers.set(1);
	}
	
	public static void stopIntake()
	{
		rollers.set(0.0);
	}
	
	public static void lowGoal()
	{
		rollers.set(-1);
	}
}
