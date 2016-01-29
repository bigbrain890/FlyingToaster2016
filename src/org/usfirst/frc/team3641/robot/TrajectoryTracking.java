package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.String;

public class TrajectoryTracking 
{
	private static AnalogInput ultrasonic;
	private static TrajectoryTracking instance;
	private static Socket socket;
	private static BufferedReader pipe;
	private static String read;
	private static int castedRead;
	
	
	private TrajectoryTracking()
	{
		try
		{
			socket = new Socket("10.36.41.41", 3641);
			pipe = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(IOException error)
		{
			error.printStackTrace();
		}
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
	}

	public static double heading()
	{
		try
		{
			read = pipe.readLine();
			castedRead = Integer.parseInt(read);
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		return castedRead;
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
