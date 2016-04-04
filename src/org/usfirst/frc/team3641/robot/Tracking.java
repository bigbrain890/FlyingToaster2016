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
	private static double driveOutput = 0;
	public static double errorRefresh = 0;
	private static int packetCount = 0;
	private static int checksum = packetCount;


	private Tracking()
	{
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
	}

	public static double getRawUltrasonic()
	{
		return ultrasonic.getVoltage();
	}
		
	public static Tracking getInstance()
	{
		if(instance == null)
		{
			instance = new Tracking();
		}
		return instance;
	}
	
	public static boolean autoTarget()
	{
		if (visionState == Constants.SEND_REQUEST)
		{
			heading = DriveBase.getDriveDirection();
			UDP.sendData("Request");
			visionState = Constants.RESPONSE_CAPTURE;
			SmartDashboard.putBoolean("TRACKED", false);
		}
		
		else if (visionState == Constants.RESPONSE_CAPTURE)
		{
			String response = UDP.flush(null); 
			if (response != null)
			{
				SmartDashboard.putString("Response", response);
				if(response.contains(";"))
				{
					String data[] = response.split(";");
					xcord = Integer.parseInt(data[0]);
					checksum = Integer.parseInt(data[data.length-1]);
					if(checksum != packetCount)
					{
						//robot.die("I have failed to keep UDP Data in sync.");
						visionState=Constants.RESPONSE_CAPTURE;
						xcord = 0;
					}
				}
				else
				{
					xcord = Integer.parseInt(response); //Compatibility in case we decide not to send multiple values.
				}
				if(xcord != 0)
				{
					visionState = Constants.DO_MATH;
				}

			}
		}
		
		else if (visionState == Constants.DO_MATH)
		{
			angleOff = (xcord - Constants.CAMERA_LINE_UP_PRACTICE) * Constants.DEGREES_PER_PIXEL;
			target = heading + angleOff;
			double direction = DriveBase.getDriveDirection();
			if(target<0) 
			{
				target += 360;
			}
			else if(target>=360)
			{
				target -= 360;
			}
						
			if(Math.abs(target - direction) < 1)
			{
				DriveBase.driveNormal(0.0, 0.0);
				return true;
			}
			else
			{
				visionState = Constants.TURN_TO_TARGET;
			}

		}
		
		else if(visionState == Constants.WAIT_FOR_STILL)
		{
			if(Math.abs(DriveBase.gyro.getRate()) > Constants.MOTION_THRESHOLD)
			{
				SmartDashboard.putBoolean("TRACKED", true);
				DriveBase.driveNormal(0.0, 0.0);
			}
			else
			{
				visionState = Constants.SEND_REQUEST;
			}
			
		}
		
		else if (visionState == Constants.TURN_TO_TARGET)
		{
			/*
			double driveOutput = -1* PILoop.smoothDrive(DriveBase.getDriveDirection(), target, false);
			}
			*/
			double ActualCurrentHeading = DriveBase.getDriveDirection();
			double error = target - ActualCurrentHeading;
			if(error>=180)
			{
				error -= 360;
			}
			else if(error<=-180)
			{
				error += 360;
			}
			if(error>=2 || error <=1 || true)
			{
				if (errorRefresh > Constants.KI_UPPER_LIMIT)
				{
					errorRefresh = Constants.KI_UPPER_LIMIT;
				}
				else if (errorRefresh < Constants.KI_LOWER_LIMIT)
				{
					errorRefresh = Constants.KI_LOWER_LIMIT;
				}
				errorRefresh += error;
				driveOutput = -1 * (((error * Constants.DRIVE_KP) + (errorRefresh * Constants.DRIVE_KI)));
				if (driveOutput > 0)
				{
					driveOutput+= .5;
				}
				else
				{
					driveOutput-= .5;
				}
				if (Math.abs(driveOutput) > .7)
				{
					if (driveOutput < 0)
					{
						driveOutput = -.7;
					}
					
					else
					{
						driveOutput = .7;
					}
				}
				
				DriveBase.driveNormal(0.0, driveOutput);
			}
			
			if(Math.abs(error) < 1)
			{
				DriveBase.driveNormal(0.0, 0.0);
				visionState = Constants.WAIT_FOR_STILL;
			}

		}
		else 
		{
			DriveBase.driveNormal(0.0, 0.0);
		}
		return false;
		
	}
	
	public static void resetVision()
	{
		visionState = 0;
		xcord = 0;
		angleOff = 0;
		target = 0;
		heading = 0;
		errorRefresh = 0;
		SmartDashboard.putBoolean("TRACKED", false);

	}
	
	public static void printOut()
	{
		SmartDashboard.putNumber("Angle Off", angleOff);
		SmartDashboard.putNumber("Vision State", visionState);
		SmartDashboard.putNumber("Target", target);
		SmartDashboard.putNumber("X Cordinate", xcord);

	}
}
