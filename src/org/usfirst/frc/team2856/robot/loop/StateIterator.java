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
			Double[] args = (Double[])current[1];
			
			switch((String)CommandList.get(0)[0]){
			
				case("forward"): // [double distance]
					startTime = -1;
					loop.robot.driveTrain.moveStraight((double)(args[0]));
					break;
					
				case("turn"): // [double angle, double radius]
					startTime = -1;
					loop.robot.driveTrain.moveTurn((double)args[0],(double)args[1]);
					break;
					
				case("lift"): // [double time, double effort]
					startTime = System.currentTimeMillis();
					duration = (double)args[0];
					loop.robot.lift.liftUp((double)args[1]);
					timerOn = true;
					break;
					
				case("manipulate"): // [double time, double effort]
					startTime = System.currentTimeMillis();
					duration = (double)args[0];
					loop.robot.manipulator.pullIn((double)args[1]);
					timerOn = true;
					break;
				
				case("effort"): // [double time, double leftEffort, double rightEffort]
					startTime = System.currentTimeMillis();
					duration = (double)args[0];
					loop.robot.driveTrain.moveEffort((double)args[1],(double)args[2]);
					timerOn = true;
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
