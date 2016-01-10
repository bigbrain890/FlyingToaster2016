package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous
{
	private static Autonomous instance;
	private static int mode = 0;
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
	public static void run()
	{
		switch (Autonomous.getAutoMode()) 
		{
			case 0:
				break;
			
			case 42:
				reallyCoolAuton();
				break;
				
		}
	}
	public static void setAutoMode(int mode)
	{
		Autonomous.mode = mode;
	}
	
	public static int getAutoMode()
	{
		return Autonomous.mode;
	}
	
	public static void startTimer()
	{
		autoTimer.reset();
		autoTimer.start();
	}
	
	public static void reallyCoolAuton()
	{
		if (autoTimer.get() < 2)
		{
			DriveBase.driveNormal(.3, 0.0);
		}
	}
}
