package Actions;

import org.usfirst.frc.team2472.robot.Robot;

import com.kauailabs.nav6.frc.IMUAdvanced;

import Constants.Const;
import Objects.Action;
import Subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class turnRight extends Action {
	
	private double angie = 0;

	private boolean done = false;

	public turnRight(double time) {

		timeout = time;
		
	}

	public turnRight(double time, double angle) {

		angie = angle;
		
		timeout = time;

	}

	public void startAction() {

		super.startAction();

		if (Robot.imu != null) {

			Robot.imu.zeroYaw();

		}

	}

	public void periodic() {
		if (Robot.imu.getYaw() < angie - Const.angleError)
			Robot.d.turn("right", 1);
		
		if (Robot.imu.getYaw() > angie + Const.angleError)
			Robot.d.turn("left", 1);
		
		if (Robot.imu.getYaw() < angie + Const.angleError && Robot.imu.getYaw() > angie - Const.angleError) {
			
			done = true;
			
		}

	}

	public void endAction() {
		
		Robot.d.stopMotors();

	}
	
	public boolean isFinished(){
		
		if(isTimedOut() || done){
			
			endAction();
			
			return true;
			
		}
		
		else{
		
			return false;
			
		}
		
	}

}
