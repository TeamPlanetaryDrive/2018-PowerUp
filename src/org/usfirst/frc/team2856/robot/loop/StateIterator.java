
package org.usfirst.frc.team2856.robot.loop;

import java.util.ArrayList;
import org.usfirst.frc.team2856.robot.Robot;

public class StateIterator {
	//contains commands and their arguments: arraylist<objects<string,doubles[4]>>
	private ArrayList<Object[]> CommandList = new ArrayList<Object[]>(); 

	private double startTime = 0, duration = 0;
	private Robot robot;
	
	private boolean timerOn = false;

	public StateIterator(Robot r) {
		robot = r;
	}

	public void update() {
		if((!robot.driveTrain.moveGetActive() && !timerOn) || (System.currentTimeMillis() > duration + startTime && timerOn)) {
			stop();
			execute();
		}
		return;
	}
	
	public void execute() {
		Object[] currentCommand; 

		if(CommandList.size()>0) {
			currentCommand = CommandList.get(0);
			double[] args = (double[])currentCommand[1];

			switch((String)currentCommand[0]){
				case("forward"): // [double distance]
					startTime = -1;
					robot.driveTrain.moveStraight((double)(args[0]));
					break;
					
				case("turn"): // [double angle, double radius]
					startTime = -1;
					robot.driveTrain.moveTurn((double)(args[0]),(double)(args[1]));
					break;
					
				case("lift"): // [double time, double effort]
					startTime = System.currentTimeMillis();
					duration = (double)(args[0]);
					robot.lift.liftUp((double)(args[1]));
					timerOn = true;
					break;
					
				case("manipulate"): // [double time, double effort]
					startTime = System.currentTimeMillis();
					duration = (double)(args[0]);
					robot.manipulator.pullIn((double)(args[1]));
					timerOn = true;
					break;
				
				case("effort"): // [double time, double leftEffort, double rightEffort]
					startTime = System.currentTimeMillis();
					duration = (double)(args[0]);
					robot.driveTrain.moveEffort((double)(args[1]),(double)(args[2]));
					timerOn = true;
					break;
					
				case("delay"):
					startTime = System.currentTimeMillis();
					duration = (double)args[0];
					timerOn = true;
					break;
				
				default:
					break;
			}
			CommandList.remove(0);
		}
	}
	
	public void stop() {
		robot.driveTrain.moveStop();
		robot.lift.liftStop();
		robot.manipulator.stopPull();
		robot.climb.climbStop();
		timerOn = false;
	}
	
	public void add(String command, double[] args) {
		double[] args2 = {0,0,0,0};
		for(int i = 0;i<args.length;i++) {
			args2[i] = args[i];
		}
		
		Object[] newCommand = new Object[]{ command, args2}; 
		CommandList.add(newCommand);
	}
}
