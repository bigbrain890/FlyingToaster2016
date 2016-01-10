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
		return getRawAxis(3);
	}
	public double getRightTriggerAxis()
	{
		return getRawAxis(4);
	}
	public boolean getLeftBumper()
	{
		return getRawButton(5);
	}
	public boolean getRightBumper()
	{
		return getRawButton(6);
	}
	
}
