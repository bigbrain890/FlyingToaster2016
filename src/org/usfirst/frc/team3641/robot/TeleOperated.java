package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class TeleOperated
{
	private static TeleOperated instance;
	public static PS4Controller dualShock;
	public static Extreme3DPro operator;
	public static Harmonix guitar;
	public static boolean driveMode = Constants.DRIVE_NORMAL;
	public static int driveBack = Constants.UNPRESSED;
	public static double leftError = 0, rightError = 0, leftOutput = 0, rightOutput = 0;
	public static int intakeState = Constants.INTAKE_DOWN;
	public static int cruiseState = Constants.CRUISE_OFF;
	private static double errorRefresh = 0;
	private static double error = 0;
	private static double output = 0;
	
	private static boolean alreadyPressed = false;
	private static Timer timer;
	private static PowerDistributionPanel pdp;
	

	private TeleOperated()
	{
		dualShock = new PS4Controller(Constants.PS4_CONTROLLER);
		operator = new Extreme3DPro(Constants.E3DPro);
		guitar = new Harmonix(Constants.E3DPro + 1);
		timer = new Timer();
		pdp = new PowerDistributionPanel();
	}
	
	public static TeleOperated getInstance()
	{
		if(instance == null)
		{
			instance = new TeleOperated();
		}
		return instance;
	}
	
	public static void runBoth()
	{
		if(operator.getButton(7)) runGuitar();
		else runDriver();
	}
	
	public static void runGuitar()
	{
		guitar.poll();
		if(guitar.isDown(Harmonix.Button.LOWER))
		{
			DriveBase.driveArcade(0, 0);
			if(guitar.isDown(Harmonix.Button.YELLOW)) Shooter.set(-.1);
			else if(guitar.isDown(Harmonix.Button.GREEN)) Shooter.farShot();
			else if(guitar.isDown(Harmonix.Button.RED)) Shooter.closeShot();
			
			Shooter.setShooterLever(guitar.isDown(Harmonix.Button.STRUM));
		}
		else
		{
			Shooter.setShooterLever(guitar.isDown(Harmonix.Button.LOWER) && guitar.isDown(Harmonix.Button.STRUM));
			double speed = guitar.getAxis(Harmonix.Axis.WHAMMY_BAR) * guitar.getAxis(Harmonix.Axis.STRUM);
			double rotation = guitar.getAxis(Harmonix.Axis.BUTTONS);
			DriveBase.driveGrilledCheese(speed, rotation);
		}
	}
	
	public static void runDataLogger()
	{
		DriveBase.driveGrilledCheese(dualShock.getLeftStickYAxis(), -1* dualShock.getRightStickXAxis());
		if(dualShock.getSquareButton())
		{
			if(!alreadyPressed)
			{
				timer.start();
				DriveBase.resetEncoders();
				alreadyPressed = true;
				UDP.sendData("\nTime, Distance, Voltage, Total Current, Right Current 1, Right Current 2, Left Current 1, Left Current 2");
			}
			double time = timer.get();
			double distance = DriveBase.getDriveDis();
			double voltage = pdp.getVoltage();
			double totalCurrent = pdp.getTotalCurrent();
			double rc1 = pdp.getCurrent(0);
			double rc2 = pdp.getCurrent(1);
			double lc1 = pdp.getCurrent(14);
			double lc2 = pdp.getCurrent(15);
			
			UDP.sendData(time + ", " + distance + ", " + voltage + ", " + totalCurrent + ", " + rc1 + ", " + rc2 + ", " + lc1 + ", " + lc2  );
		}
		else if(alreadyPressed) alreadyPressed = false;
	}
	
	
	public static void runDriver()
	{
		Shooter.setShooterLever((operator.getButton(1) == true) || (dualShock.getRawButton(14) == true));
		// Actually driving and stuff
		if ((driveMode == Constants.DRIVE_NORMAL) && (dualShock.getSquareButton() != true))
		{
			DriveBase.driveGrilledCheese(dualShock.getLeftStickYAxis(), -1* dualShock.getRightStickXAxis());
			if(dualShock.getRightDPad() == true)
			{
				DriveBase.driveArcade(0.0, -.57);
			}
			if (dualShock.getleftDPad() == true)
			{
				DriveBase.driveArcade(0.0, .57);
			}
			else if (dualShock.getTopDPad() == true)
			{
				intakeState = Constants.INTAKE_DOWN;
			}
			else if (dualShock.getBottomDPad() == true)
			{
				intakeState = Constants.INTAKE_UP;
			}
		}
				
		if(intakeState == Constants.INTAKE_DOWN)
		{
			leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(ControlMode.PercentOutput, leftOutput);
			Intake.rightIntake.set(ControlMode.PercentOutput, -rightOutput);
			if(Intake.leftPot.getVoltage() < Constants.LEFT_INTAKE_DOWN)
			{
				Intake.leftIntake.set(ControlMode.PercentOutput, 0.0);
				Intake.rightIntake.set(ControlMode.PercentOutput, 0.0);
			}
		}
		else if (intakeState == Constants.INTAKE_UP)
		{
			leftError = Constants.LEFT_INTAKE_UP - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_UP - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(ControlMode.PercentOutput, leftOutput);
			Intake.rightIntake.set(ControlMode.PercentOutput, -rightOutput);
			if(Intake.rightPot.getVoltage() > Constants.RIGHT_INTAKE_UP)
			{
				Intake.leftIntake.set(ControlMode.PercentOutput, 0.0);
				Intake.rightIntake.set(ControlMode.PercentOutput, 0.0);
			}
		}
		
		if(dualShock.getRightThrottleButton() == true)
		{
			if(Shooter.shooterLimitSwitch.get() == true)
			{
				Shooter.shooter.set(ControlMode.PercentOutput, 0.0);
			}
			else
			{
				error = Constants.SHOOTER_DOWN - Shooter.shooter.getSelectedSensorPosition(0);
				errorRefresh = error + errorRefresh;
				output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
				if (output < -.5)
				{
					output = -.5;
				}
				Shooter.shooter.set(ControlMode.PercentOutput, output);
			}
			Shooter.pullBackShooterArm();
			Shooter.intake();
			Intake.intakeBall((dualShock.getRightTriggerAxis() + 1) / 2);

		}
		
		else if (dualShock.getLeftTriggerAxis() > 0)
		{
			if(Shooter.shooterLimitSwitch.get() == true)
			{
			
				Shooter.shooter.set(ControlMode.PercentOutput, 0.0);
			}
			else
			{
				error = Constants.SHOOTER_DOWN - Shooter.shooter.getSelectedSensorPosition(0);
				errorRefresh = error + errorRefresh;
				output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
				if (output < -.5)
				{
					output = -.5;
				}
				Shooter.shooter.set(ControlMode.PercentOutput, output);
				
				
			}
			if(Shooter.shooter.getSelectedSensorPosition(0) < 400)
			{
				Shooter.lowGoal();
				Intake.lowGoal();
			}
		}		
		
		else if (operator.getButton(6) == true || (dualShock.getRightAnalogStickButton() == true))
		{
			int shooterPos = Shooter.shooter.getSelectedSensorPosition(0);
			Constants.CLOSE_SHOT = Preferences.getInstance().getInt("Close Shot", Constants.CLOSE_SHOT);
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
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			if (Shooter.shooter.getSelectedSensorPosition(0) < 2000)
			{
				Shooter.flyWheel1.set(ControlMode.PercentOutput, .85);
				Shooter.flyWheel2.set(ControlMode.PercentOutput, -.85);
			}
			else
			{
				Shooter.spinUpWheels(-.59);	
			}
		}
/*		
		else if (operator.getThumbLeft())
		{
			error = Constants.CAMERA_THRESHOLD_ANGLE - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			if(output < 0 && output < -.5)
			{
				output = -.5;
			}
			else if(output > 0 && output > .5)
			{
				output = .5;
			}
			Shooter.shooter.set(output);
		}
*/		
		else if (operator.getButton(5))
		{
			error = Constants.CASTLE_WALL_SHOT - Shooter.shooter.getSelectedSensorPosition(0);
			errorRefresh = errorRefresh + error;
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
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			if (Shooter.shooter.getSelectedSensorPosition(0) < 3000)
			{
				Shooter.intake();
			}
			else
			{
				Shooter.spinUpWheels(-1);	//Was .59
				
			}
			
		}
/*
		else if (operator.getThumbRight())
		{
			error = Constants.CLOSE_SHOT - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(output);
		}
*/		
		else if (operator.getButton(2) || (dualShock.getLeftAnalogStickButton() == true ))
		{
			Tracking.lightOn();
			Constants.FAR_SHOT_COMP = Preferences.getInstance().getInt("Far Shot", Constants.FAR_SHOT_COMP);
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getSelectedSensorPosition(0);
			if(Math.abs(error)<335)
			{
				errorRefresh = error + errorRefresh;
			}
			else 
			{
				errorRefresh = 0;
			}
			output = ((error * Constants.SHOOTER_LONG_KP) + (errorRefresh * Constants.SHOOTER_LONG_KI));

			if (output > .85)
			{
				output = .85;
			}
			else if (output < -.85)
			{
				output = -.85;
			}
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			if (Shooter.shooter.getSelectedSensorPosition(0) < 1900) //Was 1900
			{
				Shooter.flyWheel1.set(ControlMode.PercentOutput, .35);
				Shooter.flyWheel2.set(ControlMode.PercentOutput, -.35);
			}
			else
			{
				Shooter.spinUpWheels(-1);	
			}
		}
		else
		{
			Tracking.lightOff();
			Shooter.flyWheel1.set(ControlMode.PercentOutput, 0.0);
			Shooter.flyWheel2.set(ControlMode.PercentOutput, 0.0);
			error = 0;
			output = 0;
			errorRefresh = 0;
			Intake.stopIntake();
			

			if(Shooter.shooterLimitSwitch.get() && operator.getYAxis() < 0)
			{
				Shooter.manualControl(0.0);
				if(operator.getButton(11))
				{
					double speed = operator.getYAxis();
					Climber.winch1.set(ControlMode.PercentOutput, -speed);
					Climber.winch2.set(ControlMode.PercentOutput, speed);
				}

			}
			else if (Shooter.shooter.getSelectedSensorPosition(0) >= 4100 && operator.getYAxis() > 0)
			{
				Shooter.manualControl(0.0);
				if(operator.getButton(11))
				{
					double speed = operator.getYAxis();
					Climber.winch1.set(ControlMode.PercentOutput, -speed);
					Climber.winch2.set(ControlMode.PercentOutput, speed);
				}

			}
			else
			{
				if(operator.getButton(11))
				{
					double speed = operator.getYAxis();
					Climber.winch1.set(ControlMode.PercentOutput, -speed);
					Climber.winch2.set(ControlMode.PercentOutput, speed);
				}
				else
				{
					Shooter.manualControl(operator.getYAxis());
				}
			}
		}
		
		if(Shooter.shooterLimitSwitch.get())
		{
			Shooter.zeroShooterEnc();
			SmartDashboard.putBoolean("Is pressed", true);
		}
		else
		{
			SmartDashboard.putBoolean("Is pressed", false);
			
		}
		if(dualShock.getSquareButton())
		{
			Tracking.autoTarget();
		}
		else
		{
			Tracking.resetVision();
		}
		if(dualShock.getCircleButton())
		{
			UDP.getData();
		}
		SmartDashboard.putNumber("Shooter Angle", Shooter.shooter.getSelectedSensorPosition(0));
		Intake.sensorReadOut();
		Tracking.printOut();
		SmartDashboard.putNumber("Auton State Value", Autonomous.autonState);
		SmartDashboard.putNumber("Shooter Lever", Shooter.shooterLever.getSelectedSensorPosition(0)); //Use if shooter lever acts up again.
		SmartDashboard.putBoolean("Shooter Lever Lim", Shooter.shooterLeverLimitSwitch.get());
		SmartDashboard.putNumber("Shooter Lever Encoder Tick", Shooter.shooterLever.getSelectedSensorPosition(0));
	}
	
	
}
