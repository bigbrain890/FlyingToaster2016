
package org.usfirst.frc.team3641.robot;
import edu.wpi.first.wpilibj.Joystick;

public class PS4Controller extends Joystick
{
	
	public PS4Controller(int port)
	{
		super(port);
	}
	
	public double getLeftStickYAxis()
	{
		return getRawAxis(1);
	}
	
	public double getLeftStickXAxis()
	{
		return getRawAxis(0);
	}
	
	public double getRightStickYAxis()
	{
		return getRawAxis(5);
	}
	
	public double getRightStickXAxis()
	{
		return getRawAxis(2);
	}
	
	public double getLeftTriggerAxis()
	{
		double output = (getRawAxis(3) + .5) / 2;
		return output;
	}
	
	public double getRightTriggerAxis()
	{
		double output = (getRawAxis(4) + .5) / 2;
		return output;
	}
	
	public boolean getSquareButton()
	{
		return getRawButton(1);
	}
	
	public boolean getXButton()
	{
		return getRawButton(2);
	}
	
	public boolean getCircleButton()
	{
		return getRawButton(3);
	}
	
	public boolean getTriangleButton()
	{
		return getRawButton(4);
	}
	
	public boolean getLeftBumper()
	{
		return getRawButton(5);
	}
	
	public boolean getRightBumper()
	{
		return getRawButton(6);
	}
	
	public boolean getLeftThrottleButton()
	{
		return getRawButton(7);
	}
	
	public boolean getRightThrottleButton()
	{
		return getRawButton(8);
	}
	
	public boolean getShareButton()
	{
		return getRawButton(9);
	}
	
	public boolean getOptionsButton()
	{
		return getRawButton(10);
	}
	
	public boolean getLeftAnalogStickButton()
	{
		return getRawButton(11);
	}
	
	public boolean getRightAnalogStickButton()
	{
		return getRawButton(12);
	}
	
	public boolean getPlayStationButton()
	{
		return getRawButton(13);
	}

	
}
