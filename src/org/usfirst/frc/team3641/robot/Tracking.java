package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import java.lang.String;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tracking 
{
	private static AnalogInput ultrasonic;
	private static Tracking instance;
	private static int visionState = 0;
	private static int xcord = 0;
	private static double angleOff = 0;
	private static double target = 0;
	private static double heading = 0;
	private static boolean edge = false;
	private static double driveOutput = 0;
	public static double errorRefresh = 0;


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
		if (visionState == Constants.SEND_REQUEST)
		{
			heading = DriveBase.getDriveDirection();
			UDP.sendData("Request");
			visionState = Constants.RESPONSE_CAPTURE;
		}
		
		else if (visionState == Constants.RESPONSE_CAPTURE)
		{
			String response = UDP.flush(null);
			if (response != null)
			{
				xcord = Integer.parseInt(response);
				if(xcord != 0)
				{
					visionState = Constants.DO_MATH;
				}
			}
		}
		
		else if (visionState == Constants.DO_MATH)
		{
			angleOff = (xcord - Constants.CAMERA_LINE_UP) * Constants.DEGREES_PER_PIXEL;
			target = heading + angleOff;
			if(target<0) 
			{
				target = 360 + target;
				edge = true;
			}
			else if(target>=360)
			{
				target = target - 360;
				edge = true;
			}
			visionState = Constants.TURN_TO_TARGET;
		}
		
		else if (visionState == Constants.TURN_TO_TARGET)
		{
			/*
			double driveOutput = -1* PILoop.smoothDrive(DriveBase.getDriveDirection(), target, false);
			}
			*/
			double error = target - DriveBase.getDriveDirection();
			if (edge == true)
			{
				errorRefresh = error + errorRefresh;
				driveOutput = ((error * Constants.DRIVE_KP) /*+ (errorRefresh * Constants.DRIVE_KI)*/);
			}
			else
			{
				errorRefresh = error + errorRefresh;
				driveOutput = -1 * (((error * Constants.DRIVE_KP) + (errorRefresh * Constants.DRIVE_KI)));
			}
			if (Math.abs(driveOutput) > .75)
			{
				if (driveOutput < 0)
				{
					driveOutput = -.75;
				}
				
				else
				{
					driveOutput = .75;
				}
			}
			
			DriveBase.driveNormal(0.0, driveOutput);
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
		errorRefresh = 0;
		edge = false;

	}
	
	public static void printOut()
	{
		SmartDashboard.putNumber("Target", target);
		SmartDashboard.putNumber("Heading", heading);
		SmartDashboard.putNumber("Angle Off", angleOff);
		SmartDashboard.putNumber("Vision State", visionState);
		SmartDashboard.putNumber("X Cordinate", xcord);
		SmartDashboard.putBoolean("Edge", edge);
	}
}
