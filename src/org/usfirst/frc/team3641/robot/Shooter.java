package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Shooter 
{
	private static Shooter instance;
	public static CANTalon flyWheel1, flyWheel2, shooter, shooterLever;
	public static AnalogInput shooterPot, camPot;
	public static Servo shooterServo;
	
	public Shooter()
	{
		flyWheel1 = new CANTalon(Constants.FLY_WHEEL_1);
		flyWheel2 = new CANTalon(Constants.FLY_WHEEL_2);
		shooter = new CANTalon(Constants.SHOOTER);
		shooterPot = new AnalogInput(Constants.SHOOTER_POT);
		shooterLever = new CANTalon(Constants.SHOOTER_LEVER);
		shooterServo = new Servo(Constants.SHOOTER_SERVO_PORT);
		
		
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
	
	public static void fire (double joystick)
	{
		shooterLever.set(-joystick);
	}
	
	public static void resetShooterArm (double joystick)
	{
		shooterLever.set(joystick);
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
	
}
