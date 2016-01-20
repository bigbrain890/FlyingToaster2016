package org.usfirst.frc.team3641.robot;
import edu.wpi.first.wpilibj.AnalogInput;

public class Encoder
{
	private static AnalogInput input0;
	private static Encoder instance;
	
	private Encoder()
	{
		input0 = new AnalogInput(Constants.ANALOG_ENC_PORT_0);
	}
	
	public static int getEncoderPos(int EncoderNum)
	{
		int encValue = 0;
		switch(EncoderNum)
		{
			case 0:
				 encValue = input0.getValue();
				break;
		}
		return encValue;
	}
	
	public static Encoder getInstance()
	{
		if(instance == null)
		{
			instance = new Encoder();
		}
		return instance;
	}
}
