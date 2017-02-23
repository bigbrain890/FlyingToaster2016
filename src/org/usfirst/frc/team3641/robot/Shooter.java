package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

public class Shooter 
{
	private static AnalogInput ultrasonic;
	private static Shooter instance;
	public static CANTalon flyWheel1, flyWheel2, shooter, shooterLever;
	public static DigitalInput shooterLimitSwitch, shooterLeverLimitSwitch;
	public static FeedbackDevice shooterEncoder, shooterLeverEncoder;
	public static double leftError, leftErrorRefresh, rightError, rightErrorRefresh, leftOutput, rightOutput;
	static int targetAngle;
	static int counter = 1;
	static double [] ultraSonicVals ;
	
	private static boolean done;
	private static double shooterLeverInitalEncoderPosition = 0;
	private static boolean lastModeOn = false;
	private static Timer timer;
	private static double error = 0, output = 0, errorRefresh = 0;
	
	public Shooter()
	{
		ultrasonic = new AnalogInput(Constants.ULTRASONIC);
		flyWheel1 = new CANTalon(Constants.FLY_WHEEL_1);
		flyWheel2 = new CANTalon(Constants.FLY_WHEEL_2);
		shooter = new CANTalon(Constants.SHOOTER);
		shooterLever = new CANTalon(Constants.SHOOTER_LEVER);
		shooterLimitSwitch = new DigitalInput(Constants.SHOOTER_LIM_SWITCH);
		shooterLeverLimitSwitch = new DigitalInput(Constants.SHOOTER_LEVER_LIMIT_SWITCH);

		shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		shooterLever.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		
		timer = new Timer();
		
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
		flyWheel1.set(speed);
		flyWheel2.set(-speed);
	}
	
	public static void intake()
	{
		flyWheel1.set(.75);
		flyWheel2.set(-.75);
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
	
	public static void pullBackShooterArm()
	{
		shooterLever.set(.12);
	}
	
	public static void restShooterArm()
	{
		shooterLever.set(0.0);
	}
		
	public static void farShot()
	{
		shooter.set(PILoop.shooter(shooter.getEncPosition(), Constants.FAR_SHOT_COMP, false));
	}
	
	public static void mediumShot()
	{
		double pidOut=PILoop.shooter(shooter.getEncPosition(), Constants.MEDIUM_SHOT, false);
		shooter.set(pidOut);
		SmartDashboard.putNumber("PID Out", pidOut);
	}
	
	public static void closeShot()
	{
		int shooterPos = Shooter.shooter.getEncPosition();
		error = Constants.CLOSE_SHOT - shooterPos;
		errorRefresh = error + errorRefresh;
		if (errorRefresh > 25000)
		{
			errorRefresh = 25000;
		}
		else if (errorRefresh < -25000)
		{
			errorRefresh = -25000;
		}
	
		output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI) );
		if (output > .75)
		{
			output = .75;
		}
		else if (output < -.75)
		{
			output = -.75;
		}
		Shooter.shooter.set(output);
		if (Shooter.shooter.getEncPosition() < 2000)
		{
			Shooter.flyWheel1.set(.85);
			Shooter.flyWheel2.set(-.85);
		}
		else
		{
			Shooter.spinUpWheels(-.59);	
		}
	}
	
	public static void lowGoal()
	{
		flyWheel1.set(-1);
		flyWheel2.set(1);
	}
	
	public static void sensorReadout()
	{
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
	
	public static void set(double power)
	{
		flyWheel1.set(-power);
		flyWheel2.set(power);
	}
	
	public static void setShooterLever(boolean on)
	{
		if(on)
		{
			if(!lastModeOn)
			{
				timer.reset();
				timer.start();
				shooterLeverInitalEncoderPosition = shooterLever.getEncPosition();
				System.out.println("Starting to go");
				done = false;
			}
			shooterLever.set(-.1);
			if(Math.abs(shooterLeverInitalEncoderPosition - shooterLever.getEncPosition()) > 900 || timer.get() > 0.5) done = true;
			if(!done) shooterLever.set(-.35);
			else if(shooterLeverLimitSwitch.get()) shooterLever.set(0);
			else shooterLever.set(.35);
		}
		else
		{
			if(shooterLeverLimitSwitch.get()) shooterLever.set(0);
			else shooterLever.set(.1);
		}
		lastModeOn = on;
	}
}
