package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous
{
	private static double error=0, errorRefresh=0, output=0;

	private static Autonomous instance;
	private static Timer autoTimer, aimTimer;
	public static int autonState = 1;
	
	private Autonomous()
	{
		autoTimer = new Timer();
		aimTimer = new Timer();
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
			if (DriveBase.getDriveDis() < Constants.TARGET_DEFENSE_DRIVE_DIS)
			{
				DriveBase.driveStraight(0.0,.55 );
			}
			else
			{
				DriveBase.driveNormal(0.0, 0.0);
				autonState++;
			}
		}
		
		else if (autonState == 2)
		{
			if (DriveBase.getDriveDirection() < 45)
			{
				error = 45 - DriveBase.getDriveDirection();
				double rotate = error*Constants.DRIVE_KP;
				if (rotate > .5)
				{
					rotate = .5;
				}
				DriveBase.driveNormal(0.0, -rotate);
			}
			else
			{
				DriveBase.driveNormal(0.0,0.0);
				autonState++;
			}
		}
		else if (autonState == 3)
		{
			error = Constants.FAR_SHOT_COMP - Shooter.shooter.getEncPosition();
			errorRefresh = error + errorRefresh;
			output = ((error * Constants.SHOOTER_KP) + (errorRefresh * Constants.SHOOTER_KI));
			Shooter.shooter.set(output);
			//if(error <= 50)
				//autonState++;

		}
		else if (autonState == 4)
		{
			if (Tracking.autoTarget())
			{
				startTimers();
				autonState++;
			}
		}
		else if (autonState == 5)
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
	
	public static void rockWall()
	{
		DriveBase.driveNormal(-1, 0.0);
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
		
	}
	
	public static void roughTerrain()
	{
		
	}
	
	public static void portcullis()
	{
		
	}
	
	public static void ramparts()
	{
		
	}
}
