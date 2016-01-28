package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TrajectoryTracking 
{
	private static AnalogInput ultrasonic;
	private static TrajectoryTracking instance;
	private static Socket socket;
	private static BufferedReader pipe;
	
	private TrajectoryTracking()
	{
		try
		{
			socket = new Socket("10.36.41.41", 3641);
		}
		catch(Exception error)
		{
			error.printStackTrace();
		}
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
	}

	public static int heading()
	{
		return 4;
	}
	
	public static float getRawUltrasonic()
	{
		return ultrasonic.getValue();
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
