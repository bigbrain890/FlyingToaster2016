package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
public class Shooter 
{
	private static Shooter instance;
	public static CANTalon flyWheel1, flyWheel2, shooter, shooterLever;
	public static AnalogInput shooterPot, camPot;
	
	public Shooter()
	{
		flyWheel1 = new CANTalon(Constants.FLY_WHEEL_1);
		flyWheel2 = new CANTalon(Constants.FLY_WHEEL_2);
		shooter = new CANTalon(Constants.SHOOTER);
		shooterPot = new AnalogInput(Constants.SHOOTER_POT);
		shooterLever = new CANTalon(Constants.SHOOTER_LEVER);
		
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
		flyWheel1.set(-1);
		flyWheel2.set(1);
	}
	
	public static void intake()
	{
		shooter.set(PILoop.shooter(shooterPot.getVoltage(), Constants.SHOOTER_INTAKE, false));
		flyWheel1.set(.35);
		flyWheel2.set(-.35);
	}
	
	public static void manualControl(double joystick)
	{
		shooter.set(joystick);
	}
	
	public static void fire (double joystick)
	{
		shooterLever.set(joystick);
	}
	
	public static void resetShooterArm (double joystick)
	{
		shooterLever.set(-joystick);
	}
	
	
}
