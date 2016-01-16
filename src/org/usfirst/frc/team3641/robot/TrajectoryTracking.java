package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.DigitalInput;
public class TrajectoryTracking 
{
	private static DigitalInput pi;
	private static TrajectoryTracking instance;
	
	private TrajectoryTracking()
	{
		pi = new DigitalInput(Constants.PI_COM_PIN);
	}

	public static boolean heading()
	{
		return pi.get();
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
