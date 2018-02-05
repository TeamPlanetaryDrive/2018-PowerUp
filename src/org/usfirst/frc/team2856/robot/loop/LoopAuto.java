package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
//import org.usfirst.frc.team2856.robot.Shooter;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopAuto extends Loop{

	static String[] modes = {"l", "m", "r", "s"};
	
	//IterativeRobot robot;
	private String autoSelected;
	private Integer state;
	private DriveTrain drive;
	public LoopAuto(Robot rob){
		super(rob);
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
		state = 0;
		
		drive = robot.driveTrain;
		drive.initAuto();
	}

	public void loop() {
		
		drive.update(false);
	}
	
	public static void addModes(){
		
		for(int i = 0; i < modes.length; i++){
			SmartDashboard.putString("Auto Selector", modes[i]);
		}
		
	}
	
	
// in memoriam: the stateMachine(). 2017-2018. lay here until the day of it's death. 
// a fickle and confusing creature, but nonetheless a good friend until the very end
	
	public void adjust() {
		//Adjust the robot back on track
	}
	//from this point downwards moveStraight is in feet(need to update with new specifications)
	
	/*Given side of ownership, drives robot to the switch side and turns to vertical side of switch
	 * 					____________               ____________
	 * 					|          |               |          |
	 * 					|          |_______________|          |<-- or here
	 * 	deposit here-->	|          |               |          |
	 * 					|          |               |          |
	 * 					|__________|               |__________|
	 * 
	 * starts in front of the switch, drives forwards, turns, and deposits.
	 */
	public void sideSwitchCommands(int dir){//0 for left, 1 for right, start 1 feet from switch side
		if(dir == 0){
			robot.driveTrain.moveStraight(14);
			robot.driveTrain.moveTurn(-90,1);
			//drop cube in switch
		}
		else if(dir == 1){
			robot.driveTrain.moveStraight(14);
			robot.driveTrain.moveTurn(90, 1);
			//drop cube in switch
		}
	}
	/*Given side of ownership, drives robot to switch side and turns to horizontal side of switch
	 * 	____________               ____________
	 * 	|          |               |          |
	 * 	|          |_______________|          |
	 * 	|          |               |          |
	 * 	|          |               |          |
	 * 	|__________|               |__________|
	 * 		 ^							 ^
	 * 		 |							 |
	 * 		 |							 |
	 *  deposit here 				  or here
	 */
	public void directSwitchCommands(int dir){//0 for left, 1 for right, start directly on switch side
		if(dir==0){
			robot.driveTrain.moveStraight(14);
			//drop cube in switch
		}
		else if(dir == 1){
			robot.driveTrain.moveStraight(14);
			//drop cube in switch
		}
		
	}
	public void ScaleCommands(int dir){//0 for left, 1 for right, start on side opposite of scale robots
		if(dir==0){
			robot.driveTrain.moveStraight(22);
			robot.driveTrain.moveTurn(-90, 1);
			robot.driveTrain.moveTurn(90,1);
			//drop cube in scale
		}
		else if(dir ==1 ){
			robot.driveTrain.moveStraight(22);
			robot.driveTrain.moveTurn(90, 1);
			robot.driveTrain.moveTurn(-90, 1);
			//drop cube in scale
			
		}
	}
	
	public void depositAtSwitch(double start, boolean side) { // left/true  right/false
		robot.driveTrain.moveStraight(5); // clear any obstacles
		// align bot with switch
		if(side) { // do we have the left switch . . .
			if(start > -4.5) { // if we start to the right of the switch
				robot.driveTrain.moveTurn(-90,0);
				robot.driveTrain.moveStraight(start + 9.5);
				robot.driveTrain.moveTurn(90, 0);
				
			} else if(start < -9.5) { // if we start to the left of the switch
				robot.driveTrain.moveTurn(90,0);
				robot.driveTrain.moveStraight(-start - 9.5);
				robot.driveTrain.moveTurn(-90, 0);

			} // do nothing if we start directly adjacent to the switch
			
		} else { // . . . or the right switch
			if(start > 4.5) { // if we start to the right of the switch
				robot.driveTrain.moveTurn(-90,0);
				robot.driveTrain.moveStraight(start - 9.5);
				robot.driveTrain.moveTurn(90, 0);
				
			} else if(start < 9.5) { // if we start to the left of the switch
				robot.driveTrain.moveTurn(90,0);
				robot.driveTrain.moveStraight(-start + 9.5);
				robot.driveTrain.moveTurn(-90, 0);

			} // do nothing if we start directly adjacent to the switch
			
		}
		
		robot.driveTrain.moveStraight(6.66 - Constants.DRIVE_BASE_LENGTH); //swiggity swoot the rest of the  way to the switch.
		//deposit the cube
		
		
		
	}
	
	public void depositAtScale(double start, boolean side) { // left/true  right/false
		robot.driveTrain.moveStraight(5); // clear any obstacles
		// align b o t b o y e  with the scale
		if(side) { // do we have the left scale . . .
			robot.driveTrain.moveTurn(-90,0);
			robot.driveTrain.moveStraight(start + 11);
			robot.driveTrain.moveTurn(90,0);

		} else { // . . . or the right scale
			robot.driveTrain.moveTurn(90,0);
			robot.driveTrain.moveStraight(-start - 11);
			robot.driveTrain.moveTurn(-90,0);

		}
		
		robot.driveTrain.moveStraight(22 - Constants.DRIVE_BASE_LENGTH); //swiggity swoot to the center of the arena
		
		//turn to face the scale
		if(side) {
			robot.driveTrain.moveTurn(90,0);
		} else {
			robot.driveTrain.moveTurn(-90,0);
		}
		
		
		robot.driveTrain.moveStraight(5 - Constants.DRIVE_BASE_LENGTH); // approach the scale
		//deposit the cube
	}
		
	
}
