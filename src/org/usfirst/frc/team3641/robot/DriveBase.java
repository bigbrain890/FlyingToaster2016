package org.usfirst.frc.team3641.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;

public class DriveBase
{
	private static DriveBase instance;
	public static CANTalon leftMotor1, leftMotor2, rightMotor1, rightMotor2;
	public static RobotDrive chassis;
	public static AHRS gyro;
	
	public DriveBase()
	{
		leftMotor1 = new CANTalon(Constants.LEFT_MOTOR_1);
		leftMotor2 = new CANTalon(Constants.LEFT_MOTOR_2);
		rightMotor1 = new CANTalon(Constants.RIGHT_MOTOR_1);
		rightMotor2 = new CANTalon(Constants.RIGHT_MOTOR_2);
		gyro = new AHRS(SPI.Port.kMXP);
		chassis = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);

		rightMotor1.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
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
		chassis.arcadeDrive(throttle * -1, rotate);
	}
	
	public static void driveTank(double left, double right)
	{
		chassis.tankDrive(-left, -right);
	}
	
	public static double getDriveDis()
	{
		return rightMotor1.getAnalogInPosition() * Constants.DRIVE_ENCODER_MULTIPLIER;
	}
	
	public static double getDriveDirection()
	{
		return gyro.getAngle();
	}
	
	public static double getDriveSpeed()
	{
		return rightMotor1.getAnalogInVelocity() * Constants.DRIVE_ENCODER_MULTIPLIER;
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
	
	public static void driveStraight(double target_angle, double drive_speed)
	{
		double error = (target_angle) - (getDriveDirection());
		if (error >= 180)
		{
			error -= 360;
		}
		else if (error<=-180)
		{
			error+=360;
		}
		driveNormal(-drive_speed, -1 * error * Constants.DRIVE_KP);
	}
	
}