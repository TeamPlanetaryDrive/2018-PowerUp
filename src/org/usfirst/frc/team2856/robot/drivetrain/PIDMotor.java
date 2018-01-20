package org.usfirst.frc.team2856.robot.drivetrain;

import org.usfirst.frc.team2856.robot.Constants;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PIDMotor extends PIDSubsystem{
	
	//PIDController ...
	public SpeedController motor;
	public PIDSource src;
	public double multiplier;
	
	/**
	 * Init needs to be called
	 */
	public PIDMotor(double Kp, double Ki, double Kd){
		super("PIDMotor", Kp, Ki, Kd);
		
		this.setOutputRange(-0.95, 0.95);
		this.setAbsoluteTolerance(0.05);
		this.getPIDController().setContinuous(true);//XXX
		multiplier = 1;
	}
	
	public void init(SpeedController sc, boolean inv, PIDSource en){
		motor = sc;
		motor.setInverted(inv);
		
		src = en;
		
	}
	
	
	protected double returnPIDInput() {
		return src.pidGet();		
			
	}

	protected void usePIDOutput(double output) {
		motor.set(output * multiplier);
	}

	@Deprecated
	protected void initDefaultCommand() {
		//this.setSetpoint(10);	
	}
}
	