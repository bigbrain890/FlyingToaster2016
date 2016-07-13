package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;

public class TeleOperated
{
	private static TeleOperated instance;
	public static PS4Controller dualShock;
	public static Attack3 operator;
	static public boolean driveMode = Constants.DRIVE_NORMAL;
	static public int driveBack = Constants.UNPRESSED;
	static public double leftError = 0, rightError = 0, leftOutput = 0, rightOutput = 0;
	public static int shooterLeverState = Constants.RESTING_POSITION;
	public static int intakeState = Constants.INTAKE_DOWN;
	private static double errorRefresh = 0;
	private static double error = 0;
	private static double output = 0;

	private TeleOperated()
	{
		dualShock = new PS4Controller(Constants.PS4_CONTROLLER);
		operator = new Attack3(Constants.ATTACK3);
	}
	
	public static TeleOperated getInstance()
	{
		if(instance == null)
		{
			instance = new TeleOperated();
		}
		return instance;
	}
	
	public static void runDriver()
	{
		if (operator.getIndexTrigger() == true)
		{
			shooterLeverState = Constants.FIRE;
		}
		if (dualShock.getShareButton() == true)
		{
			
		}
		
		if (dualShock.getRightAnalogStickButton() == true)
		{
			intakeState = Constants.INTAKE_DOWN;
		}
		
		else if (dualShock.getLeftAnalogStickButton() == true)
		{
			intakeState = Constants.INTAKE_UP;
		}
	
		
				
		// Actually driving and stuff
		if ((driveMode == Constants.DRIVE_NORMAL) && (dualShock.getSquareButton() != true))
		{
			DriveBase.driveNormal(dualShock.getLeftStickYAxis(), -1* dualShock.getRightStickXAxis());
			if(dualShock.getRightDPad() == true)
			{
				DriveBase.driveNormal(0.0, -.57);
			}
			if (dualShock.getleftDPad() == true)
			{
				DriveBase.driveNormal(0.0, .57);
			}
			else if (dualShock.getTopDPad() == true)
			{
				DriveBase.driveNormal(-.5, 0.0);
			}
			else if (dualShock.getBottomDPad() == true)
			{
				DriveBase.driveNormal(.5, 0.0);
			}
		}
		else if ((driveMode == Constants.DRIVE_TANK) && (dualShock.getSquareButton() != true))
		{
			DriveBase.driveTank(dualShock.getLeftStickYAxis(), dualShock.getRightStickYAxis());
		}
		
		if(intakeState == Constants.INTAKE_DOWN)
		{
			leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(leftOutput);
			Intake.rightIntake.set(-rightOutput);
			if(Intake.leftPot.getVoltage() < Constants.LEFT_INTAKE_DOWN)
			{
				Intake.leftIntake.set(0.0);
				Intake.rightIntake.set(0.0);
			}
		}
		else if (intakeState == Constants.INTAKE_UP)
		{
			leftError = Constants.LEFT_INTAKE_UP - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_UP - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(leftOutput);
			Intake.rightIntake.set(-rightOutput);
			if(Intake.rightPot.getVoltage() > Constants.RIGHT_INTAKE_UP)
			{
				Intake.leftIntake.set(0.0);
				Intake.rightIntake.set(0.0);
			}
		}
		
		if(dualShock.getRightThrottleButton() == true)
		{
			if(Shooter.shooterLimitSwitch.get() == true)
			{
				Shooter.shooter.set(0.0);
			}
			else
			{
				error = Constants.SHOOTER_DOWN - Shooter.shooter.getEncPosition();
				errorRefresh = error + errorRefresh;
				output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
				if (output < -.5)
				{
					output = -.5;
				}
				Shooter.shooter.set(output);
			}
			Shooter.pullBackShooterArm();
			Shooter.intake();
			Intake.intakeBall((dualShock.getRightTriggerAxis() + 1) / 2);

		}
		
		else if (dualShock.getLeftTriggerAxis() > 0)
		{
			if(Shooter.shooterLimitSwitch.get() == true)
			{
			
				Shooter.shooter.set(0.0);
			}
			else
			{
				error = Constants.SHOOTER_DOWN - Shooter.shooter.getEncPosition();
				errorRefresh = error + errorRefresh;
				output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
				if (output < -.5)
				{
					output = -.5;
				}
				Shooter.shooter.set(output);
				
				
			}
			if(Shooter.shooter.getEncPosition() < 400)
			{
				Shooter.lowGoal();
				Intake.lowGoal();
			}
		}		
		
		else if (operator.getBaseFrontLeft() == true)
		{
			int shooterPos = Shooter.shooter.getEncPosition();
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
		else if (operator.getBaseCenterLeft())
		{
			error = Constants.CASTLE_WALL_SHOT - Shooter.shooter.getEncPosition();
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
			Shooter.shooter.set(output);
			if (Shooter.shooter.getEncPosition() < 3000)
			{
				Shooter.intake();
			}
			else
			{
				Shooter.spinUpWheels(-.59);	
				
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
		else if (operator.getBaseBackLeft() == true)
		{
			Tracking.lightOn();
			Constants.FAR_SHOT_COMP = Preferences.getInstance().getInt("Far Shot", Constants.FAR_SHOT_COMP);
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
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
				Shooter.spinUpWheels(-1);	
			}
		}
		else
		{
			Tracking.lightOff();
			Shooter.flyWheel1.set(0.0);
			Shooter.flyWheel2.set(0.0);
			error = 0;
			output = 0;
			errorRefresh = 0;
			Intake.stopIntake();
			

			if(Shooter.shooterLimitSwitch.get() && operator.getYAxis() < 0)
			{
				Shooter.manualControl(0.0);
				if(operator.getBaseFrontRight())
				{
					double speed = operator.getYAxis();
					Climber.winch1.set(-speed);
					Climber.winch2.set(speed);
				}

			}
			else if (Shooter.shooter.getEncPosition() >= 4100 && operator.getYAxis() > 0)
			{
				Shooter.manualControl(0.0);
				if(operator.getBaseFrontRight())
				{
					double speed = operator.getYAxis();
					Climber.winch1.set(-speed);
					Climber.winch2.set(speed);
				}

			}
			else
			{
				if(operator.getBaseFrontRight())
				{
					double speed = operator.getYAxis();
					Climber.winch1.set(-speed);
					Climber.winch2.set(speed);
				}
				else
				{
					Shooter.manualControl(operator.getYAxis());
				}
			}
		}
		
		if (shooterLeverState == Constants.RESTING_POSITION)
		{
			Shooter.restShooterArm();
		}
		else if (shooterLeverState == Constants.FIRE)
		{
			
			if (Shooter.shooterLever.getEncPosition() >= Constants.LEVER_MAX_SWING)
			{
				Shooter.fire();
			}
			else
			{
				Shooter.resetShooterArm();
				shooterLeverState = Constants.RESET;
			}
		}
		else if (shooterLeverState == Constants.RESET)
		{
			if ((Shooter.shooterLever.getEncPosition() <= 25) || (Shooter.shooterLeverLimitSwitch.get() == false))
			{
				Shooter.resetShooterArm();
			}
			else
			{
				shooterLeverState = Constants.RESTING_POSITION;
				if( Shooter.shooterLeverLimitSwitch.get())
				{
					Shooter.zeroShooterLeverEnc();
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
		if (dualShock.getRightBumper() == true)
		{
			Intake.rollers.set(1);
		}
		else if (dualShock.getLeftBumper() == true)
		{
			Intake.lowGoal();
			Shooter.intake();
		}
		if(dualShock.getSquareButton())
		{
			Tracking.autoTarget();
		}
		else
		{
			Tracking.resetVision();
		}
		SmartDashboard.putNumber("Shooter Angle", Shooter.shooter.getEncPosition());
		Intake.sensorReadOut();
		Tracking.printOut();
		SmartDashboard.putNumber("Shooter Lever", Shooter.shooterLever.getEncPosition()); //Use if shooter lever acts up again.

	}
	
	
}
