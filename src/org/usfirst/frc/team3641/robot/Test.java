package org.usfirst.frc.team3641.robot;

public class Test 
{
	private static Test instance;
	private static double mp_error;
	private static double mp_position;
	private static double mp_velocity;
	private static boolean mp_forward;
	private static int mp_current_state;
	private static int last_mp_state;
	private static double accel_dist;
	
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
	
	public static void motionProfileGen(float accel, float cruiseVelocity, float targDist)
	{
		mp_position = DriveBase.getDriveDis();
		mp_velocity = DriveBase.getDriveSpeed();
		mp_error = targDist - mp_position;
		if (targDist < 0)
		{
			mp_forward = false;
		}
		if (targDist > 0)
		{
			mp_forward = true;
		}
		if (targDist == 0)
		{
			accel = 0;
		}
		if ((Math.abs(mp_velocity) >= cruiseVelocity) && (mp_current_state != Constants.DECEL))
		{
			mp_current_state = Constants.CRUISE;
		}
		if((mp_current_state == Constants.CRUISE) && (last_mp_state == Constants.ACCEL))
		{
			accel_dist = mp_position;
		}
		
		switch(mp_current_state)
		{
			case Constants.ACCEL:
				break;
			case Constants.CRUISE:
				break;
			case Constants.DECEL:
				break;
			case Constants.REST:
				break;
		}
		
	}
	
	public static void resetMP()
	{
		mp_error =0;
		mp_position = 0;
		DriveBase.resetEncoders();
		mp_current_state = Constants.REST;
	}
	public static void runTest()
	{
		
	}
}
