package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Extreme3DPro extends Joystick
{
	public Extreme3DPro (int port)
	{
		super(port);
	}
	public double getXAxis()
	{
		return getRawAxis(0);
	}
	public double getYAxis()
	{
		return getRawAxis(1);
	}
	public double getZAxis()
	{
		return getRawAxis(2);
	}
	public double getThrottle()
	{
		return getRawAxis(3);
	}
	public boolean getButton(int x)
	{
		return getRawButton(x);
	}
}
