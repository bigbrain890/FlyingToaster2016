package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

public class TrajectoryTracking 
{
	private static DigitalInput pi;
	private static AnalogInput ultrasonic;
	private static TrajectoryTracking instance;
	
	private TrajectoryTracking()
	{
		pi = new DigitalInput(Constants.PI_COM_PIN);
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
	}

	public static boolean heading()
	{
		return pi.get();
	}
	
	public static float getRawUltrasonic()
	{
		return ultrasonic.getValue();
	}
	
	public static TrajectoryTracking getInstance()
	{
		if(instance == null)
		{
			instance = new TrajectoryTracking();
		}
		return instance;
	}
}
