package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;


public class DriveBase
{
	private static DriveBase instance;
	public static CANTalon leftMotor1, leftMotor2, rightMotor1, rightMotor2;
	public static CANTalon slaveLeft, slaveRight;
	public static RobotDrive chassis;
	public static AHRS gyro;
	public static Encoder driveEncoder;
	
	public DriveBase()
	{
		leftMotor1 = new CANTalon(Constants.LEFT_MOTOR_1);
		leftMotor2 = new CANTalon(Constants.LEFT_MOTOR_2);
		slaveLeft = new CANTalon(Constants.LEFT_MOTOR_3);
		rightMotor1 = new CANTalon(Constants.RIGHT_MOTOR_1);
		rightMotor2 = new CANTalon(Constants.RIGHT_MOTOR_2);
		slaveRight = new CANTalon(Constants.RIGHT_MOTOR_3);
		gyro = new AHRS(SPI.Port.kMXP);
		driveEncoder = new Encoder(Constants.DRIVE_ENC_PORT_1, Constants.DRIVE_ENC_PORT_2);
		chassis = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
		slaveRight.changeControlMode(TalonControlMode.Follower);
		slaveLeft.changeControlMode(TalonControlMode.Follower);
		slaveRight.set(Constants.RIGHT_MOTOR_1);
		slaveLeft.set(Constants.LEFT_MOTOR_1);
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
		chassis.arcadeDrive(-throttle, -rotate);
	}
	
	public static double getDriveDis()
	{
		return driveEncoder.getDistance();
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
		driveEncoder.reset();
	}
	
	public static void resetDriveSensors()
	{
		driveEncoder.reset();
		gyro.reset();
	}
	
}