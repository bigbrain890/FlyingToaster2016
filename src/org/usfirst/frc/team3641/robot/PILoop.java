package org.usfirst.frc.team3641.robot;

public class PILoop 
{
	private static PILoop instance;
	public static double errorRefresh = 0;
	public static double motorOutput;
		
	public static PILoop getInstance()
	{
		if(instance == null)
		{
			instance = new PILoop();
		}
		return instance;
	}
	
	public static double loop(double current, double target, double KP, double KI)
	{
		double error = target - current;
		errorRefresh = error + errorRefresh;
		double output = (error * KP) + (errorRefresh * KI);
		return output;
	}
	public void reset()
	{
		errorRefresh = 0;
	}
}
