
package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;


/*
 * This is a comment to test if Eclipse and Github work together nicely. HFS it works.
 */

public class Robot extends IterativeRobot 
{
	private int mode;
	private SendableChooser sc;
	public static CameraServer server;

    public void robotInit()
    {
    	TrajectoryTracking.getInstance();
    	DriveBase.getInstance();
    	Autonomous.getInstance();
    	TeleOperated.getInstance();
    	server = CameraServer.getInstance();
    	server.startAutomaticCapture();
    	sc = new SendableChooser();
    	sc.addDefault("Do Absolutely Nothing", 0);
    	sc.addObject("Do Nothing", 42);
    	sc.addObject("The Answer to Life", 1);
    	
    }

    public void autonomousInit()
    {
    	//Autonomous.setAutoMode((int)sc.getSelected());
    	//mode = (int) sc.getSelected();
    	//Autonomous.startTimer();
    	//DriveBase.resetEncoders();
    	//DriveBase.resetGyro();
    	if (Preferences.getInstance().getBoolean("DankAuton", false))
    	{
    		mode = Constants.REALLY_COOL_AUTON; 
    	}
    	
    	else
    	{
    		mode = Constants.NOT_SO_COOL_AUTON;
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
    }
    public void testPeriodic()
    {
    
    }
    
}
