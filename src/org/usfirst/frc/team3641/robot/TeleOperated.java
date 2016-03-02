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
		if ((driveMode == Constants.DRIVE_NORMAL) && (dualShock.getRightAnalogStickButton() != true))
		{
			DriveBase.driveTank(dualShock.getLeftStickYAxis(), dualShock.getRightStickYAxis());
		}
		else if ((driveMode == Constants.DRIVE_REVERSE) && (dualShock.getRightAnalogStickButton() != true))
		{
			DriveBase.driveReverseTank(dualShock.getLeftStickYAxis(), dualShock.getRightStickYAxis());
		}
		else
		{
			DriveBase.driveTank(0.0, 0.0);
		}
		
		if (dualShock.getSquareButton() == true)
		{
			Tracking.autoTarget();
		}
		
		else
		{
			Tracking.resetVision();
		}
		
		if (dualShock.getLeftAnalogStickButton() == true)
		{
			Shooter.spinUpWheels();
		}
		
		if (dualShock.getLeftThrottleButton() == true)
		{
			Shooter.intake();
		}
		else
		{
			Shooter.flyWheel1.set(0.0);
			Shooter.flyWheel2.set(0.0);
		}
		if(dualShock.getOptionsButton())
		{
			DriveBase.resetGyro();
		}
		if(dualShock.getRightAnalogStickButton() == true)
		{
			Shooter.manualControl(dualShock.getRightStickYAxis());
		}
		else
		{
			Shooter.manualControl(0.0);
		}
		if(dualShock.getTriangleButton() == true)
		{
			Shooter.fire(.09);
		}
		else if (dualShock.getXButton() == true)
		{
			Shooter.resetShooterArm(.09);
		}
		else
		{
			Shooter.shooterLever.set(0.0);
		}
		Tracking.printOut();
	}
}
