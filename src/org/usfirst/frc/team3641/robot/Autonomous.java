package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous
{
	private static Autonomous instance;
	private static Timer autoTimer;
	
	private Autonomous()
	{
		autoTimer = new Timer();
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
	
	public static void startTimer()
	{
		autoTimer.reset();
		autoTimer.start();
	}
	
	public static void lowBar()
	{
		DriveBase.driveNormal(1, 0.0);
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
