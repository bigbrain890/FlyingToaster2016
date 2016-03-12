package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Shooter 
{
	private static Shooter instance;
	public static CANTalon flyWheel1, flyWheel2, shooter, shooterLever;
	public static AnalogInput shooterPot, camPot;
	public static DigitalInput leverLimSwitch;
	public static FeedbackDevice shooterEncoder, shooterLeverEncoder;
	
	public Shooter()
	{
		flyWheel1 = new CANTalon(Constants.FLY_WHEEL_1);
		flyWheel2 = new CANTalon(Constants.FLY_WHEEL_2);
		shooter = new CANTalon(Constants.SHOOTER);
		shooterPot = new AnalogInput(Constants.SHOOTER_POT);
		shooterLever = new CANTalon(Constants.SHOOTER_LEVER);
		leverLimSwitch = new DigitalInput(Constants.LEVER_LIM_SWITCH);
		
		shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		
		
	}
	
	public static Shooter getInstance()
	{
		if(instance == null)
		{
			instance = new Shooter();
		}
		return instance;
	}
		
	public static void spinUpWheels()
	{
		flyWheel1.set(1);
		flyWheel2.set(-1);
	}
	
	public static void intake()
	{
		shooter.set(PILoop.shooter(shooterPot.getVoltage(), Constants.SHOOTER_INTAKE, false));
		flyWheel1.set(-.5);
		flyWheel2.set(.5);
	}
	
	public static void manualControl(double joystick)
	{
		shooter.set(joystick);
	}
	
	public static void fire ()
	{
		shooterLever.set(-.3);
	}
	
	public static void resetShooterArm ()
	{
		shooterLever.set(.3);
	}
	
	public static void restShooterArm()
	{
		shooterLever.set(0.0);
	}
	
	public static void getShooterAngle()
	{
		SmartDashboard.putNumber("Shooter Angle", shooterPot.getVoltage());
	}
	
	public static void farShot()
	{
		shooter.set(PILoop.shooter(shooterPot.getVoltage(), Constants.FAR_SHOT, false));
	}
	
	public static void mediumShot()
	{
		shooter.set(PILoop.shooter(shooterPot.getVoltage(), Constants.MEDIUM_SHOT, false));
	}
	
	public static void closeShot()
	{
		shooter.set(PILoop.shooter(shooterPot.getVoltage(), Constants.CLOSE_SHOT, false));
	}
	
	public static void lowGoal()
	{
		flyWheel1.set(.6);
		flyWheel2.set(-.6);
	}
	
	public static void sensorReadout()
	{
	SmartDashboard.putNumber("Shooter Lever", shooterLever.getAnalogInPosition());
	SmartDashboard.putNumber("Shooter Angle", shooter.getAnalogInPosition());
	}
	
}
