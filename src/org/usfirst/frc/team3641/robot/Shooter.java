package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

public class Shooter 
{
	private static AnalogInput ultrasonic;
	private static Shooter instance;
	public static TalonSRX flyWheel1, flyWheel2, shooter, shooterLever;
	public static DigitalInput shooterLimitSwitch, shooterLeverLimitSwitch;
	public static FeedbackDevice shooterEncoder, shooterLeverEncoder;
	public static double leftError, leftErrorRefresh, rightError, rightErrorRefresh, leftOutput, rightOutput;
	static int targetAngle;
	static int counter = 1;
	static double [] ultraSonicVals;
	
	private static PID aimPID;
	
	private static boolean done;
	private static double shooterLeverInitalEncoderPosition = 0;
	private static boolean lastModeOn = false;
	private static Timer timer;
	private static double error = 0, output = 0, errorRefresh = 0;
	
	public Shooter()
	{
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
		flyWheel1 = new TalonSRX(Constants.FLY_WHEEL_1);
		flyWheel2 = new TalonSRX(Constants.FLY_WHEEL_2);
		shooter = new TalonSRX(Constants.SHOOTER);
		shooterLever = new TalonSRX(Constants.SHOOTER_LEVER);
		shooterLimitSwitch = new DigitalInput(Constants.SHOOTER_LIM_SWITCH);
		shooterLeverLimitSwitch = new DigitalInput(Constants.SHOOTER_LEVER_LIMIT_SWITCH);

		shooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		shooterLever.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		
		timer = new Timer();
		aimPID = new PID("Shooter Aim");
		aimPID.setValues(Constants.SHOOTER_KP, Constants.SHOOTER_KI, 0.0, 0);
		aimPID.setICap(25000);
	}
	
	public static Shooter getInstance()
	{
		if(instance == null)
		{
			instance = new Shooter();
		}
		return instance;
	}
	
	public static int autoAngle()
	{
		double averageDis = 0;
		if (counter == 1)
		{
			for (int i = 0; i <=2; i++)
			{
				ultraSonicVals[i] = ultrasonic.getVoltage();
			}
			counter++;
		}
		else if (counter == 2)
		{
			averageDis = (ultraSonicVals[0] + ultraSonicVals[1] + ultraSonicVals[2]) / 3;
			counter++;
		}
		else if (counter == 3)
		{
			double disToBase = averageDis * Constants.COSINE_30;
		}
		return targetAngle;
	}
		
	public static void spinUpWheels(double speed)
	{
		flyWheel1.set(ControlMode.PercentOutput, speed);
		flyWheel2.set(ControlMode.PercentOutput, -speed);
	}
	
	public static void intake()
	{
		flyWheel1.set(ControlMode.PercentOutput, .75);
		flyWheel2.set(ControlMode.PercentOutput, -.75);
	}
	
	public static void manualControl(double joystick)
	{
		shooter.set(ControlMode.PercentOutput, joystick);
	}
	
	public static void fire ()
	{
		shooterLever.set(ControlMode.PercentOutput, -.35);
	}
	
	public static void resetShooterArm ()
	{
		shooterLever.set(ControlMode.PercentOutput, .3);
	}
	
	public static void pullBackShooterArm()
	{
		shooterLever.set(ControlMode.PercentOutput, .12);
	}
	
	public static void restShooterArm()
	{
		shooterLever.set(ControlMode.PercentOutput, 0.0);
	}
		
	public static void farShot()
	{
		double error = Constants.CASTLE_WALL_SHOT - shooter.getSelectedSensorPosition(0);
		shooter.set(ControlMode.PercentOutput, aimPID.run(error, Constants.CLOSE_SHOT));
		if (Math.abs(error) > 200) spinUpWheels(.85);
		else spinUpWheels(-1);
	}
	
	public static void closeShot()
	{
		double error = Constants.FAR_SHOT_COMP - shooter.getSelectedSensorPosition(0);
		shooter.set(ControlMode.PercentOutput, aimPID.run(error, Constants.FAR_SHOT_COMP));
		if (Math.abs(error) > 200) spinUpWheels(.85);
		else spinUpWheels(-1);
	}
	
	public static void lowGoal()
	{
		flyWheel1.set(ControlMode.PercentOutput, -1);
		flyWheel2.set(ControlMode.PercentOutput, 1);
	}
	
	public static void sensorReadout()
	{
		SmartDashboard.putNumber("Shooter Angle", shooter.getSelectedSensorPosition(0));
	}
	
	public static void zeroShooterLeverEnc()
	{
		shooterLever.setSelectedSensorPosition(0, 0, 0);
	}
	
	public static void zeroShooterEnc()
	{
		shooter.setSelectedSensorPosition(0, 0, 0);
	}
	
	public static void set(double power)
	{
		flyWheel1.set(ControlMode.PercentOutput, -power);
		flyWheel2.set(ControlMode.PercentOutput, power);
	}
	
	public static void setShooterLever(boolean on)
	{
		if(on)
		{
			if(!lastModeOn)
			{
				timer.reset();
				timer.start();
				shooterLeverInitalEncoderPosition = shooterLever.getSelectedSensorPosition(0);
				System.out.println("Starting to go");
				done = false;
			}
			shooterLever.set(ControlMode.PercentOutput, -.1);
			if(Math.abs(shooterLeverInitalEncoderPosition - shooterLever.getSelectedSensorPosition(0)) > 900 || timer.get() > 0.5) done = true;
			if(!done) shooterLever.set(ControlMode.PercentOutput, -.35);
			else if(shooterLeverLimitSwitch.get()) shooterLever.set(ControlMode.PercentOutput, 0);
			else shooterLever.set(ControlMode.PercentOutput, 1);
		}
		else
		{
			if(shooterLeverLimitSwitch.get()) shooterLever.set(ControlMode.PercentOutput, 0);
			else shooterLever.set(ControlMode.PercentOutput, .15);
		}
		lastModeOn = on;
	}
}
