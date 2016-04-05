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
	static public double driveBackTarg = 0;
	public static int shooterLeverState = Constants.RESTING_POSITION;
	public static int intakeState = Constants.INTAKE_DOWN;
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
			driveMode = Constants.DRIVE_TANK;
		}
		if (operator.getIndexTrigger() == true)
		{
			shooterLeverState = Constants.FIRE;
		}
		if(dualShock.getXButton() == true)
		{
			driveBack = Constants.DO_DRIVE_BACK_MATH;
		}
		else if(dualShock.getTriangleButton() == true)
		{
			driveBack = Constants.RESTING_POSITION;
		}
		if (dualShock.getShareButton() == true)
		{
			intakeState = Constants.INTAKE_DOWN;
		}
		else if (dualShock.getRightAnalogStickButton() == true)
		{
			intakeState = Constants.INTAKE_DOWN;
		}
		
		else if (dualShock.getLeftAnalogStickButton() == true)
		{
			intakeState = Constants.INTAKE_UP;
		}
		
		
				
		// Actually driving and stuff
		if (driveMode == Constants.DRIVE_NORMAL)
		{
			DriveBase.driveNormal(dualShock.getLeftStickYAxis(), -1* dualShock.getRightStickXAxis());
			if(dualShock.getRightDPad() == true)
			{
				DriveBase.driveNormal(0.0, -.53);
			}
			else if (dualShock.getleftDPad() == true)
			{
				DriveBase.driveNormal(0.0, .53);
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
		else if (driveMode == Constants.DRIVE_TANK)
		{
			DriveBase.driveTank(dualShock.getLeftStickYAxis(), dualShock.getRightStickYAxis());
		}
		
		if(intakeState == Constants.INTAKE_DOWN)
		{
			double leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			double rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			double leftOutput = leftError * Constants.INTAKE_KP;
			double rightOutput = rightError * Constants.INTAKE_KP;
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
			double leftError = Constants.LEFT_INTAKE_UP - Intake.leftPot.getVoltage();
			double rightError = Constants.RIGHT_INTAKE_UP - Intake.rightPot.getVoltage();
			double leftOutput = leftError * Constants.INTAKE_KP;
			double rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(leftOutput);
			Intake.rightIntake.set(-rightOutput);
		}
		
		if (driveBack == Constants.DO_DRIVE_BACK_MATH)
		{
			driveBackTarg = DriveBase.getDriveDis() - Constants.ROLL_BACK;
			driveBack = Constants.DRIVE_BACK;
		}
		
		else if (driveBack == Constants.DRIVE_BACK)
		{
			double error = driveBackTarg - DriveBase.getDriveDis();
			double output = error * Constants.DRIVE_KP;
			if(output > .35)
			{
				output = .35;
			}
			else if (error < -.35)
			{
				output = -.35; 
			}
			DriveBase.driveNormal(-output, 0.0);
			if(Math.abs(error) < 2)
			{
				driveBack = Constants.RESTING_POSITION;
			}
		}
		
		if (dualShock.getSquareButton() == true)
		{
			Tracking.autoTarget();
		}
		
		else
		{
			Tracking.resetVision();
		}
		
		if((dualShock.getRightTriggerAxis() > 0) || (operator.getThumbBottom() == true))
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
			Intake.intakeBall();
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
				Shooter.intake();
			}
			else
			{
				Shooter.spinUpWheels(.75);	
			}
			double shotAccuracy = Math.abs(Constants.CLOSE_SHOT - Shooter.shooter.getEncPosition());
			if(shotAccuracy < 30)
			{
				SmartDashboard.putBoolean("FIRE", true);
			}
			else
			{
				SmartDashboard.putBoolean("FIRE", false);
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
			error = Constants.SHOOTER_DOWN - Shooter.shooter.getEncPosition();
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
			Constants.FAR_SHOT_COMP = Preferences.getInstance().getInt("Far Shot", Constants.FAR_SHOT_COMP);
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(output);
			if (Shooter.shooter.getEncPosition() < 2000)
			{
				Shooter.intake();
			}
			else
			{
				Shooter.spinUpWheels(1);	
			}
			double shotAccuracy = Math.abs(Constants.FAR_SHOT_COMP - Shooter.shooter.getEncPosition());
			if(shotAccuracy < 30)
			{
				SmartDashboard.putBoolean("FIRE", true);
			}
			else
			{
				SmartDashboard.putBoolean("FIRE", false);
			}
			
		}
		else
		{
			Shooter.flyWheel1.set(0.0);
			Shooter.flyWheel2.set(0.0);
			error = 0;
			output = 0;
			errorRefresh = 0;
			Intake.stopIntake();
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
			SmartDashboard.putBoolean("FIRE", false);
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
			if ((Shooter.shooterLever.getEncPosition() <= 25))
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
