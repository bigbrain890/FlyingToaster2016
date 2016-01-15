package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
public class TrajectoryTracking 
{
	private static SerialPort input;
	private static TrajectoryTracking instance;
	
	private TrajectoryTracking()
	{
		input = new SerialPort(9600, SerialPort.Port.kUSB);
	}

	private static int getX()
	{
		return Integer.parseInt(input.readString().substring(1));
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
