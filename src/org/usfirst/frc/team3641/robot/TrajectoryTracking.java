package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SerialPort;

public class TrajectoryTracking 
{
	private static SerialPort piPipe;
	private static AnalogInput ultrasonic;
	private static TrajectoryTracking instance;
	
	private TrajectoryTracking()
	{
		piPipe = new SerialPort(Constants.TRANSFER_RATE, SerialPort.Port.kOnboard);
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
	}

	public static int heading()
	{
		byte incoming[] = piPipe.read(4);
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
