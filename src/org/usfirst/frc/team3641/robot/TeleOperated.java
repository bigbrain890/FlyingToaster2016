package org.usfirst.frc.team3641.robot;

public class TeleOperated
{
	private static TeleOperated instance;
	public static PS4Controller dualShock;
	public static Attack3 operator;
	
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
<<<<<<< HEAD
		int UDPData = UDP.getData();
		if(UDPData == 1)
=======
		UDP.sendData("Request: Binary");
		String response = UDP.getData();
		//int UDPData = Integer.parseInt(UDP.getData());
		if(response.equals("1"))
>>>>>>> origin/master
		{
			DriveBase.driveNormal(0.3, 0.0);
		}
		else if(response.equals("0"))
		{
			DriveBase.driveReverse(0.3, 0.0);
		}
	}
}
