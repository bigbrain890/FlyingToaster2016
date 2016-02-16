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
		int xcord = 0;
		double angleOff = 0;
		double target = 0;
		double heading = 0;
		if (visionState == Constants.FIND_TARGET)
		{
			heading = DriveBase.getDriveDirection();
			UDP.sendData("Request");
			String response = UDP.getData();
			if (response != null)
			{
				xcord = Integer.parseInt(response);
				visionState++;
			}
			
		}
		
		else if (visionState == Constants.DO_MATH)
		{
			if (xcord != 0)
			{
				angleOff = (xcord - Constants.CAMERA_LINE_UP) * Constants.DEGREES_PER_PIXEL;
				target = heading + angleOff;
				visionState++;
			}
			else
			{
				visionState--;
			}
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
	}
}
