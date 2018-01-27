package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
//import org.usfirst.frc.team2856.robot.Shooter;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//commit test
public class LoopAuto extends Loop{

	static String[] modes = {"l", "m", "r", "s"};
	
	//IterativeRobot robot;
	private String autoSelected;
	private Integer state;
	private DriveTrain drive;
	public LoopAuto(Robot rob){
		super(rob);
//		String gameData;
//		gameData = DriverStation.getInstance().getGameSpecificMessage();
//		if(gameData.charAt(0) == 'L')
//		{
//			//Put left auto code here
//			System.out.println("Left");
//		} else {
//			//Put right auto code here
//			System.out.println("Right");
//		}
	}

	public void init() {
		autoSelected = SmartDashboard.getString("Auto Selector", "None");
		System.out.println("Auto selected: " + autoSelected);
		state = 0;
		
		drive = robot.driveTrain;
		drive.initAuto();
		stateMachine();
	}

	public void loop() {
		
		stateMachine();
		drive.update(false);
	}
	
	public static void addModes(){
		
		for(int i = 0; i < modes.length; i++){
			SmartDashboard.putString("Auto Selector", modes[i]);
		}
		
	}
	
	
	private void stateMachine() {
		switch(autoSelected) {
			case "Forward":
				switch(state) {
					case 0:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(Constants.AUTO_DIST);
							state++;
						}
						break;
					default:
						break;
				}
				break;
			case "FwdRev":
				switch(state) {
					case 0:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(Constants.AUTO_DIST);
							state++;
						}
						break;
					case 1:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(-Constants.AUTO_DIST);
							state++;
						}
						break;
					default:
						break;
				}
				break;
			case "Line":
				switch(state) {
					case 0:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(6.0);
							state++;
						}
						break;
					case 1:
						if(!drive.moveGetActive())
						{
							drive.moveTurn(180.0, 0.0);
							state++;
						}
						break;
					case 2:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(6.0);
							state++;
						}
						break;
					case 3:
						if(!drive.moveGetActive())
						{
							drive.moveTurn(-180.0, 0.0);
							state++;
						}
						break;
					default:
						break;
				}
				break;
			case "Square":
				switch(state) {
					case 0:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(3.0);
							state++;
						}
						break;
					case 1:
						if(!drive.moveGetActive())
						{
							drive.moveTurn(90.0, 2.0);
							state++;
						}
						break;
					case 2:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(3.0);
							state++;
						}
						break;
					case 3:
						if(!drive.moveGetActive())
						{
							drive.moveTurn(90.0, 2.0);
							state++;
						}
						break;
					case 4:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(3.0);
							state++;
						}
						break;
					case 5:
						if(!drive.moveGetActive())
						{
							drive.moveTurn(90.0, 2.0);
							state++;
						}
						break;
					case 6:
						if(!drive.moveGetActive())
						{
							drive.moveStraight(3.0);
							state++;
						}
						break;
					case 7:
						if(!drive.moveGetActive())
						{
							drive.moveTurn(90.0, 2.0);
							state++;
						}
						break;
					default:
						break;
				}
				break;
			case "Turn":
				switch(state) {
					case 0:
						if(!drive.moveGetActive())
						{
							drive.moveTurn(90.0, 0.0);
							state++;
						}
						break;
					default:
						break;
				}
				break;
			default:
				// No match found, do nothing
				break;
		}
	}
	
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
			//drop cube in switch
		}
		else if(dir == 1){
			robot.driveTrain.moveStraight(14);
			robot.driveTrain.moveTurn(90, 1);
			//drop cube in switch
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
	
	
	
}
