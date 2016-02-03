package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
public class Shooter 
{
	private static Shooter instance;
	public static CANTalon intake;
	
	public Shooter()
	{
		intake = new CANTalon(Constants.INTAKE_MOTOR);
	}
	
	public static Shooter getInstance()
	{
		if(instance == null)
		{
			instance = new Shooter();
		}
		return instance;
	}
	
	public static void feedToShooter(double power)
	{
		intake.set(power);
	}
}
