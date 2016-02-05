package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;

public class DriveBase
{
	private static DriveBase instance;
	public static CANTalon leftMotor1, leftMotor2, rightMotor1, rightMotor2;
	public static RobotDrive chassis;
	public static AHRS gyro;
	public static FeedbackDevice dankEncoder;
	
	public DriveBase()
	{
		leftMotor1 = new CANTalon(Constants.LEFT_MOTOR_1);
		leftMotor2 = new CANTalon(Constants.LEFT_MOTOR_2);
		rightMotor1 = new CANTalon(Constants.RIGHT_MOTOR_1);
		rightMotor2 = new CANTalon(Constants.RIGHT_MOTOR_2);
		gyro = new AHRS(SPI.Port.kMXP);
		chassis = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);

		leftMotor2.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
	}
	
	public static DriveBase getInstance()
	{
		if(instance == null)
		{
			instance = new DriveBase();
		}
		return instance;
	}
	
	public static void driveNormal(double throttle, double rotate)
	{
		chassis.arcadeDrive(throttle, rotate);
	}
	
	public static void driveReverse(double throttle, double rotate)
	{
		chassis.arcadeDrive(throttle * -1, rotate * -1);
	}
	
	public static double getDriveDis()
	{
		return leftMotor1.getAnalogInPosition() * Constants.DRIVE_ENCODER_MULTIPLIER;
	}
	
	public static double getDriveDirection()
	{
		return gyro.getAngle();
	}

	public static void resetGyro()
	{
		gyro.reset();
	}
	
	public static void resetEncoders()
	{
		rightMotor1.setAnalogPosition(0);
	}
	
	public static void resetDriveSensors()
	{
		gyro.reset();
		rightMotor1.setAnalogPosition(0);
	}
	
}