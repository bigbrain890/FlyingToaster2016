package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.AnalogInput;
public class Shooter 
{
	private static Shooter instance;
	public static CANTalon intake, flyWheel1, flyWheel2;
	public static DoubleSolenoid puncher;
	public static AnalogInput shooterPot;
	
	public Shooter()
	{
		intake = new CANTalon(Constants.INTAKE_MOTOR);
		flyWheel1 = new CANTalon(Constants.FLY_WHEEL_1);
		flyWheel2 = new CANTalon(Constants.FLY_WHEEL_2);
		puncher = new DoubleSolenoid(Constants.PUNCHER_FORWARD, Constants.PUNCHER_REVERSE);
		shooterPot = new AnalogInput(Constants.SHOOTER_POT);
		
	}
	
	public static Shooter getInstance()
	{
		if(instance == null)
		{
			instance = new Shooter();
		}
		return instance;
	}
	
	public static void feedToShooter(double power)
	{
		intake.set(power);
	}
	
	public static void fire()
	{
		puncher.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void retract()
	{
		puncher.set(DoubleSolenoid.Value.kReverse);
	}
	
	public static void aim()
	{
		flyWheel1.set(1);
		flyWheel2.set(1);
	}
}
