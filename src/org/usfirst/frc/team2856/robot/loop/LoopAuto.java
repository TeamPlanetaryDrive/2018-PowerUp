package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
//import org.usfirst.frc.team2856.robot.Shooter;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopAuto extends Loop{

	static String[] modes = {"l", "m", "r", "s"};
	
	//IterativeRobot robot;
	private String autoSelected;
	private Integer state;
	private DriveTrain drive;
	public LoopAuto(Robot rob){super(rob);
	
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
	
	
	public void leftPegCommands() {
		switch(state) {
		//The robot will move 13.5428085...ft+ forward from starting position (figure out later)
		//then turn 120 degrees right to face the peg.
		case 0:
				drive.moveStraight(16.0/3);
			case 1:
				/*
				 * If the robot does not see the left peg,
				 * turn the robot
				 * 
				 * After it sees the peg, center the robot onto the peg
				 */
			case 2:
				/*
				 * If the root is not yet on the peg, make it go forward
				 */
			case 3:
				/*
				 * If the robot is off from its course, adjust and go back a step.
				 */
			case 4:
				/*
				 * Important Note: When the robot gets near to the location, make it slow down!
				 */
			
		}
		
		//robot.driveTrain.moveStraight(1);
		//robot.driveTrain.moveTurn(1, 90);
	}
	public void middlePegCommands() {
		switch(state) {
		//the robot will move 9 1/3 ft forward from the starting position (figure out later)
			case 0:
				drive.moveStraight(16.0/3);
			case 1:
				/*
				 * If the robot does not see the middle peg,
				 * turn the robot
				 * 
				 * After it sees the peg, center the robot onto the peg
				 */
			case 2:
				/*
				 * If the root is not yet on the peg, make it go forward
				 */
			case 3:
				/*
				 * If the robot is off from its course, adjust and go back a step.
				 */
			case 4:
				/*
				 * Important Note: When the robot gets near to the location, make it slow down!
				 */
		
		}
		//robot.driveTrain.moveStraight(1);
		//robot.driveTrain.moveTurn(1, 90);
	}
	public void rightPegCommands(){
		switch(state) {
		//The robot will move 13.5428085...ft+ forward from starting position (figure out later)
		//then turn 120 degrees left to face the peg.
			case 0:
				drive.moveStraight(16.0/3);
			case 1:
				/*
				 * If the robot does not see the right peg,
				 * turn the robot
				 * 
				 * After it sees the peg, center the robot onto the peg
				 */
			case 2:
				/*
				 * If the root is not yet on the peg, make it go forward
				 */
			case 3:
				/*
				 * If the robot is off from its course, adjust and go back a step.
				 */
			case 4:
				/*
				 * Important Note: When the robot gets near to the location, make it slow down!
				 */
		
		}
		//robot.driveTrain.moveStraight(1);
		//robot.driveTrain.moveTurn(1, 90);
	}
	public void shootCommands() {
		robot.driveTrain.moveStraight(2);
		robot.driveTrain.moveTurn(1, 90);
//		Constants.shooter.set(0.98);;
	}
	public void adjust() {
		//Adjust the robot back on track
	}
	
	
}
