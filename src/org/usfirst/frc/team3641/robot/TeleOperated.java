package org.usfirst.frc.team3641.robot;

public class TeleOperated
{
	private static TeleOperated instance;
	public static PS4Controller dualShock;
	public static Attack3 operator;
	static boolean driveMode;
	
	private TeleOperated()
	{
		dualShock = new PS4Controller(Constants.PS4_CONTROLLER);
		operator = new Attack3(Constants.ATTACK3);
	}
	
	public static TeleOperated getInstance()
	{
		if(instance == null)
		{
			instance = new TeleOperated();
		}
		return instance;
	}
	
	public static void runDriver()
	{
		if (dualShock.getRightBumper() == true)
		{
			
		}
		DriveBase.driveNormal(dualShock.getLeftStickYAxis(), 0.0);
		Shooter.feedToShooter(dualShock.getRightStickYAxis());
	}
}
