package org.usfirst.frc.team3641.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber
{
	private static Climber instance;
	public static TalonSRX winch1, winch2;
	public static int climbState;
	public static int initialWinch1Pos, initialWinch2Pos;
	public static int winch1Pos, winch2Pos;
	public static int winch1V, winch2V;
	public static int winch1Error, winch2Error;
	public static int winch1ErrorRefresh, winch2ErrorRefresh;
	public static double winch1Output, winch2Output;
	public static boolean winch1Done = false, winch2Done = false;
	
	private Climber()
	{
		winch1 = new TalonSRX(Constants.WINCH_ONE);
		winch2 = new TalonSRX(Constants.WINCH_TWO);
		climbState = Constants.FIND_WINCH_OFFSET;
		
	}
	
	public static Climber getInstance()
	{
		if(instance == null)
		{
			instance = new Climber();
		}
		return instance;
	}
	
	public static int climb()
	{
		if(climbState == Constants.FIND_WINCH_OFFSET)
		{
			initialWinch1Pos = winch1.getSelectedSensorPosition(0);
			initialWinch1Pos = winch2.getSelectedSensorPosition(0);
			
			climbState = Constants.WINCH_UP;
		}
		if(climbState == Constants.WINCH_UP)
		{
			winch1Pos = winch1.getSelectedSensorPosition(0);
			winch2Pos = winch1.getSelectedSensorPosition(0);
			
			winch1V = winch1.getSelectedSensorVelocity(0);
			winch2V = winch1.getSelectedSensorVelocity(0);
			
			winch1Error = Constants.WINCH_VELOCITY - winch1V;
			winch2Error = Constants.WINCH_VELOCITY + winch2V;
			
			winch1ErrorRefresh += winch1Error;
			winch2ErrorRefresh += winch2Error;
			
			winch1Output = (((winch1Error * Constants.WINCH_KP) + (winch1ErrorRefresh * Constants.WINCH_KI)));
			winch2Output = -(((winch2Error * Constants.WINCH_KP) + (winch2ErrorRefresh * Constants.WINCH_KI)));
			
			if(winch1Pos < initialWinch1Pos+Constants.WINCH_DISTANCE)
			{
				winch1.set(ControlMode.PercentOutput, winch1Output);
			}
			else
			{
				winch1.set(ControlMode.PercentOutput, 0.0);
				winch1Done = true;
			}
			
			if(winch2Pos > initialWinch1Pos-Constants.WINCH_DISTANCE)
			{
				winch2.set(ControlMode.PercentOutput, winch1Output);
			}
			else
			{
				winch2.set(ControlMode.PercentOutput, 0.0);
				winch2Done = true;
			}
			
			if(winch1Done && winch2Done)
			{
				climbState = Constants.DONE_CLIMBING;
			}
			
		}
		return climbState;
	}

}