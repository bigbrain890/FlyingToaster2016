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
		UDP.sendData("Request: Binary");
		String response = "";
		response = UDP.getData();
		UDP.sendData("Recieved: " + response);
		if(response != null)
		{
			if (response.contains("1"))
			{
				DriveBase.driveNormal(0.3, 0);
			}	
			else if (response.contains("0"))
			{
				DriveBase.driveNormal(-0.3, 0);
			}
		} 
	}
}
