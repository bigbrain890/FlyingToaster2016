package org.usfirst.frc.team3641.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
public class Intake {

	private static Intake instance;
	public static TalonSRX rollers, rightIntake, leftIntake;
	public static DigitalInput limSwitch;
	public static AnalogInput leftPot, rightPot;
	public static DigitalInput ballSensor;
	
	public Intake()
	{
		rollers = new TalonSRX(Constants.ROLLERS);
		leftIntake = new TalonSRX(Constants.LEFT_INTAKE_MOTOR);
		rightIntake = new TalonSRX(Constants.RIGHT_INTAKE_MOTOR);
		leftPot = new AnalogInput(Constants.LEFT_POT);
		rightPot = new AnalogInput(Constants.RIGHT_POT);
		ballSensor = new DigitalInput(Constants.BALL_SENSOR);
	}
	
	public static Intake getInstance()
	{
		if(instance == null)
		{
			instance = new Intake();
		}
		return instance;
	}
	
	public static void intakeBall(double speed)
	{
			rollers.set(ControlMode.PercentOutput, speed);
	}
	
	public static void stopIntake()
	{
		rollers.set(ControlMode.PercentOutput, 0.0);
	}
	
	public static void lowGoal()
	{
		rollers.set(ControlMode.PercentOutput, -1);
	}
	
	public static boolean doesWeHasBall()
	{
		if(ballSensor.get())
			return false;
		else
			return true;
	}
	
	public static void sensorReadOut()
	{
		SmartDashboard.putNumber("Left Pot", leftPot.getVoltage());
		SmartDashboard.putNumber("Right Pot", rightPot.getVoltage());
		SmartDashboard.putBoolean("Does We Has Ball?", doesWeHasBall());
	}
	
}
