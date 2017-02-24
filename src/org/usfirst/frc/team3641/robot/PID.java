package org.usfirst.frc.team3641.robot;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID
{
	public static final int OFF = 0, PROPORTIONAL = 1, CONSTANT = 2;
	
	private static ArrayList<PID> instances = new ArrayList<PID>();

	private double errorRefresh, lastError;
	private double kP, kI, kD, kFF, deadband, cap;
	private int feedForwardMode;
	private String name;

	/**
	 * Initializes a new instance of the PID class.
	 * 
	 * @param name The name of the config file to read from and the name to print for debugging
	 * purposes.
	 */
	public PID(String name)
	{
		this.name = name;
		instances.add(this);
		reset();
	}
	
	public void setICap(double cap)
	{
		this.cap = cap;
	}
		
	/**
	 * Sets the backup values to use if they can't be read from the config file.
	 * 
	 * @param BkP THe backup kP.
	 * @param BkI The backup kI.
	 * @param BkD The backup kD.
	 * @param BkFF The backup feed forward.
	 * @param BfeedForwardMode The backup feed forward mode.
	 * @param Bdeadband The backup deadband range.
	 */
	public void setValues(double BkP, double BkI, double BkD, double BkFF, int BfeedForwardMode, double Bdeadband)
	{
		this.kP = BkP;
		this.kI = BkI;
		this.kD = BkD;
		
		this.kFF = BkFF;
		this.feedForwardMode = BfeedForwardMode;
		
		this.deadband = Bdeadband;
	}
	
	/**
	 * Sets the most of the backup values to use if they can't be read from the config file.
	 * The backup deadband will be set to 0.
	 * 
	 * @param BkP THe backup kP.
	 * @param BkI The backup kI.
	 * @param BkD The backup kD.
	 * @param BkFF The backup feed forward.
	 * @param BfeedForwardMode The backup feed forward mode.
	 */
	public void setValues(double BkP, double BkI, double BkD, double BkFF, int BfeedForwardMode)
	{
		setValues(BkP, BkI, BkD, BkFF, BfeedForwardMode, 0);
	}
	
	/**
	 * Sets the most of the backup values to use if they can't be read from the config file.
	 * The backup feed forward will be set to off.
	 * 
	 * @param BkP THe backup kP.
	 * @param BkI The backup kI.
	 * @param BkD The backup kD.
	 * @param Bdeadband The backup deadband.
	 */
	public void setValues(double BkP, double BkI, double BkD, double Bdeadband)
	{
		setValues(BkP, BkI, BkD, 0, 0, Bdeadband);
	}

	
	/**
	 * Runs the PID loop.
	 * 
	 * @param error The error to target.
	 * @param target The target value (used for proportional feed forward).
	 * @return Motor output power.
	 */
	public double run(double error, double target)
	{
		if(deadband != 0)
		{
			if(Math.abs(error) <= deadband) errorRefresh += error;
			else errorRefresh = 0;
		}
		else errorRefresh += error;
		
		if(cap != 0 && Math.abs(errorRefresh) >= cap)
		{
			if(errorRefresh < 0) errorRefresh = -cap;
			else errorRefresh = cap;
		}
				
		double output = (error * kP) + (errorRefresh * kI) + ((error-lastError) * kD);
		lastError = error;
		
		if(feedForwardMode == PROPORTIONAL) output += (target/kFF);
		else if(feedForwardMode == CONSTANT)
		{
			if(output > 0) output += kFF;
			else if(output < 0) output -= kFF;
		}

		SmartDashboard.putNumber(name + " P", error * kP);
		SmartDashboard.putNumber(name + " I", errorRefresh * kI);
		SmartDashboard.putNumber(name + " D", lastError * kD);
		
		return output;
	}

	/**
	 * Runs the PID loop.
	 * If using proportional feed forward, since there is no target it is ignored.
	 * 
	 * @param error The error to target.
	 * @return Motor output power.
	 */
	public double run(double error)
	{
		return run(error, 0);
	}
			
	/**
	 * Resets the PID Loop.
	 * Sets the error over time and the last error to 0.
	 */
	public void reset()
	{
		errorRefresh = 0;
		lastError = 0;
	}

}
