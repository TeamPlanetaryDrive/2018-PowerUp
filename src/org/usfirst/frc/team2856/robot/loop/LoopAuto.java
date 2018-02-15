package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopAuto extends Loop{	

	static String[] modes = {"l", "m", "r", "s"};
	
	//IterativeRobot robot;
	private String autoSelected;
	private Integer state;
	private DriveTrain drive;
	// private double startPos;
	private SendableChooser<String> chooser;
	private double startTime;
	// Getting Game-Specific data
	private String gameSides = DriverStation.getInstance().getGameSpecificMessage();
	private boolean gameSideSwitch;
	private boolean gameSideScale;
	
	
	//Names of our options for Autonomous
	private final String 
		chooserTest = "Test",
		chooserSwitch = "Switch",
		chooserScale = "Scale",
		chooserForward = "Cross the Line";
	
	private String choosenCommand = null;
	
	public LoopAuto(Robot rob){
		//First instantiating through the parent class
		super(rob);

		// Then adding options for Autonomous mode
		chooser = new SendableChooser<String>();
		chooser.addDefault(chooserTest, chooserTest);
		chooser.addObject(chooserSwitch, chooserSwitch);
		chooser.addObject(chooserScale, chooserScale);
		chooser.addObject(chooserForward, chooserForward);
		SmartDashboard.putData("Auto modes", chooser);

		/*
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			//Put left auto code here
			System.out.println("Left");
		} else {
			//Put right auto code here
			System.out.println("Right");
		}
		*/
	}
	
	public void init() {
		autoSelected = SmartDashboard.getString("Auto Selector", "None");
		System.out.println("Auto selected: " + autoSelected);
		
		switch(autoSelected) {
			case chooserForward:
				choosenCommand = "Forward";
				break;
			case chooserSwitch:
				choosenCommand = "Switch";
				break;
			case chooserScale:
				choosenCommand = "Scale";
				break;
			case chooserTest:
				choosenCommand = "Test";
				break;
			default:
				choosenCommand = "Test";
				break;	
		}
		
		state = 0;

		// startPos = Double.parseDouble(SmartDashboard.getString("Starting
		// Position", "0"));
		if (gameSides.charAt(0) == 'L') {
			gameSideSwitch = true;
		}
		if (gameSides.charAt(1) == 'L') {
			gameSideScale = true;
		}
		
		drive = robot.driveTrain;
		drive.initAuto();

		// Gyro for tracking direction of the robot
//		robot.gyro.reset();
//		robot.gyro.calibrate();
	}
	
	public void loop() {
		this.switchAuto(choosenCommand);
		drive.update(false);
	}

	public static void addModes() {
		for (int i = 0; i < modes.length; i++) {
			SmartDashboard.putString("Auto Selector", modes[i]);
		}
	}
	
	//Controls the switching of the functions in Auto
	//E.g. putting power boxes on the switch
	public void switchAuto(String mode) {
		switch (mode) {
			case "Test":
				this.testingAuto(0, false);
				break;
			case "Switch":
				this.depositAtSwitch(0, gameSideSwitch);
				break;
			case "Scale":
				this.depositAtScale(0, gameSideScale);
				break;
			case "Forward":
				 this.crossLine(0);
				break;
			default:
				break;
		}
	}

	// in memoriam: the stateMachine(). 2017-2018. lay here until the day of
	// it's death.
	// a fickle and confusing creature, but nonetheless a good friend until the
	// very end

	public void adjust() {
		// Adjust the robot back on track
	}

	public void testingAuto(double start, boolean side){
		if (state == 0) {
			if (!robot.driveTrain.moveGetActive()) {
				System.out.println(state);
				System.out.println("driving forward");
				//previous parameter value: 5
				robot.driveTrain.moveTurn(90*1.25, 0);
				state++;
			}
			return;
		}
		if (state == 1) {
			if (!robot.driveTrain.moveGetActive()) {
				System.out.println(state);
				System.out.println(state);
				//previous first parameter value: 90*1.25
				robot.driveTrain.moveStraight(2);
				state++;
			}
			return;
		}
		if(state == 2) {
			state++;
			System.out.println(state);
		}
		
	}
	
	public void depositAtSwitch(double start, boolean side) { // left = true,
																// right = false
		if (state == 0) {
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(5); // clear any obstacles
				state++;
			}
			return;
		}
		// align bot with switch
		if (side) { 
			// do we have the left switch . . .
			if (start > -4.5) { 
				// if we start to the right of the switch

				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {

						robot.driveTrain.moveStraight(start + 9.5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {

						robot.driveTrain.moveTurn(90, 0);
						state++;
					}
					return;
				}

			} else if (start < -9.5) { // if we start to the left of the switch
				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(90, 0);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(-start - 9.5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}
			} // do nothing if we start directly adjacent to the switch

		} else { // . . . or the right switch
			if (start > 4.5) { // if we start to the right of the switch
				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(start - 9.5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(90, 0);
						state++;
					}
					return;
				}
				// if we start to the left of the switch
			} else if (start < 9.5) {
				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(90, 1);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						System.out.println("moved to ");
						robot.driveTrain.moveStraight(-start + 9.5);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}

			}
			// do nothing if we start directly adjacent to the switch

		}

		if (state == 4) {
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(6.66 - Constants.DRIVE_BASE_LENGTH);
				state++;
			}
			return;
		}

		// deposit the cube
		if (state == 5) {
			startTime = System.currentTimeMillis();
			// update on time required
			robot.lift.liftUp(1);
			state++;
			return;
		}
		if (state == 6) {
			if (System.currentTimeMillis() - startTime > 3000) {
				robot.lift.liftStop();
				state++;
			}
			return;
		}
		if (state == 7) {
			robot.manipulator.pullOut(1);
			state++;
			return;
		}

	}

	public void depositAtScale(double start, boolean side) { // left = true,
																// right = false
		if(state == 0){
			if(!robot.driveTrain.moveGetActive()){
				robot.driveTrain.moveStraight(5); // clear any obstacles
				state++;
			}
			return;
		}
		
		// Align robot with the scale
		if(side){ // do we have the left scale . . .
			if(state == 1){
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(-90, 0);
					state++;
				}
				return;
			}

			if(state == 2){
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(start + 11);
					state++;
				}
				return;
			}

			if(state == 3){
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(90, 0);
					state++;
				}
				return;
			}

		} 
		else{ // . . . or the right scale
			if(state == 1){
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(90, 0);
					state++;
				}
				return;
			}
			if(state == 2){
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(start + 11);
					state++;
				}
				return;
			}
			if(state == 3){
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(-90, 0);
					state++;
				}
				return;
			}

		}
		
		// Move to the center of the arena
		if(state == 4){
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(22 - Constants.DRIVE_BASE_LENGTH);
				state++;
			}
			return;
		}

		// turn to face the scale
		if (side) {
			if(state == 5){
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(90, 0);
					state++;
				}
				return;
			}
		} 
		else {
			if(state == 5){
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(-90, 0);
					state++;
				}
				return;
			}
		}

		if(state == 6){
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(5 - Constants.DRIVE_BASE_LENGTH);
				state++;
			}
			return;
		}
		/*
		 * /deposit the cube /long startTime = System.currentTimeMillis();
		 * while(System.currentTimeMillis()-startTime< 3000){ //update on time
		 * required robot.lift.liftUp(1); } robot.lift.liftStop();
		 * robot.manipulator.pullOut(1);
		 */
	}

	public void crossLine(double start) {
		if (start < 13 && start < 6 || start < -13 && start > -6) {
			if (state == 0) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(13);
					state++;
				}
				return;
			}
		}
		if (start >= 0 && start <= 4.5) { //----------
			if (state == 0) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(1);
					state++;
				}
				return;
			}
			if (state == 1) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(90, 0);
					state++;
				}
				return;
			}
			if (state == 2) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(9 - start);
					state++;
				}
				return;
			}
			if (state == 3) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(-90, 0);
					state++;
				}
				return;
			}
			if (state == 4) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(12);
					state++;
				}
				return;
			}
		}
		
		if (start < 0 && start >= -6) { //-------
				if (state == 0) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(1);
						state++;
					}
					return;
				}
				if (state == 1) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-90, 0);
						state++;
					}
					return;
				}
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(9 - start);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(90, 0);
						state++;
					}
					return;
				}
				if (state == 4) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(12);
						state++;
					}
					return;
				}
			}
		}

}
