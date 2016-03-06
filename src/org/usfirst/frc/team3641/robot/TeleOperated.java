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
			DriveBase.driveTank(dualShock.getLeftStickYAxis(), dualShock.getRightStickYAxis());
		}
		else if (driveMode == Constants.DRIVE_REVERSE)
		{
			DriveBase.driveReverseTank(dualShock.getLeftStickYAxis(), dualShock.getRightStickYAxis());
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
		
		if(dualShock.getRightThrottleButton() == true)
		{
			Shooter.intake();
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
		}
		
		if(operator.getTrigger() == true)
		{
			Shooter.fire(.3);
		}
		else if (operator.getBaseCenterLeft() == true)
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
