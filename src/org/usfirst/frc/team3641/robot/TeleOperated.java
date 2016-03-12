package org.usfirst.frc.team3641.robot;

public class TeleOperated
{
	private static TeleOperated instance;
	public static PS4Controller dualShock;
	public static Attack3 operator;
	static public boolean driveMode = Constants.DRIVE_NORMAL;
	public static int shooterLeverState = Constants.RESTING_POSITION;
	
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
		// State Switching inputs.
		if (dualShock.getRightBumper() == true)
		{
			driveMode = Constants.DRIVE_NORMAL;
		}
		else if (dualShock.getLeftBumper() == true)
		{
			driveMode = Constants.DRIVE_REVERSE;
		}
		if (operator.getIndexTrigger() == true)
		{
			shooterLeverState = Constants.FIRE;
		}

		
		// Actually driving and stuff
		if (driveMode == Constants.DRIVE_NORMAL)
		{
			DriveBase.driveNormal(dualShock.getLeftStickYAxis(), dualShock.getRightStickXAxis());
		}
		else if (driveMode == Constants.DRIVE_REVERSE)
		{
			DriveBase.driveReverse(dualShock.getLeftStickYAxis(), dualShock.getRightStickXAxis());
		}
		
		if (dualShock.getSquareButton() == true)
		{
			Tracking.autoTarget();
		}
		
		else
		{
			Tracking.resetVision();
		}
		
		if(dualShock.getOptionsButton())
		{
			DriveBase.resetGyro();
		}
		
		if(dualShock.getRightTriggerAxis() > 0)
		{
			Shooter.intake();
			Intake.pullBall();
		}
		
		else if(dualShock.getLeftTriggerAxis() > 0)
		{
			Shooter.lowGoal();
			Intake.spitBall();
		}
		
		
		else if (operator.getBaseFrontLeft() == true)
		{
			Shooter.closeShot();
			Shooter.spinUpWheels();
		}
		
		else if (operator.getBaseBackLeft() == true)
		{
			Shooter.mediumShot();
			Shooter.spinUpWheels();
		}
		else if (operator.getBaseCenterLeft() == true)
		{
			Shooter.farShot();
			Shooter.spinUpWheels();
		}
		else
		{
			Shooter.flyWheel1.set(0.0);
			Shooter.flyWheel2.set(0.0);
			Intake.stopIntake();
		}
		if (shooterLeverState == Constants.RESTING_POSITION)
		{
			Shooter.restShooterArm();
		}
		else if (shooterLeverState == Constants.FIRE)
		{
			if (Shooter.shooterLever.getAnalogInPosition() < Constants.LEVER_MAX_SWING)
			{
				Shooter.fire();
			}
			else
			{
				shooterLeverState = Constants.RESET;
			}
		}
		else if (shooterLeverState == Constants.RESET)
		{
			if ((Shooter.shooterLever.getAnalogInPosition() > 0) || (Shooter.leverLimSwitch.get() == false))
			{
				Shooter.resetShooterArm();
			}
			else
			{
				shooterLeverState = Constants.RESTING_POSITION;
			}
		}
		
		Shooter.manualControl(operator.getYAxis());
		Shooter.getShooterAngle();
		Tracking.printOut();
	}
}
