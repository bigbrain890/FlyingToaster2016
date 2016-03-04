package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.AnalogOutput;

public class Lighting {
	public static Lighting instance;
	public static AnalogOutput lightController;
	
	public Lighting()
	{
		lightController = new AnalogOutput(Constants.LIGHTING_CONTROLLER);
	}
	
	public static Lighting getInstance()
	{
		if(instance == null)
		{
			instance = new Lighting();
		}
		return instance;
	}
	
	public static void fade()
	{
		lightController.setVoltage(Constants.LIGHTING_FADE);
	}
	
	public static void theaterChase()
	{
		lightController.setVoltage(Constants.LIGHTING_THEATER_CHASE);
	}
	
	public static void rainbow()
	{
		lightController.setVoltage(Constants.LIGHTING_RAINBOW);
	}
	
	public static void blink()
	{
		lightController.setVoltage(Constants.LIGHTING_BLINK);
	}
	
}
