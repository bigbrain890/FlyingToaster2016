package org.usfirst.frc.team3641.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
public class Intake {

	private static Intake instance;
	public static CANTalon rollers, intake1, intake2;
	public static DigitalInput limSwitch;
	
	public Intake()
	{
		rollers = new CANTalon(Constants.ROLLERS);
		intake1 = new CANTalon(Constants.INTAKE_ARTICULATION_1);
		intake2 = new CANTalon(Constants.INTAKE_ARTICULATION_2);
		limSwitch = new DigitalInput(Constants.INTAKE_LIM_SWITCH);
	}
	
	public static Intake getInstance()
	{
		if(instance == null)
		{
			instance = new Intake();
		}
		return instance;
	}
	
	public static void intakeBall()
	{
		rollers.set(Preferences.getInstance().getDouble("Intake Speed", 1.0));
	}
	
	public static void stopIntake()
	{
		rollers.set(0.0);
	}
	
	public static void lowGoal()
	{
		rollers.set(-1);
	}
	
	public static void setDown()
	{
		if(limSwitch.get() == false)
		{
			intake1.set(-.5);
			intake2.set(-.5);
		}
		else
		{
			intake1.set(0);
			intake2.set(0);
		}
	}
	
}
