
package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;

public class Robot extends IterativeRobot 
{
	private int mode;
	public static CameraServer server;

    public void robotInit()
    {
    	Tracking.getInstance();
    	DriveBase.getInstance();
    	Autonomous.getInstance();
    	TeleOperated.getInstance();
    	Shooter.getInstance();
    	PILoop.getInstance();
    	UDP.getInstance();
    	TCP.getInstance();
    	Manipulator.getInstance();
    	server = CameraServer.getInstance();
    	server.startAutomaticCapture();
    }

    public void autonomousInit()
    {
    	Autonomous.startTimers();
    	DriveBase.resetEncoders();
    	DriveBase.resetGyro();
    	if (Preferences.getInstance().getBoolean("Low Bar", false))
    	{
    		mode = Constants.LOW_BAR; 
    	}
    	
    	else if (Preferences.getInstance().getBoolean("Rock Wall", false))
    	{
    		mode = Constants.ROCK_WALL;
    	}
    	
    	else if (Preferences.getInstance().getBoolean("Shove in Freezer", false))
    	{
    		mode = Constants.SHOVE_IN_FREEZER;
    	}
    	
    	else if (Preferences.getInstance().getBoolean("Sally Port", false))
    	{
    		mode = Constants.SALLY_PORT;
    	}
    	
    	else if (Preferences.getInstance().getBoolean("Draw Bridge", false))
    	{
    		mode = Constants.DRAW_BRIDGE;
    	}
    	
    	else if (Preferences.getInstance().getBoolean("Moat", false))
    	{
    		mode = Constants.MOAT;
    	}
    	
    	else if (Preferences.getInstance().getBoolean("Rough Terrain", false))
    	{
    		mode = Constants.ROUGH_TERRAIN;
    	}
    	
    	else if (Preferences.getInstance().getBoolean("Portcullis", false))
    	{
    		mode = Constants.PORTCULLIS;
    	}
    	
    	else if (Preferences.getInstance().getBoolean("Ramparts", false))
    	{
    		mode = Constants.RAMPARTS;
    	}
   
    }
    
    public void autonomousPeriodic() 
    {
    	Autonomous.run(mode);
    }

	public void teleopPeriodic()
    {
		
        TeleOperated.runDriver();
        SmartDashboard.putNumber("Direction", DriveBase.getDriveDirection());
        SmartDashboard.putNumber("Drive Distance", DriveBase.getDriveDis());
    }
	
    public void testPeriodic()
    {
    	//Teh Codez Goes Here
    }
    
}
