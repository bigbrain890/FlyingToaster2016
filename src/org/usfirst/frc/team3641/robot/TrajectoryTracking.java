package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.I2C;

public class TrajectoryTracking 
{
	private static TrajectoryTracking instance;
	public static I2C dataPipe;
	
	private TrajectoryTracking()
	{
		dataPipe = new I2C(I2C.Port.kOnboard, 0x1E);
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
