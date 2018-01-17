package org.usfirst.frc.team3641.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class DriveBase
{
	private static DriveBase instance;
	public static TalonSRX leftMotor1, leftMotor2, rightMotor1, rightMotor2;
	public static AHRS gyro;
	
	public DriveBase()
	{
		leftMotor1 = new TalonSRX(Constants.LEFT_MOTOR_1);
		leftMotor2 = new TalonSRX(Constants.LEFT_MOTOR_2);
		rightMotor1 = new TalonSRX(Constants.RIGHT_MOTOR_1);
		rightMotor2 = new TalonSRX(Constants.RIGHT_MOTOR_2);
		gyro = new AHRS(SPI.Port.kMXP);

		rightMotor1.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
	}
	
	public static DriveBase getInstance()
	{
		if(instance == null)
		{
			instance = new DriveBase();
		}
		return instance;
	}
	
	public static double squareInput(double input, double power)
	{
		return (input < 0) ? -Math.pow(Math.abs(input), power) : Math.pow(input, power); 
	}

	public static void driveGrilledCheese(double power, double rotation)
	{

		double gain = 1.5;
		double limit = 0.1;
				
		double arcadePower = power;
		double arcadeRotation = rotation;
		double cheesyRotation = rotation * gain * Math.abs(arcadePower);
		
		power = Math.abs(power);
		if(power == 0) rotation = arcadeRotation;
		else if(power <= limit) rotation = (power/limit)*cheesyRotation + (1-power/limit) * arcadeRotation;
		else rotation = cheesyRotation;
		
		driveArcade(arcadePower, rotation);
	}
	
	public static void driveArcade(double throttle, double rotation)
	{
		double left = throttle + rotation;
		double right = throttle - rotation;
		
		driveTank(left, right);
	}
	
	public static void driveTank(double left, double right)
	{
		double maxPower;
		double al = Math.abs(left);
		double ar = Math.abs(right);
		if(al > ar) maxPower = al;
		else maxPower = ar;

		if(maxPower > 1)
		{
			left/= maxPower;
			right/= maxPower;
		}
		left = -left;
		
		leftMotor1.set(ControlMode.PercentOutput, left);
		leftMotor2.set(ControlMode.PercentOutput, left);
		rightMotor1.set(ControlMode.PercentOutput, right);
		rightMotor2.set(ControlMode.PercentOutput, right);
	}
	
	public static double getDriveDis()
	{
		return rightMotor1.getSelectedSensorPosition(0) * Constants.DRIVE_ENCODER_MULTIPLIER;
	}
	
	public static double getDriveDirection()
	{
		return gyro.getAngle();
	}
	
	public static double getDriveSpeed()
	{
		return rightMotor1.getSelectedSensorVelocity(0) * Constants.DRIVE_ENCODER_MULTIPLIER;
	}

	public static void resetGyro()
	{
		gyro.reset();
	}
	
	public static void resetEncoders()
	{
		rightMotor1.setSelectedSensorPosition(0,0,0);
	}
	
	public static void resetDriveSensors()
	{
		gyro.reset();
		rightMotor1.setSelectedSensorPosition(0,0,0);
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
		driveArcade(-drive_speed, -1 * error * Constants.DRIVE_KP);
	}
	
}