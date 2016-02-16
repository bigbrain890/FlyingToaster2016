package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/*import java.io.*;
import java.net.Socket;*/
import java.lang.String;

public class Tracking 
{
	private static AnalogInput ultrasonic;
	private static Tracking instance;

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
		UDP.sendData("Request");
		//String[] response = UDP.getData().split(",");
		String response = UDP.getData();
		if(response/*[0]*/ != null && !response.equals("0"))
		{
			double xcord = Integer.parseInt(response);
			DriveBase.driveNormal(0.0, -1* PILoop.smoothDrive(xcord, Constants.CAMERA_LINE_UP, false));		
		}/*
		if(response[1] != null && !response[1].equals("0"))
		{
			double ycord = Integer.parseInt(response[1]);
			//Move shooter arm according to the y var
		}*/

		else 
		{
			DriveBase.driveNormal(0.0, 0.0);
		}
	}
}
