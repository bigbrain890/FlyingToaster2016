package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Shooter 
{
	private static Shooter instance;
	public static CANTalon flyWheel1, flyWheel2, shooter, shooterLever;
	public static DigitalInput leverLimSwitch, shooterLimitSwitch;
	public static FeedbackDevice shooterEncoder, shooterLeverEncoder;
	
	public Shooter()
	{
		flyWheel1 = new CANTalon(Constants.FLY_WHEEL_1);
		flyWheel2 = new CANTalon(Constants.FLY_WHEEL_2);
		shooter = new CANTalon(Constants.SHOOTER);
		shooterLever = new CANTalon(Constants.SHOOTER_LEVER);
		leverLimSwitch = new DigitalInput(Constants.LEVER_LIM_SWITCH);
		shooterLimitSwitch = new DigitalInput(Constants.SHOOTER_LIM_SWITCH);
		
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
		
	public static void spinUpWheels(double speed)
	{
		flyWheel1.set(speed);
		flyWheel2.set(-speed);
	}
	
	public static void intake()
	{
		flyWheel1.set(-.4);
		flyWheel2.set(.4);
	}
	
	public static void manualControl(double joystick)
	{
		shooter.set(joystick);
	}
	
	public static void fire ()
	{
		shooterLever.set(-.35);
	}
	
	public static void resetShooterArm ()
	{
		shooterLever.set(.3);
	}
	
	public static void restShooterArm()
	{
		shooterLever.set(0.0);
	}
		
	public static void farShot()
	{
		shooter.set(PILoop.shooter(shooter.getEncPosition(), Constants.FAR_SHOT, false));
	}
	
	public static void mediumShot()
	{
		double pidOut=PILoop.shooter(shooter.getEncPosition(), Constants.MEDIUM_SHOT, false);
		shooter.set(pidOut);
		SmartDashboard.putNumber("PID Out", pidOut);
	}
	
	public static void closeShot()
	{
		shooter.set(PILoop.shooter(shooter.getEncPosition(), Constants.CLOSE_SHOT, false));
	}
	
	public static void lowGoal()
	{
		flyWheel1.set(.6);
		flyWheel2.set(-.6);
	}
	
	public static void sensorReadout()
	{
		SmartDashboard.putNumber("Shooter Lever", shooterLever.getEncPosition());
		SmartDashboard.putNumber("Shooter Angle", shooter.getEncPosition());
	}
	
	public static void zeroShooterLeverEnc()
	{
		shooterLever.setEncPosition(0);
	}
	
	public static void zeroShooterEnc()
	{
		shooter.setEncPosition(0);
	}
}
