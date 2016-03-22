package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleOperated
{
	private static TeleOperated instance;
	public static PS4Controller dualShock;
	public static Attack3 operator;
	static public boolean driveMode = Constants.DRIVE_NORMAL;
	public static int shooterLeverState = Constants.RESTING_POSITION;
	static double errorRefresh = 0;
	static double error = 0;
	static double output = 0;
	
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
		if (operator.getIndexTrigger() == true || dualShock.getXButton() == true)
		{
			shooterLeverState = Constants.FIRE;
		}

		
		// Actually driving and stuff
		if (driveMode == Constants.DRIVE_NORMAL)
		{
			DriveBase.driveNormal(dualShock.getLeftStickYAxis(), -1* dualShock.getRightStickXAxis());
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
		
		if((dualShock.getRightTriggerAxis() > 0) || (operator.getThumbBottom() == true))
		{
			if(Shooter.shooterLimitSwitch.get() == true)
			{
			
				Shooter.shooter.set(0.0);
			}
			else
			{
				error = Constants.INTAKE_DOWN - Shooter.shooter.getEncPosition();
				errorRefresh = error + errorRefresh;
				output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
				if (output < -.5)
				{
					output = -.5;
				}
				Shooter.shooter.set(output);
			}
			Shooter.intake();
		}
		
		else if (dualShock.getLeftTriggerAxis() > 0)
		{
			if(Shooter.shooterLimitSwitch.get() == true)
			{
			
				Shooter.shooter.set(0.0);
			}
			else
			{
				error = Constants.INTAKE_DOWN - Shooter.shooter.getEncPosition();
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
			}
		}
		
		else if(dualShock.getXButton() == true)
		{
			//Intake.spitBall();
		}
		
		
		else if (operator.getBaseFrontLeft() == true)
		{
			error = Constants.CLOSE_SHOT - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(output);
			if (Shooter.shooter.getEncPosition() < 1100)
			{
				Shooter.intake();
			}
			else
			{
				Shooter.spinUpWheels(.9);	
			}
		}
		
		else if (operator.getThumbLeft())
		{
			error = Constants.CAMERA_THRESHOLD_ANGLE - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(output);
		}
		else if (operator.getBaseCenterLeft())
		{
			error = Constants.INTAKE_DOWN - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			if (output < -.5)
			{
				output = -.5;
			}
			Shooter.shooter.set(output);
			Shooter.lowGoal();
		}
		else if (operator.getThumbRight())
		{
			error = Constants.CLOSE_SHOT - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(output);
		}
		
		else if (operator.getBaseBackLeft() == true)
		{
			error = Constants.FAR_SHOT - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(output);
			if (Shooter.shooter.getEncPosition() < 1000)
			{
				Shooter.intake();
			}
			else
			{
				Shooter.spinUpWheels(1);	
			}
			
		}
		else
		{
			Shooter.flyWheel1.set(0.0);
			Shooter.flyWheel2.set(0.0);
			Intake.stopIntake();
			error = 0;
			output = 0;
			errorRefresh = 0;
			if(Shooter.shooterLimitSwitch.get() && operator.getYAxis() < 0)
			{
				Shooter.manualControl(0.0);
			}
			else if (Shooter.shooter.getEncPosition() >= 4100 && operator.getYAxis() > 0)
			{
				Shooter.manualControl(0.0);
			}
			else
			{
				Shooter.manualControl(operator.getYAxis());
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
			if ((Shooter.shooterLever.getEncPosition() <= 0) || (Shooter.leverLimSwitch.get() == false))
			{
				Shooter.resetShooterArm();
			}
			else
			{
				shooterLeverState = Constants.RESTING_POSITION;
			}
		}
		if(Shooter.shooterLimitSwitch.get())
		{
			Shooter.zeroShooterEnc();
		}
		if (Shooter.shooterLimitSwitch.get() == true)
		{
			SmartDashboard.putBoolean("Is pressed", true);
		}
		else
		{
			SmartDashboard.putBoolean("Is pressed", false);
			
		}
		Shooter.sensorReadout();
		Tracking.printOut();
	}
	
	
}
