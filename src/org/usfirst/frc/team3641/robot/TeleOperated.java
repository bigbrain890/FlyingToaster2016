package org.usfirst.frc.team3641.robot;

public class TeleOperated
{
	private static TeleOperated instance;
	public static PS4Controller dualShock;
	public static Attack3 operator;
	static public boolean driveMode = Constants.DRIVE_NORMAL;
	
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
		
		if(operator.getTrigger() == true)
		{
			Shooter.fire(.3);
		}
		else if (operator.getThumbTop() == true)
		{
			Shooter.resetShooterArm(.3);
		}
		else
		{
			Shooter.shooterLever.set(0.0);
		}
		Shooter.manualControl(operator.getYAxis());
		Shooter.getShooterAngle();
		Tracking.printOut();
	}
}
