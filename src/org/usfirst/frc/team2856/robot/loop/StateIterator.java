
package org.usfirst.frc.team2856.robot.loop;

import java.util.ArrayList;
import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.Robot;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

public class StateIterator {
	
	// arraylist of arrays [string, string[]], 
	private ArrayList<Object[]> CommandList = new ArrayList<Object[]>(); 

	private DriveTrain drive;
	private double startTime = 0, duration = 0;
	private LoopAuto loop;
	
	private boolean timerOn = false;

	public StateIterator(DriveTrain d, LoopAuto l) {
		
		drive = d;
		loop = l;
		
	}

	public void update() {
		
		if(drive == null) {
			System.out.println("NO DRIVE");
			return;
		}
		if((!drive.moveGetActive() && !timerOn) || (System.currentTimeMillis() > duration + startTime && timerOn)) {
			
			stop();
			execute();
						
		}
		return;
	}
	
	public void execute() {
		Object[] currentCommand; 
		System.out.println(CommandList.size());
		if(CommandList.size()>0) {
			currentCommand = CommandList.get(0);
			System.out.println("running command: " + (String)currentCommand[0]);
			double[] args = (double[])currentCommand[1];

			for(int i = 0;i<args.length;i++) {
				System.out.println(args[i]);
			}
			switch((String)currentCommand[0]){
			
				case("forward"): // [double distance]
					startTime = -1;
					loop.robot.driveTrain.moveStraight((double)(args[0]));
					break;
					
				case("turn"): // [double angle, double radius]
					startTime = -1;
					loop.robot.driveTrain.moveTurn((double)(args[0]),(double)(args[1]));
					break;
					
				case("lift"): // [double time, double effort]
					startTime = System.currentTimeMillis();
					duration = (double)(args[0]);
					loop.robot.lift.liftUp((double)(args[1]));
					timerOn = true;
					break;
					
				case("manipulate"): // [double time, double effort]
					startTime = System.currentTimeMillis();
					duration = (double)(args[0]);
					loop.robot.manipulator.pullIn((double)(args[1]));
					timerOn = true;
					break;
				
				case("effort"): // [double time, double leftEffort, double rightEffort]
					startTime = System.currentTimeMillis();
					duration = (double)(args[0]);
					loop.robot.driveTrain.moveEffort((double)(args[1]),(double)(args[2]));
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
		loop.robot.driveTrain.moveStop();
		loop.robot.lift.liftStop();
		loop.robot.manipulator.stopPull();
		timerOn = false;
		
	}
	
	public void add(String command, double[] args) {
		//{string,{double,double}}
		double[] args2 = {0,0,0,0};
		for(int i = 0;i<args.length;i++) {
			args2[i] = args[i];
		}
		Object[] newCommand = new Object[]{ command, args2}; 
		CommandList.add(newCommand);
				
	}
}
