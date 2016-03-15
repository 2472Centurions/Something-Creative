package Actions;

import org.usfirst.frc.team2472.robot.Robot;
import Constants.Const;
import Objects.Action;

public class turnToZero extends Action {
	
	private double angie = 0;

	private boolean done = false;

	public turnToZero(double time) {

		timeout = time;
		
	}

	public void startAction() {

		super.startAction();

	}

	public void periodic() {
		if (Robot.imu.getYaw() < angie - Const.angleError)
			Robot.d.turn("right", .5);
		
		if (Robot.imu.getYaw() > angie + Const.angleError)
			Robot.d.turn("left", .5);
		
		//if (Robot.imu.getYaw() < angie + Const.angleError && Robot.imu.getYaw() > angie - Const.angleError) {
			
			//done = true;
			
		//}

	}

	public void endAction() {
		
		Robot.d.stopMotors();

	}
	
	public boolean isFinished(){
		
		if(isTimedOut()){
			
			endAction();
			
			return true;
			
		}
		
		else{
		
			return false;
			
		}
		
	}

}
