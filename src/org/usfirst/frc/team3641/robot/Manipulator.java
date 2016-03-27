package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class Manipulator {
	private static Manipulator instance;
	public static CANTalon manipulator1, manipulator2;
	
	public Manipulator()
	{
		manipulator1 = new CANTalon(Constants.MANIPULATOR1);
		manipulator2 = new CANTalon(Constants.MANIPULATOR2);
	}
	
	public static Manipulator getInstance()
	{
		if(instance == null)
		{
			instance = new Manipulator();
		}
		return instance;
	}

	public static void up()
	{
		manipulator1.set(-.375);
		manipulator2.set(.375);
	}
	
	public static void down()
	{
		manipulator1.set(.375);
		manipulator2.set(-.375);
	}
}
