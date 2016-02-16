package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/*import java.io.*;
import java.net.Socket;*/
import java.lang.String;

public class Tracking 
{
	private static AnalogInput ultrasonic;
	private static Tracking instance;
	private static int visionState = 0;
	private static int xcord = 0;
	private static double angleOff = 0;
	private static double target = 0;
	private static double heading = 0;


	private Tracking()
	{
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
	}

	public static double getRawUltrasonic()
	{
		return ultrasonic.getVoltage();
	}
	
	public static double getDistance()
	{
		return getRawUltrasonic() / Constants.ULTRASONIC_CONVERSION_FACTOR;
	}
	
	public static Tracking getInstance()
	{
		if(instance == null)
		{
			instance = new Tracking();
		}
		return instance;
	}
	
	public static void autoTarget()
	{
		if (visionState == Constants.FIND_TARGET)
		{
			heading = DriveBase.getDriveDirection();
			UDP.sendData("Request");
			String response = UDP.getData();
			if (response != null)
			{
				xcord = Integer.parseInt(response);
				if(xcord != 0)
				{
					visionState++;
				}
			}
			
		}
		
		else if (visionState == Constants.DO_MATH)
		{
			angleOff = (xcord - Constants.CAMERA_LINE_UP) * Constants.DEGREES_PER_PIXEL;
			target = heading + angleOff;
			visionState++;
		}
		
		else if (visionState == Constants.TURN_TO_TARGET)
		{
			DriveBase.driveNormal(0.0, PILoop.smoothDrive(DriveBase.getDriveDirection(), target, false));
		}
		else 
		{
			DriveBase.driveNormal(0.0, 0.0);
		}
	}
	
	public static void resetVision()
	{
		visionState = 0;
		xcord = 0;
		angleOff = 0;
		target = 0;
		heading = 0;

	}
}
