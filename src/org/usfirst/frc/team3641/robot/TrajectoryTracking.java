package org.usfirst.frc.team3641.robot;

public class TrajectoryTracking {
	private static TrajectoryTracking instance;
	
	public static TrajectoryTracking getInstance()
	{
		if(instance == null)
		{
			instance = new TrajectoryTracking();
		}
		return instance;
	}
}
