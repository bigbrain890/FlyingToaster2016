package org.usfirst.frc.team3641.robot;
import edu.wpi.first.wpilibj.Joystick;

public class CustomController extends Joystick 
{
	
	public CustomController(int port)
	{
		super(port);
	}
	
	public boolean getThumb()
	{
		return getRawButton(1);
	}
	
	public boolean getIndex()
	{
		return getRawButton(2);
	}
	
	public boolean getMiddle()
	{
		return getRawButton(3);
	}
	
	public boolean getRing()
	{
		return getRawButton(4);
	}
	
	public boolean getPinkie()
	{
		return getRawButton(5);
	}
	
	public double getShooterPos()
	{
		return getRawAxis(1);
	}
}
