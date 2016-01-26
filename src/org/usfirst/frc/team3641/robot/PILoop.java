package org.usfirst.frc.team3641.robot;


public class PILoop 
{
	private static PILoop instance;
	double motorOutput;
	
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
	
	public double smoothDrive(double current, double target, boolean useIntegral)
	{
		double error = target - current;
		if(useIntegral == true)
		{
			
		}
		else
		{
			motorOutput = ((error * Constants.DRIVE_KP)* -1);	//Drive Rotation normally reversed.
		}
		return motorOutput;
	}
}
