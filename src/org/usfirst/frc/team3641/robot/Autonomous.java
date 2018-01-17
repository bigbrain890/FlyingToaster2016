package org.usfirst.frc.team3641.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous
{
	private static double error=0, errorRefresh=0, output=0, leftError=0, rightError=0,leftOutput=0, rightOutput=0, driveError=0, oldDriveError=0, driveOutput=0, driveErrorRefresh=0;
	private static Autonomous instance;
	private static Timer autoTimer, aimTimer, intakeTimer, turnTimer;
	public static int autonState = 1;
	static int shooterLeverState = 0;
	public static int counter = 0;
	private static double speed, rotation;
	private Autonomous()
	{
		autoTimer = new Timer();
		aimTimer = new Timer();
		intakeTimer = new Timer();
		turnTimer = new Timer();
	}
	
	public static Autonomous getInstance()
	{
		if(instance == null)
		{
			instance = new Autonomous();
		}
		return instance;
	}
	
	public static void run(int mode)
	{
		switch (mode) 
		{
			case Constants.LOW_BAR:
				lowBar();
				break;
			
			case Constants.ROCK_WALL:
				rockWall();
				break;
			
			case Constants.SHOVE_IN_FREEZER:
				shoveInFreezer();
				break;
			
			case Constants.SALLY_PORT:
				sallyPort();
				break;
				
			case Constants.DRAW_BRIDGE:
				drawBridge();
				break;
				
			case Constants.MOAT:
				moat();
				break;
				
			case Constants.ROUGH_TERRAIN:
				roughTerrain();
				break;
				
			case Constants.PORTCULLIS:
				portcullis();
				break;
				
			case Constants.RAMPARTS:
				ramparts();
				break;
			
			case Constants.ONE_BALL:
				oneBall();
				break;
		}
	}
	
	public static void startTimers()
	{
		autoTimer.reset();
		aimTimer.reset();
		autoTimer.start();
		aimTimer.start();
	}
	
	public static void lowBar()
	{
		if (autonState == 1)
		{
			intakeTimer.start();
			autonState++;
		}
		if (autonState == 2)
		{
			leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(ControlMode.PercentOutput, leftOutput);
			Intake.rightIntake.set(ControlMode.PercentOutput, -rightOutput);
			if(Intake.leftPot.getVoltage() < Constants.LEFT_INTAKE_DOWN || intakeTimer.get() > Constants.MAX_INTAKE_DOWN_TIME)
			{
				Intake.leftIntake.set(ControlMode.PercentOutput, 0.0);
				Intake.rightIntake.set(ControlMode.PercentOutput, 0.0);
				autonState++;
			}
		}
		else if (autonState == 3)
		{
			DriveBase.resetDriveSensors();
			autonState++;
		}
		else if (autonState == 4)
		{
			Shooter.intake();
			if (DriveBase.getDriveDis() < Constants.TARGET_DEFENSE_DRIVE_DIS_LOW)
			{
				DriveBase.driveStraight(0.0,.55 );
			}
			else
			{
				DriveBase.driveArcade(0.0, 0.0);
				autonState++;
			}
		}
		
		else if (autonState == 5)
		{
			if (DriveBase.getDriveDirection() < 45)
			{
				error = 45 - DriveBase.getDriveDirection();
				if (error >= 180)
				{
					error -= 360;
				}
				else if (error<=-180)
				{
					error+=360;
				}
				double rotate = error*Constants.DRIVE_KP;
				if (rotate > .5)
				{
					rotate = .5;
				}
				DriveBase.driveArcade(0.0, -rotate);
			}
			else
			{
				DriveBase.driveArcade(0.0,0.0);
				autonState++;
			}
		}
		else if (autonState == 6)
		{
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getSelectedSensorPosition(0);
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			if(error <= 50)
				autonState++;

		}
		else if (autonState == 7)
		{
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getSelectedSensorPosition(0);
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			if (Tracking.autoTarget())
			{
				startTimers();
				autonState++;
			}
		}
		else if (autonState == 8)
		{
			if( aimTimer.get() < 4)
			{
				Shooter.spinUpWheels(1);
				shooterLeverState = Constants.FIRE;
			}
			
			else
			{
				if (shooterLeverState == Constants.RESTING_POSITION)
				{
					Shooter.restShooterArm();
				}
				else if (shooterLeverState == Constants.FIRE)
				{
					
					if (Shooter.shooterLever.getSelectedSensorPosition(0) >= Constants.LEVER_MAX_SWING)
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
					if ((Shooter.shooterLever.getSelectedSensorPosition(0) <= 25))
					{
						Shooter.resetShooterArm();
					}
					else
					{
						shooterLeverState = Constants.RESTING_POSITION;
					}
				}
			}
		}
	}
	
	public static void rockWall()
	{
		if (autonState == 1)
		{
			intakeTimer.start();
			autonState++;
		}
		if (autonState == 2)
		{
			leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(ControlMode.PercentOutput, leftOutput);
			Intake.rightIntake.set(ControlMode.PercentOutput, -rightOutput);
			if(intakeTimer.get() >= 7)
			{
				Intake.leftIntake.set(ControlMode.PercentOutput, 0.0);
				Intake.rightIntake.set(ControlMode.PercentOutput, 0.0);
				autonState++;
			}
		}
		else if (autonState == 3)
		{
			DriveBase.resetDriveSensors();
			autonState++;
		}
		else if (autonState == 4)
		{
			Shooter.intake();
			if (DriveBase.getDriveDis() < Constants.TARGET_DEFENSE_DRIVE_DIS)
			{
				DriveBase.driveStraight(0.0,.85 );
			}
			else
			{
				DriveBase.driveArcade(0.0, 0.0);
				autonState++;
			}
		}
		
		else if (autonState == 5)
		{
			if (DriveBase.getDriveDirection() < 45)
			{
				error = 45 - DriveBase.getDriveDirection();
				if (error >= 180)
				{
					error -= 360;
				}
				else if (error<=-180)
				{
					error+=360;
				}
				double rotate = error*Constants.DRIVE_KP;
				if (rotate > .5)
				{
					rotate = .5;
				}
				DriveBase.driveArcade(0.0, -rotate);
			}
			else
			{
				DriveBase.driveArcade(0.0,0.0);
				autonState++;
			}
		}
		else if (autonState == 6)
		{
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getSelectedSensorPosition(0);
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			//if(error <= 50)
				//autonState++;

		}
		else if (autonState == 7)
		{
			if (Tracking.autoTarget())
			{
				startTimers();
				autonState++;
			}
		}
		else if (autonState == 8)
		{
			if( aimTimer.get() < 2)
			{
				Shooter.spinUpWheels(1);
			}
			
			else
			{
				Shooter.fire();
			}
		}
	}
	
	public static void shoveInFreezer()
	{
		
	}
	
	public static void sallyPort()
	{
		
	}
	
	public static void drawBridge()
	{
		
	}
	
	public static void moat()
	{
		if (autonState == 1)
		{
			intakeTimer.start();
			autonState++;
		}
		if (autonState == 2)
		{
			leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(ControlMode.PercentOutput, leftOutput);
			Intake.rightIntake.set(ControlMode.PercentOutput, -rightOutput);
			if(intakeTimer.get() >= 7)
			{
				Intake.leftIntake.set(ControlMode.PercentOutput, 0.0);
				Intake.rightIntake.set(ControlMode.PercentOutput, 0.0);
				autonState++;
			}
		}
		else if (autonState == 3)
		{
			DriveBase.resetDriveSensors();
			autonState++;
		}
		else if (autonState == 4)
		{
			Shooter.intake();
			if (DriveBase.getDriveDis() < Constants.TARGET_DEFENSE_DRIVE_DIS)
			{
				DriveBase.driveStraight(0.0,.85 );
			}
			else
			{
				DriveBase.driveArcade(0.0, 0.0);
				autonState++;
			}
		}
		
		else if (autonState == 5)
		{
			if (DriveBase.getDriveDirection() < 45)
			{
				error = 45 - DriveBase.getDriveDirection();
				if (error >= 180)
				{
					error -= 360;
				}
				else if (error<=-180)
				{
					error+=360;
				}
				double rotate = error*Constants.DRIVE_KP;
				if (rotate > .5)
				{
					rotate = .5;
				}
				DriveBase.driveArcade(0.0, -rotate);
			}
			else
			{
				DriveBase.driveArcade(0.0,0.0);
				autonState++;
			}
		}
		else if (autonState == 6)
		{
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getSelectedSensorPosition(0);
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			//if(error <= 50)
				//autonState++;

		}
		else if (autonState == 7)
		{
			if (Tracking.autoTarget())
			{
				startTimers();
				autonState++;
			}
		}
		else if (autonState == 8)
		{
			if( aimTimer.get() < 2)
			{
				Shooter.spinUpWheels(1);
			}
			
			else
			{
				Shooter.fire();
			}
		}
	}
	
	public static void roughTerrain()
	{
		if (autonState == 1)
		{
			intakeTimer.start();
			autonState++;
		}
		if (autonState == 2)
		{
			leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(ControlMode.PercentOutput, leftOutput);
			Intake.rightIntake.set(ControlMode.PercentOutput, -rightOutput);
			if(intakeTimer.get() >= 7)
			{
				Intake.leftIntake.set(ControlMode.PercentOutput, 0.0);
				Intake.rightIntake.set(ControlMode.PercentOutput, 0.0);
				autonState++;
			}
		}
		else if (autonState == 3)
		{
			DriveBase.resetDriveSensors();
			autonState++;
		}
		else if (autonState == 4)
		{
			Shooter.intake();
			if (DriveBase.getDriveDis() < Constants.TARGET_DEFENSE_DRIVE_DIS)
			{
				DriveBase.driveStraight(0.0,.75 );
			}
			else
			{
				DriveBase.driveArcade(0.0, 0.0);
				autonState++;
			}
		}
		
		else if (autonState == 5)
		{
			if (DriveBase.getDriveDirection() < 45)
			{
				error = 45 - DriveBase.getDriveDirection();
				if (error >= 180)
				{
					error -= 360;
				}
				else if (error<=-180)
				{
					error+=360;
				}
				double rotate = error*Constants.DRIVE_KP;
				if (rotate > .5)
				{
					rotate = .5;
				}
				DriveBase.driveArcade(0.0, -rotate);
			}
			else
			{
				DriveBase.driveArcade(0.0,0.0);
				autonState++;
			}
		}
		else if (autonState == 6)
		{
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getSelectedSensorPosition(0);
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			//if(error <= 50)
				//autonState++;

		}
		else if (autonState == 7)
		{
			if (Tracking.autoTarget())
			{
				startTimers();
				autonState++;
			}
		}
		else if (autonState == 7)
		{
			if( aimTimer.get() < 2)
			{
				Shooter.spinUpWheels(1);
			}
			
			else
			{
				Shooter.fire();
			}
		}
	}
	
	public static void portcullis()
	{
		
	}
	
	public static void ramparts()
	{
		if (autonState == 1)
		{
			intakeTimer.start();
			autonState++;
		}
		if (autonState == 2)
		{
			leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(ControlMode.PercentOutput, leftOutput);
			Intake.rightIntake.set(ControlMode.PercentOutput, -rightOutput);
			if(intakeTimer.get() >= 7)
			{
				Intake.leftIntake.set(ControlMode.PercentOutput, 0.0);
				Intake.rightIntake.set(ControlMode.PercentOutput, 0.0);
				autonState++;
			}
		}
		else if (autonState == 3)
		{
			DriveBase.resetDriveSensors();
			autonState++;
		}
		else if (autonState == 4)
		{
			Shooter.intake();
			if (DriveBase.getDriveDis() < Constants.TARGET_DEFENSE_DRIVE_DIS)
			{
				DriveBase.driveStraight(0.0,.85 );
			}
			else
			{
				DriveBase.driveArcade(0.0, 0.0);
				autonState++;
			}
		}
		
		else if (autonState == 5)
		{
			if (DriveBase.getDriveDirection() < 45)
			{
				error = 45 - DriveBase.getDriveDirection();
				if (error >= 180)
				{
					error -= 360;
				}
				else if (error<=-180)
				{
					error+=360;
				}
				double rotate = error*Constants.DRIVE_KP;
				if (rotate > .5)
				{
					rotate = .5;
				}
				DriveBase.driveArcade(0.0, -rotate);
			}
			else
			{
				DriveBase.driveArcade(0.0,0.0);
				autonState++;
			}
		}
		else if (autonState == 6)
		{
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getSelectedSensorPosition(0);
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			//if(error <= 50)
				//autonState++;

		}
		else if (autonState == 7)
		{
			if (Tracking.autoTarget())
			{
				startTimers();
				autonState++;
			}
		}
		else if (autonState == 7)
		{
			if( aimTimer.get() < 2)
			{
				Shooter.spinUpWheels(1);
			}
			
			else
			{
				Shooter.fire();
			}
		}
	}
	
	public static void oneBall()
	{
		if (autonState == 1)
		{
			speed = .73;//Preferences.getInstance().getDouble("autoSpeed", .73);
			intakeTimer.start();
			Tracking.flush();
			UDP.getData();
			autonState++;
		}
		if (autonState == 2)
		{
			leftError = Constants.LEFT_INTAKE_DOWN - Intake.leftPot.getVoltage();
			rightError = Constants.RIGHT_INTAKE_DOWN - Intake.rightPot.getVoltage();
			leftOutput = leftError * Constants.INTAKE_KP;
			rightOutput = rightError * Constants.INTAKE_KP;
			Intake.leftIntake.set(ControlMode.PercentOutput, leftOutput);
			Intake.rightIntake.set(ControlMode.PercentOutput, -rightOutput);
			if(Intake.leftPot.getVoltage() < Constants.LEFT_INTAKE_DOWN || intakeTimer.get() > Constants.MAX_INTAKE_DOWN_TIME)
			{
				Intake.leftIntake.set(ControlMode.PercentOutput, 0.0);
				Intake.rightIntake.set(ControlMode.PercentOutput, 0.0);
				Tracking.flush();
				autonState++;
			}
		}
		else if (autonState == 3)
		{
			DriveBase.resetDriveSensors();
			Tracking.flush();
			autonState++;
		}
		else if (autonState == 4)
		{
			Shooter.intake();
			if (DriveBase.getDriveDis() < Constants.TARGET_DEFENSE_DRIVE_DIS_LOW)
			{
				DriveBase.driveStraight(0.0,speed);
			}
			if (DriveBase.getDriveDis() >= Constants.TARGET_DEFENSE_DRIVE_DIS)
			{
				DriveBase.driveArcade(0.0, 0.0);
				autonState++;
				DriveBase.gyro.reset();
				turnTimer.start();
			}
		}
		
		else if (autonState == 5)
		{
			double ActualCurrentHeading = DriveBase.getDriveDirection();
			double error = 50 - ActualCurrentHeading;
			if (DriveBase.getDriveDirection() < 40)
			{
				if(error>=180)
				{
					error -= 360;
				}
				else if(error<=-180)
				{
					error += 360;
				}
				driveOutput = -1 * (error * Constants.DRIVE_KP);
				if(Math.abs(driveOutput) > .8)
				{
					if(driveOutput < 0)
					{
						driveOutput = -.8;
					}
					else if (driveOutput > 0)
					{
						driveOutput = .8;
					}
				}
				DriveBase.driveArcade(0.0, driveOutput);
			}
			if (Math.abs(error) < 4)
			{
				DriveBase.driveArcade(0.0,0.0);
				autonState++;				
			}
		}
		else if (autonState == 6) //Wait for the rotation to stop before taking a picture
		{
			//if(waitForStill.get() < 1)
			//{
			//	DriveBase.driveNormal(0.0, 0.0);
			//}
			//else
			//{
				Tracking.resetVision();
				autonState++;
			//}
		}
		else if (autonState == 7)
		{
			Tracking.autoTarget();
			if(intakeTimer.get() >= 10)
			{
				autonState++;
			}
		}
		else if (autonState == 8)
		{
			Constants.FAR_SHOT_COMP = Preferences.getInstance().getInt("Auton Shot", 2700);
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getSelectedSensorPosition(0);
			if(Math.abs(error) < 335)
			{
				errorRefresh = error + errorRefresh;
			}
			else 
			{
				errorRefresh = 0;
			}
			if (output > .85)
			{
				output = .85;
			}
			if (output < -.85)
			{
				output = -.85;
			}

			output = ((error * Constants.SHOOTER_LONG_KP) + (errorRefresh * Constants.SHOOTER_LONG_KI));
			Shooter.shooter.set(ControlMode.PercentOutput, output);
			if (Shooter.shooter.getSelectedSensorPosition(0) < 1900)
			{
				Shooter.flyWheel1.set(ControlMode.PercentOutput, .35);
				Shooter.flyWheel2.set(ControlMode.PercentOutput, -.35);
			}
			else
			{
				Shooter.spinUpWheels(-1);	
			}
			if (intakeTimer.get() >=13)
			{
				shooterLeverState = Constants.FIRE;
			}
			if (shooterLeverState == Constants.RESTING_POSITION)
			{
				Shooter.restShooterArm();
			}
			else if (shooterLeverState == Constants.FIRE)
			{
				
				if (Shooter.shooterLever.getSelectedSensorPosition(0) >= Constants.LEVER_MAX_SWING)
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
				if ((Shooter.shooterLever.getSelectedSensorPosition(0) <= 25))
				{
					Shooter.resetShooterArm();
				}
				else
				{
					shooterLeverState = Constants.RESTING_POSITION;
				}
			}
		}
	}
			
		
	
}
