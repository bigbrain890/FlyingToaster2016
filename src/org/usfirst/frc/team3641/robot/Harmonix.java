package org.usfirst.frc.team3641.robot;
import java.util.EnumMap;
import edu.wpi.first.wpilibj.Joystick;

public class Harmonix
{
	private EnumMap<Button, Boolean> current, last;
	private EnumMap<Axis, Double> axes;
	private Joystick rawJoystick;

	/**
	 * Initalize the joysitck with the port.
	 * 
	 * @param port The port it uses on the driver station.
	 */
	public Harmonix(int port)
	{
		rawJoystick= new Joystick(port);
		current = new EnumMap<Button, Boolean>(Button.class);
		last = new EnumMap<Button, Boolean>(Button.class);
		axes = new EnumMap<Axis, Double>(Axis.class);
		poll(); //Populate the current EnumMap so the last EnumMap won't be null when the user polls for the first time.
	}
	
	/**
	 * The buttons it supports
	 */
	public static enum Button
	{
		BLUE, GREEN, RED, YELLOW, ORANGE,
		LOWER,
		STRUM;
	}
	
	/**
	 * The axes it supports
	 */
	public enum Axis
	{
		STRUM, WHAMMY_BAR, BUTTONS;
	}

	/**
	 * Returns the value of the specified axis.
	 * 
	 * @param axis The axis to read.
	 * @return The value of said axis.
	 */
	public double getAxis(Axis axis)
	{
		return axes.get(axis);
	}
		
	/**
	 * Checks if the specified button is down at all.
	 * 
	 * @param button The button to read.
	 * @return True if the button is down.
	 */
	public boolean isDown(Button button)
	{
		return current.get(button);
	}
	
	/**
	 * Checks if the specified button has just been pressed.
	 * 
	 * @param button The button to read.
	 * @return True on the rising edge of the button.
	 */
	public boolean isPressed(Button button)
	{
		return (current.get(button) && !last.get(button));
	}
	
	/**
	 * Checks if the specified button has just been released.
	 * 
	 * @param button The button to read.
	 * @return True on the falling edge of the button.
	 */
	public boolean isReleased(Button button)
	{
		return (!current.get(button) && last.get(button));
	}
	
	/**
	 * Read the current state of each button and axis.
	 */
	public void poll()
	{
		last = current.clone();

		if(rawJoystick.getPOV(0) == 0) axes.put(Axis.STRUM, 1.0);
		else if(rawJoystick.getPOV(0) == 180) axes.put(Axis.STRUM, -1.0);
		else axes.put(Axis.STRUM, 0.0);

		double wb = rawJoystick.getRawAxis(2);
		if(wb == -0.0078125) wb = 0;
		else wb = (wb+1)/2;
		
		axes.put(Axis.WHAMMY_BAR, wb);
		
		current.put(Button.STRUM, !(axes.get(Axis.STRUM) == 0));
		current.put(Button.GREEN, rawJoystick.getRawButton(2));
		current.put(Button.RED, rawJoystick.getRawButton(3));
		current.put(Button.YELLOW, rawJoystick.getRawButton(4));
		current.put(Button.BLUE, rawJoystick.getRawButton(1));
		current.put(Button.ORANGE, rawJoystick.getRawButton(5));

		current.put(Button.LOWER, rawJoystick.getRawButton(7));
		
		axes.put(Axis.BUTTONS, buttonsToAxis());
	}

	/**
	 * Converts the current value of the frets to an axis.
	 * 
	 * @return A number based on which frets are down.
	 */
	public double buttonsToAxis()
	{
		double rotation = 0;
		double numberOfButtons = 0;
		if(!isDown(Button.LOWER))
		{
			if(isDown(Button.GREEN))
			{
				rotation += 1;
				numberOfButtons++;
			}
			if(isDown(Button.RED))
			{
				rotation += .5;
				numberOfButtons++;
			}
			if(isDown(Button.YELLOW))
			{
				rotation += 0;
				numberOfButtons++;
			}
			if(isDown(Button.BLUE))
			{
				rotation -= .5;
				numberOfButtons++;
			}
			if(isDown(Button.ORANGE))
			{
				rotation -= 1;
				numberOfButtons++;
			}
			if(numberOfButtons != 0) rotation /= numberOfButtons;
		}
		return rotation;
	}
}
