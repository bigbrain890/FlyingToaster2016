package org.usfirst.frc.team3641.robot;

public class PILoop 
{
	private static PILoop instance;
	public static double errorRefresh = 0;
	public static double motorOutput;
	
	public PILoop()
	{
		
	}
	
	public static PILoop getInstance()
	{
		if(instance == null)
		{
			instance = new PILoop();
		}
		return instance;
	}
	
	public static double smoothDrive(double current, double target, boolean useIntegral)
	{
		double error = target - current;
		if(useIntegral == true)
		{
			errorRefresh = error + errorRefresh;
			motorOutput = (((error * Constants.DRIVE_KP)* -1) + (errorRefresh * Constants.DRIVE_KI)* -1);
		}
		else
		{
			motorOutput = ((error * Constants.DRIVE_KP)* -1); //Drive Rotation normally reversed.
		}
		return motorOutput;
	}
	
	public static double shooter(double current, double target, boolean useIntegral)
	{
		double error = target - current;
		if (useIntegral == true)
		{
			errorRefresh = error + errorRefresh;
			motorOutput = (((error * Constants.SHOOTER_KP) *-1) + (errorRefresh * Constants.SHOOTER_KI) * -1);
		}
		else
		{
			motorOutput = ((error * Constants.SHOOTER_KP) * -1);
		}
		return -motorOutput;
	}
	
	public static double intake(double current, double target, boolean useIntegral)
	{
		double error = target - current;
		if (useIntegral == true)
		{
			errorRefresh = error + errorRefresh;
			motorOutput = (((error * Constants.INTAKE_KP) * -1) + (errorRefresh * Constants.INTAKE_KI));
		}
		else
		{
			motorOutput = ((error * Constants.INTAKE_KP) * -1);
		}
		return motorOutput;
	}
	
	
	public void reset()
	{
		errorRefresh = 0;
	}
}
