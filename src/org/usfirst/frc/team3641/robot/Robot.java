
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
    	TrajectoryTracking.getInstance();
    	DriveBase.getInstance();
    	Autonomous.getInstance();
    	TeleOperated.getInstance();
    	server = CameraServer.getInstance();
    	server.startAutomaticCapture();
    }

    public void autonomousInit()
    {
    	Autonomous.startTimer();
    	DriveBase.resetEncoders();
    	DriveBase.resetGyro();
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
        SmartDashboard.putNumber("Ultrasonic", TrajectoryTracking.getRawUltrasonic());
    }
	
    public void testPeriodic()
    {
    	//Teh Codez Goes Here
    }
    
}
