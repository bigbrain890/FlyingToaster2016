package org.usfirst.frc.team3641.robot;

public class Constants
{
	// Drive Controllers
	public static final int PS4_CONTROLLER = 0;
	public static final int ATTACK3 = 1;
	public static final int CONTROL_BOARD = 2;
	
	// Drive Motors
	public static final int LEFT_MOTOR_1 = 1;
	public static final int LEFT_MOTOR_2 = 2;
	public static final int RIGHT_MOTOR_1 = 3;
	public static final int RIGHT_MOTOR_2 = 4;

	
	// Encoders
	public static final int DRIVE_ENC_PORT_1 = 0;
	public static final int DRIVE_ENC_PORT_2 = 1;
	public static final int ANALOG_ENC_PORT_0 = 0;
	
	//Pi Port
	public static final int PI_PORT = 3641;
	//Ethan's Pi: "10.36.41.41"
	//Team Pi: "10.36.41.121"
	public static final String PI_IP_ADDR = "10.36.41.165";//"10.36.41.41"; Ethan's Pi
	public static final String PI_COMP_ADDR = "10.36.41.5";
	
	// Various Sensors 
	public static final int ULTRASONIC = 0;
	public static final double ULTRASONIC_CONVERSION_FACTOR = 0.009766;
	
	// Auton References
	public static final int LOW_BAR = 1;
	public static final int ROCK_WALL = 2;
	public static final int SHOVE_IN_FREEZER = 3;
	public static final int SALLY_PORT = 4;
	public static final int DRAW_BRIDGE = 5;
	public static final int MOAT = 6;
	public static final int ROUGH_TERRAIN = 7;
	public static final int PORTCULLIS = 8;
	public static final int RAMPARTS = 9;
	public static final int TARGET_DEFENSE_DRIVE_DIS = 190;
	public static final int TARGET_DEFENSE_DRIVE_DIS_LOW = 175;
	public static final int STRAIGHT = 0;
	
	// PID Contants
	public static final double DRIVE_KP = .13;
	public static final double DRIVE_KI = .0001;
	public static final double SHOOTER_KP = 0.00055;
	public static final double SHOOTER_KI = 0.0000023;
	public static final double OFFSET_KP = .07;
	public static final double INTAKE_KP = 0.0;
	public static final double INTAKE_KI = 0.0;
	public static final double KI_UPPER_LIMIT = 2300;
	public static final double KI_LOWER_LIMIT = -2300;
	
	// Camera stuff
	public static final int CAMERA_LINE_UP_COMP = 171;
	public static final int CAMERA_LINE_UP_PRACTICE = 170;
	public static final double DEGREES_PER_PIXEL = 0.2140625; //Life Cam
	//public static final double DEGREES_PER_PIXEL = 0.2375;  //PS3 Eye
	
	public static final double MIN_ANGLE_ERROR = 1;
	public static final int SEND_REQUEST = 0;
	public static final int RESPONSE_CAPTURE = 1;
	public static final int DO_MATH = 2;
	public static final int TURN_TO_TARGET = 3;
	public static final int TRACKED = 4;
	public static final int WAIT_FOR_STILL = 5;
	public static final double MOTION_THRESHOLD = .5;

	
	// Shooter stuff
	public static final int FLY_WHEEL_1 = 7;
	public static final int FLY_WHEEL_2 = 6;
	public static final int SHOOTER = 5;
	public static final int SHOOTER_LEVER = 8;
	public static final double SHOOTER_REVERSE = 9999; //TODO: ADD REAL VALUE HERE! Value of pot when shooter is vertical.
	public static final int SHOOTER_DOWN = 0;
	public static int FAR_SHOT_COMP = 2450;
	public static final int FAR_SHOT_PRACTICE = 2600;
	public static final int CAMERA_THRESHOLD_ANGLE = 1873;
	public static int MEDIUM_SHOT = 2400;
	public static int CLOSE_SHOT = 3450;
	public static final int SHOOTER_LIM_SWITCH = 9;
	
	// Intake Stuff
	public static final int INTAKE_LIM_SWITCH = 8;
	public static final int INTAKE_ARTICULATION_1 = 9;
	public static final int ROLLERS = 10;
	public static final int INTAKE_ARTICULATION_2 = 11;
	
	//Misc
	public static final double DRIVE_ENCODER_MULTIPLIER = 0.02454369; // Assuming 1:1 ratio between encoder and wheel.
	public static final int LIGHTING_CONTROLLER = 0;
	public static final double LIGHTING_FADE = 1.0;
	public static final double LIGHTING_THEATER_CHASE = 2.0;
	public static final double LIGHTING_RAINBOW = 3.0;
	public static final double LIGHTING_BLINK = 4.0;
	public static final double ROTATION_STALL_INPUT = .25;
	public static final int LEVER_MAX_SWING = -1000;
	public static final double LOW_GEAR_MULTIPLIER = .35;
	
	//Teleop State Variables
	public static final boolean DRIVE_NORMAL = false;
	public static final boolean DRIVE_TANK = true;
	public static final int RESTING_POSITION = 0;
	public static final int DO_DRIVE_BACK_MATH = 1;
	public static final int DRIVE_BACK = 2;
	public static final int UNPRESSED = -1;
	public static final double ROLL_BACK = 46.5;
	public static final int FIRE = 1;
	public static final int RESET = 2;
	
}
