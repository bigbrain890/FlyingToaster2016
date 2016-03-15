package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		if (dualShock.getRightBumper())
		{
			driveMode = Constants.DRIVE_NORMAL;
		}
		else if (dualShock.getLeftBumper())
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
		//Operator Logic
		if(operator.getIndexTrigger())
		{
			Shooter.fireLogic();
		}

		//PS4 Controller Logic		
		if(dualShock.getCircleButton())
		{
			Shooter.mediumShot();
		}
		else if(dualShock.getXButton())
		{
			Shooter.mediumShotTest();
		}
		else if(dualShock.getSquareButton())
		{
			//Last Resort: The Ugly Way
			Shooter.shooter.set(PILoop.loop(Shooter.shooter.getEncPosition(), Constants.MEDIUM_SHOT, Constants.SHOOTER_KP, Constants.SHOOTER_KI));
		}
		else
		{
			Shooter.flyWheel1.set(0.0);
			Shooter.flyWheel2.set(0.0);
			Intake.stopIntake();
			Shooter.manualControl(operator.getYAxis());
		}
		
		SmartDashboard.putNumber("Drive Distance", DriveBase.getDriveDis());
		Shooter.sensorReadout();
		Tracking.printOut();
	}

}

/*
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
		
		else if(dualShock.getXButton() == true)
		{
			//Intake.spitBall();
		}
		
		
		else if (operator.getBaseFrontLeft() == true)
		{
			Shooter.closeShot();
			Shooter.spinUpWheels();
		}
		
		else if (operator.getBaseCenterLeft() == true)
		{
			Shooter.farShot();
			Shooter.spinUpWheels();
		}
		
		else if (operator.getBaseBackLeft() == true)
		{
			double pidOut=PILoop.loop(Shooter.shooter.getEncPosition(), Constants.MEDIUM_SHOT, Constants.SHOOTER_KP, Constants.SHOOTER_KI);
			Shooter.shooter.set(pidOut);
			SmartDashboard.putNumber("PID Out", pidOut);
		}
		else
		{
			Shooter.flyWheel1.set(0.0);
			Shooter.flyWheel2.set(0.0);
			Intake.stopIntake();
			Shooter.manualControl(operator.getYAxis());
		}
				if(!Shooter.leverLimSwitch.get())
		{
			Shooter.zeroShooterLeverEnc();
}*/