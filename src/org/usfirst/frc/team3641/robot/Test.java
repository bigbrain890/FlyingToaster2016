package org.usfirst.frc.team3641.robot;

public class Test 
{
	private static Test instance;
	
	private Test()
	{
		
	}
	
	public static Test getInstance()
	{
		if (instance == null)
		{
			instance = new Test();
		}
		return instance;
	}
	
	public static void runTest()
	{
		
	}
}
