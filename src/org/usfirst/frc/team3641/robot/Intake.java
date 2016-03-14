package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;

public class Intake {
	private static CANTalon intake, rollers;
	private static Intake instance;
	private static AnalogInput intakePot;
	
	private Intake()
	{
		rollers = new CANTalon(Constants.ROLLERS);
		intake = new CANTalon(Constants.INTAKE);
		intakePot = new AnalogInput(Constants.INTAKE_POT);
	}
	
	public static Intake getInstance()
	{
		if(instance == null)
		{
			instance = new Intake();
		}
		return instance;
	}
	
	public static void pullBall()
	{
		intake.set(PILoop.loop(intakePot.getVoltage(), Constants.INTAKE_DOWN, Constants.INTAKE_KP, Constants.INTAKE_KI));
		rollers.set(.75);
		
	}
	
	public static void stopIntake()
	{
		rollers.set(0.0);
	}
	
	public static void spitBall()
	{
		rollers.set(-.5);
	}
}
