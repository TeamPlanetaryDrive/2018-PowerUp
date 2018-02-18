package org.usfirst.frc.team2856.robot.loop;

import java.util.ArrayList;
import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

public class StateIterator {
	
	// arraylist of arrays [string, string[]], 
	private ArrayList<Object[]> CommandList = new ArrayList<Object[]>(); 

	private Object[] current; 
	private DriveTrain drive;
	private double startTime, duration;
	private LoopAuto loop;
	
	private boolean timerOn = false;

	public StateIterator(DriveTrain d, LoopAuto l) {
		
		drive = d;
		loop = l;
		
	}

	public void update() {
		
		if(!drive.moveGetActive() || (System.currentTimeMillis() > duration + startTime && timerOn)) {
			
			stop();
			execute();
						
		}
		return;
	}
	
	public void execute() {
		
		if(CommandList.size()>=0) {
			
			current = CommandList.get(0); 
			String[] args = (String[])current[1];
			
			switch((String)CommandList.get(0)[0]){
			
				case("forward"):
					startTime = -1;
					loop.robot.driveTrain.moveStraight(Double.valueOf(args[0]));
					break;
					
				case("turn"):
					startTime = -1;
					loop.robot.driveTrain.moveTurn(Double.valueOf(args[0]),Double.valueOf(args[1]));
					break;
					
				case("lift"):
					startTime = System.currentTimeMillis();
					duration = Double.valueOf(args[0]);
					loop.robot.lift.liftUp(Double.valueOf(args[1]));
					break;
					
				case("manipulate"):
					startTime = System.currentTimeMillis();
					duration = Double.valueOf(args[0]);
					loop.robot.manipulator.pullIn(Double.valueOf(args[1]));
					break;
				
				case("forwardEffort"):
					// do things
					break;
				
				default:
					break;
			}
			
		}
		
		CommandList.remove(0);
		
	}
	
	public void stop() {
		loop.robot.driveTrain.moveStop();
		loop.robot.lift.liftStop();
		loop.robot.manipulator.stopPull();
		timerOn = false;
		
	}
	
	public void add(String command, double[] args) {
		CommandList.add(new Object[]{ command, args});
				
	}
}
