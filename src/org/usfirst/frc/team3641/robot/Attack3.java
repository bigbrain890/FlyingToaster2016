package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Attack3 extends Joystick
{
	public Attack3 (int port)
	{
		super(port);
	}
	
	public boolean getBaseFrontLeft()
	{
		return getRawButton(6);
	}
	
	public boolean getBaseBackLeft()
	{
		return getRawButton(7);
	}
	
	public boolean getBaseFrontRight()
	{
		return getRawButton(11);
	}
	
	public boolean getBaseCenterLeft()
	{
		return getRawButton(8);
	}
	
	public boolean getBaseCenterRight()
	{
		return getRawButton(9);
	}
	
	public boolean getThumbLeft()
	{
		return getRawButton(4);
	}
	
	public boolean getThumbRight()
	{
		return getRawButton(5);
	}
	
	public boolean getThumbBottom()
	{
		return getRawButton(2);
	}
	
	public boolean getThumbTop()
	{
		return getRawButton(3);
	}
	
	public boolean getIndexTrigger()
	{
		return getRawButton(1);
	}
}
