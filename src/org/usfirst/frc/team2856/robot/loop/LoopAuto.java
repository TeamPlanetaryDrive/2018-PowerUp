package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
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
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			directSwitchCommands(0);//will need to update based on starting position
			System.out.println("Left");
		} else {
			directSwitchCommands(1);//will need to update based on starting position
			System.out.println("Right");
		}
		
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
//a fickle and confusing creature, but nonetheless a good friend until the very end
	
	public void adjust() {
		//Adjust the robot back on track
	}
	//from this point downwards moveStraight is in feet(need to update with new specifications)
	
	/*Given side of ownership, drives robot to the switch side and turns to vertical side of switch
	 * 					____________               _____________
	 * 					|          |               |           |
	 * 					|          |_______________|           |<-- or here
	 * 	deposit here-->	|          |               |           |
	 * 					|          |               |           |
	 * 					___________|               _____________
	 */
	public void sideSwitchCommands(int dir){//0 for left, 1 for right, start 1 feet from switch side
		if(dir == 0){
			robot.driveTrain.moveStraight(14);
			robot.driveTrain.moveTurn(-90,1);
			long startTime = System.currentTimeMillis(); 
			while(System.currentTimeMillis()-startTime< 5){//update on time required 
				robot.lift.liftUp(1);
			}
			robot.lift.liftStop();
			robot.manipulator.pullOut(1);

		}
		else if(dir == 1){
			robot.driveTrain.moveStraight(14);
			robot.driveTrain.moveTurn(90, 1);
			long startTime = System.currentTimeMillis(); 
			while(System.currentTimeMillis()-startTime< 5){//update on time required 
				robot.lift.liftUp(1);
			}
			robot.lift.liftStop();
			robot.manipulator.pullOut(1);

		}
	}
	/*Given side of ownership, drives robot to switch side and turns to horizontal side of switch
	 * 	____________               _____________
	 * 	|          |               |           |
	 * 	|          |_______________|           |
	 * 	|          |               |           |
	 * 	|          |               |           |
	 * 	___________|               _____________
	 * 		 ^							 ^
	 * 		 |							 |
	 * 		 |							 |
	 *  deposit here 				  or here
	 */
	public void directSwitchCommands(int dir){//0 for left, 1 for right, start directly on switch side
		if(dir==0){
			robot.driveTrain.moveStraight(14);
			long startTime = System.currentTimeMillis(); 
			while(System.currentTimeMillis()-startTime< 5){//update on time required 
				robot.lift.liftUp(1);
			}
			robot.lift.liftStop();
			robot.manipulator.pullOut(1);

		}
		else if(dir == 1){
			robot.driveTrain.moveStraight(14);
			long startTime = System.currentTimeMillis(); 
			while(System.currentTimeMillis()-startTime< 5){//update on time required 
				robot.lift.liftUp(1);
			}
			robot.lift.liftStop();
			robot.manipulator.pullOut(1);

		}
		
	}
	public void ScaleCommands(int dir){//0 for left, 1 for right, start on side opposite of scale robots
		if(dir==0){
			robot.driveTrain.moveStraight(22);
			robot.driveTrain.moveTurn(-90, 1);
			robot.driveTrain.moveTurn(90,1);
			long startTime = System.currentTimeMillis(); 
			while(System.currentTimeMillis()-startTime< 5){//update on time required 
				robot.lift.liftUp(1);
			}
			robot.lift.liftStop();
			robot.manipulator.pullOut(1);
		}
		else if(dir ==1 ){
			robot.driveTrain.moveStraight(22);
			robot.driveTrain.moveTurn(90, 1);
			robot.driveTrain.moveTurn(-90, 1);
			long startTime = System.currentTimeMillis(); 
			while(System.currentTimeMillis()-startTime< 5){//update on time required 
				robot.lift.liftUp(1);
			}
			robot.lift.liftStop();
			robot.manipulator.pullOut(1);	
		}	
	}
	//Command to cross line during auto
	public void AutoLine(){
		robot.driveTrain.moveStraight(22);
	}
	
	
}
