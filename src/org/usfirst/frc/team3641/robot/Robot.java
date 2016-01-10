
package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private SendableChooser sc;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
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
    public void autonomousPeriodic() {
    	Autonomous.run();
    }

    public void teleopPeriodic() {
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
