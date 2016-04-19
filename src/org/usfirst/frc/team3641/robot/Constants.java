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
	public static final int LEFT_SHOOTER_ENCODER_1 = 7;
	public static final int LEFT_SHOOTER_ENCODER_2 = 6;
	public static final int RIGHT_SHOOTER_ENCODER_1 = 5;
	public static final int RIGHT_SHOOTER_ENCODER_2 = 4;
	public static final int SHOOTER_LIM_SWITCH = 9;
	public static final int BALL_SENSOR = 8;
	public static final int LEFT_POT = 3;
	public static final int RIGHT_POT = 2;
	
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
	public static final int ONE_BALL = 10;
	public static final int TARGET_DEFENSE_DRIVE_DIS = 190;
	public static final int TARGET_DEFENSE_DRIVE_DIS_LOW = 175;
	public static final double FINAL_BATTER_SHOT_DIS = 295.206;
	public static final double FINAL_BATTER_SHOT_ANGLE = 66.81;
	public static final int AUTON_BALL_SETPOINT = 3650;
	public static final int STRAIGHT = 0;
	public static final double MAX_INTAKE_DOWN_TIME = 1.5;
	public static final double KI_DEADBAND_THRESHOLD = 3;
	public static final double SENSOR_AUTON_GYRO_ROTATION = 15;
	
	// PID Contants
	public static final double DRIVE_KP = .13;
	public static final double DRIVE_KI = .0001;
	public static final double SHOOTER_KP = 0.00055;
	public static final double SHOOTER_KI = 0.0000023;
	public static final double OFFSET_KP = .16;
	public static final double INTAKE_KP = 0.25;
	public static final double INTAKE_KI = 0.0;
	public static final double KI_UPPER_LIMIT = 2300;
	public static final double KI_LOWER_LIMIT = -2300;
	public static final double FLYWHEEL_KP = .13;
	public static final double FLYWHEEL_KI = .0001;
	public static final double CASTLE_WALL_SHOT_SPEED = 900000; //TODO: FIND SPEED
	
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
	public static final int TURN_TO_TARGET_NO_PID = 42;
	public static final int TRACKED = 4; 
	public static final int WAIT_FOR_STILL = 5;
	public static final double MOTION_THRESHOLD = .25;

	
	// Shooter stuff
	public static final double SHOOTER_DOWN = 0.0;
	public static final int FLY_WHEEL_1 = 7;
	public static final int FLY_WHEEL_2 = 6;
	public static final int SHOOTER = 5;
	public static final int SHOOTER_LEVER = 8;
	public static int FAR_SHOT_COMP = 2450;
	public static int MEDIUM_SHOT = 2400;
	public static int CLOSE_SHOT = 3450;
	public static int CASTLE_WALL_SHOT = 3750;
	public static final int FAR_SHOT_PRACTICE = 2600;
	public static final int CAMERA_THRESHOLD_ANGLE = 1873;
	public static double ACCEPTABLE_ERROR = 1;
	
	// Intake Stuff
	public static final double LEFT_INTAKE_DOWN = .45;
	public static final double RIGHT_INTAKE_DOWN = .5;
	public static final double LEFT_INTAKE_UP = 3.05 ;
	public static final double RIGHT_INTAKE_UP = 3.15 ;
	public static final int INTAKE_DOWN = 0;
	public static final int INTAKE_UP = 1;
	public static final int INTAKE_REST = 2;
	public static final int RIGHT_INTAKE_MOTOR = 9;
	public static final int ROLLERS = 10;
	public static final int LEFT_INTAKE_MOTOR = 11;
	public static final int WINCH_ONE = 12; //Left Side
	public static final int WINCH_TWO = 13; //Right Side
	
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
	public static final int FLASHLIGHT_SPIKE = 3;
	
	//Climber
	public static final int WINCH_DISTANCE = 3641; //TODO: Find Real Distance
	public static final int FIRE_HARPOON = 0;
	public static final int FIND_WINCH_OFFSET = 1;
	public static final int WINCH_UP = 2;
	public static final int DONE_CLIMBING = 3641;
	public static final int WINCH_VELOCITY = 9001; //TODO: Adjust before using (IT'S OVER NINE-THOUSAND!)
	public static final double WINCH_KP = 0.0;
	public static final double WINCH_KI = 0.0;

	
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
