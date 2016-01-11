
package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;

/*
 * This is a comment to test if Eclipse and Github work together nicely
 */

public class Robot extends IterativeRobot 
{
	private SendableChooser sc;

    public void robotInit()
    {
    	TrajectoryTracking.getInstance();
    	DriveBase.getInstance();
    	Autonomous.getInstance();
    	TeleOperated.getInstance();
    	sc = new SendableChooser();
    	sc.addObject("Do Nothing", 1);
    	sc.addObject("The Answer to Life", 42);
    	
    }

    public void autonomousInit()
    {
    	Autonomous.setAutoMode((int)sc.getSelected());
    	Autonomous.startTimer();
    	DriveBase.resetEncoders();
    	DriveBase.resetGyro();
    }
    
    public void autonomousPeriodic() 
    {
    	Autonomous.run();
    }

    public void teleopPeriodic()
    {
        
    }
    
    public void testPeriodic()
    {
    
    }
    
}
