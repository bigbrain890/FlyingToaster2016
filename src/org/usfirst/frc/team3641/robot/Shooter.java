package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Shooter 
{
	private static Shooter instance;
	public static CANTalon flyWheel1, flyWheel2, shooter, shooterLever;
	public static DigitalInput leverLimSwitch, ShooterLimSwitch;
	public static FeedbackDevice shooterEncoder, shooterLeverEncoder;
	public static int shooterLeverState = Constants.RESTING_POSITION;
	
	public Shooter()
	{
		flyWheel1 = new CANTalon(Constants.FLY_WHEEL_1);
		flyWheel2 = new CANTalon(Constants.FLY_WHEEL_2);
		shooter = new CANTalon(Constants.SHOOTER);
		shooterLever = new CANTalon(Constants.SHOOTER_LEVER);
		leverLimSwitch = new DigitalInput(Constants.LEVER_LIM_SWITCH);
		ShooterLimSwitch = new DigitalInput(Constants.SHOOTER_LIM_SWITCH);
		
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
		flyWheel1.set(-.4);
		flyWheel2.set(.4);
	}
	
	public static void manualControl(double joystick)
	{
		if(ShooterLimSwitch.get() && joystick<0)
		{
			shooter.set(0.0);
		}
		else
		{
			shooter.set(joystick);
		}
	}
	
	public static void fire()
	{
		shooterLever.set(-.35);
	}
	
	public static void fireLogic()
	{
		if (shooterLeverState == Constants.RESTING_POSITION)
		{
			restShooterArm();
		}
		else if (shooterLeverState == Constants.FIRE)
		{
			
			if (shooterLever.getEncPosition() >= Constants.LEVER_MAX_SWING)
			{
				SmartDashboard.putBoolean("Level Test", true);
				fire();
			}
			else
			{
				SmartDashboard.putBoolean("Level Test", false);
				resetShooterArm();
				shooterLeverState = Constants.RESET;
			}
		}
		else if (shooterLeverState == Constants.RESET)
		{
			if ((shooterLever.getEncPosition() <= 0) || (leverLimSwitch.get() == false))
			{
				resetShooterArm();
			}
			else
			{
				shooterLeverState = Constants.RESTING_POSITION;
			}
		}
	}
	
	public static void resetShooterArm()
	{
		shooterLever.set(0.0);
	}
	
	public static void restShooterArm()
	{
		shooterLever.set(0.0);
	}
		
	public static void farShot()
	{
		shooter.set(PILoop.loop((double)shooter.getEncPosition(), Constants.FAR_SHOT, Constants.SHOOTER_KP, Constants.SHOOTER_KI));
	}
	
	public static void mediumShot()
	{
		shooter.set(PILoop.loop(shooter.getEncPosition(), Constants.MEDIUM_SHOT, Constants.SHOOTER_KP, Constants.SHOOTER_KI));
	}
	
	public static void mediumShotTest()
	{
		double pidOut=PILoop.loop(shooter.getEncPosition(), Constants.MEDIUM_SHOT, Constants.SHOOTER_KP, Constants.SHOOTER_KI);
		shooter.set(pidOut);
		SmartDashboard.putNumber("PID Out", pidOut);
	}

	public static void closeShot()
	{
		shooter.set(PILoop.loop(shooter.getEncPosition(), Constants.CLOSE_SHOT, Constants.SHOOTER_KP, Constants.SHOOTER_KI));
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
		shooterLever.set(0.0);
	}
	
	public static void zeroShooterEnc()
	{
		shooter.setEncPosition(0);
		shooter.set(0.0);
	}

	public static void dropShooter()
	{
		if(!ShooterLimSwitch.get())
		{
			PILoop.loop(shooter.getEncPosition(), 0, Constants.SHOOTER_KP, Constants.SHOOTER_KI);
		}
		else
		{
			zeroShooterEnc();
		}
	}

}
