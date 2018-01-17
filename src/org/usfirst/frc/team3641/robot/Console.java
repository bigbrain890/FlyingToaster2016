package org.usfirst.frc.team3641.robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class Console
{
	private static Console instance;
	private static Timer uptime;
	private static DriverStation DS;
	
	public static Console getInstance()
	{
		if(instance == null) instance = new Console();
		return instance;
	}
	
	private Console()
	{
		uptime = new Timer();
		uptime.reset();
		uptime.start();
				
		DS = DriverStation.getInstance();
	}
	
	/**
	 * Print a message to the console with the current mode and uptime.
	 * 
	 * @param msg The message to print.
	 */
	public static void print(String msg)
	{
		String prefix = getPrefix();
		System.out.println(prefix + msg);
	}
		
	/**
	 * Print a warning message to the console with the current mode and uptime. Shows up even if prints are disabled.
	 * 
	 * @param msg The warning message to print.
	 */
	public static void printWarning(String msg)
	{
		print("WARNING: " + msg);
	}
	
	
	/**
	 * Print an error message to the console with the current mode and uptime. Shows up even if prints are disabled.
	 * 
	 * @param msg The error message to print.
	 */
	public static void printError(String msg)
	{
		print("ERROR: " + msg);
	}
		
	/**
	 * Gets the current mode and time stamp.
	 * 
	 * @return A string in the format: "[Mode] [Time] "
	 */
	private static String getPrefix()
	{
		String mode =                   "  [Teleop]";
		if(DS.isAutonomous()) mode =    "   [Auton]";
		else if(DS.isTest()) mode =     "    [Test]";
		else if(DS.isDisabled()) mode = "[Disabled]";
		
		String time = String.format("%.2f", uptime.get());
		time = " [" + time + "] ";
		
		return mode + time;
	}
	
	/**
	 * Reset the timer for prints.
	 */
	public static void restartTimer()
	{
		print("Restarting Match Timer");
		uptime.reset();
		uptime.start();
		print("Match Started");
	}
	
}
