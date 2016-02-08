package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class Intake {
	private static CANTalon intake, rollers;
	private static Intake instance;
	
	private Intake()
	{
		rollers = new CANTalon(Constants.ROLLERS);
		intake = new CANTalon(Constants.INTAKE);
	}
	
	public static Intake getInstance()
	{
		if(instance == null)
		{
			instance = new Intake();
		}
		return instance;
	}
}
