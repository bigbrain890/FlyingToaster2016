package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous
{
	private static Autonomous instance;
	private static Timer autoTimer;
	
	private Autonomous()
	{
		autoTimer = new Timer();
	}
	
	public static Autonomous getInstance()
	{
		if(instance == null)
		{
			instance = new Autonomous();
		}
		return instance;
	}
	
	public static void run(int mode)
	{
		switch (mode) 
		{
			case Constants.NOT_SO_COOL_AUTON:
				notSoCoolAuton();
				break;
			
			case Constants.REALLY_COOL_AUTON:
				reallyCoolAuton();
				break;
				
		}
	}
	
	public static void startTimer()
	{
		autoTimer.reset();
		autoTimer.start();
	}
	
	public static void reallyCoolAuton()
	{
		DriveBase.driveNormal(1, 0.0);
	}
	
	public static void notSoCoolAuton()
	{
		DriveBase.driveNormal(-1, 0.0);
	}
}
