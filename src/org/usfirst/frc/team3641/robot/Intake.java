package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
public class Intake {

	private static Intake instance;
	public static CANTalon rollers, rightIntake, leftIntake;
	public static DigitalInput limSwitch;
	public static AnalogInput leftPot, rightPot;
	public static DigitalInput ballSensor;
	
	public Intake()
	{
		rollers = new CANTalon(Constants.ROLLERS);
		leftIntake = new CANTalon(Constants.LEFT_INTAKE_MOTOR);
		rightIntake = new CANTalon(Constants.RIGHT_INTAKE_MOTOR);
		limSwitch = new DigitalInput(Constants.INTAKE_LIM_SWITCH);
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
	
	public static void intakeBall()
	{
		if(doesWeHasBall() == false)
			rollers.set(Preferences.getInstance().getDouble("Intake Speed", 1.0));
	}
	
	public static void stopIntake()
	{
		rollers.set(0.0);
	}
	
	public static void lowGoal()
	{
		rollers.set(-1);
	}
	
	public static void setDown()
	{

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
