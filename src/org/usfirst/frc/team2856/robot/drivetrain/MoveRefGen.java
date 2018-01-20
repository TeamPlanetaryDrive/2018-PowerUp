package org.usfirst.frc.team2856.robot.drivetrain;

import org.usfirst.frc.team2856.robot.Constants;

public class MoveRefGen {
	// State machine enumeration
	private static enum MoveState {
		Idle,
		Accel,
		SteadyState,
		Decel,
		Settle
	}

	// Parameters
	private double accelRate;
	private double maxSpeed;
	private double settleTime;

	// States
	private MoveState moveState;
	private double decelStart;
	private double direction;
	private double endDist;
	private double refDist;
	private double refSpeed;
	private double settleTimer;

	MoveRefGen() {
		moveState = MoveState.Idle;
	}
	
	public void configure(double accelRate, double maxSpeed, double settleTime) {
		this.accelRate = accelRate;
		this.maxSpeed = maxSpeed;
		this.settleTime = settleTime;
	}

	public double getRefPosition() {
		return direction * refDist;
	}

	public double getRefVelocity() {
		return direction * refSpeed;
	}

	public boolean isActive() {
		return moveState != MoveState.Idle;
	}

	public void start(double distance) {
		double decelDist;
		
		if (distance != 0) {
			
			if (distance > 0) {
				
				direction = 1;
				endDist = distance;
			
			} else {
			
				direction = -1;
				endDist = -distance;
			}
			
			decelDist = (maxSpeed * maxSpeed) / (2 * accelRate);
			
			if ((2 * decelDist) > endDist) {
				decelDist = endDist / 2;
			}
			
			decelStart = endDist - decelDist;
			refDist = 0;
			refSpeed = 0;
			moveState = MoveState.Accel;
		}
	}

	public void stop() {
		refSpeed = 0;
		moveState = MoveState.Idle;
	}

	public void update() {
		switch (moveState) {
			case Idle:
				// Do nothing
				break;
			case Accel:
				refSpeed += accelRate * Constants.PERIODIC_UPDATE_PERIOD;
				if (refSpeed >= maxSpeed)
				{
					refSpeed = maxSpeed;
					moveState = MoveState.SteadyState;
				}
				refDist += refSpeed * Constants.PERIODIC_UPDATE_PERIOD;
				if (refDist >= decelStart)
				{
					moveState = MoveState.Decel;
				}
				break;
			case SteadyState:
				refDist += refSpeed * Constants.PERIODIC_UPDATE_PERIOD;
				if (refDist >= decelStart)
				{
					moveState = MoveState.Decel;
				}
				break;
			case Decel:
	            refSpeed -= accelRate * Constants.PERIODIC_UPDATE_PERIOD;
	            if (refSpeed <= 0)
	            {
	                refSpeed = 0;
	                refDist = endDist;
	                settleTimer = 0;
	                moveState = MoveState.Settle;
	            }
	            else
	            {
	                refDist += refSpeed * Constants.PERIODIC_UPDATE_PERIOD;
	                if (refDist >= endDist)
	                {
	                    refSpeed = 0;
	                    refDist = endDist;
	                    if (settleTime != 0)
	                    {
		                    settleTimer = 0;
		                    moveState = MoveState.Settle;
	                    }
	                    else
	                    {
	                    	moveState = MoveState.Idle;
	                    }
	                }
	            }
				break;
			case Settle:
	            settleTimer += Constants.PERIODIC_UPDATE_PERIOD;
	            if (settleTimer >= settleTime)
	            {
	                moveState = MoveState.Idle;
	            }
				break;
		}
	}
}
