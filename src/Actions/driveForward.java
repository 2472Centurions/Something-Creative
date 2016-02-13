package Actions;


import org.usfirst.frc.team2472.robot.Robot;

import com.kauailabs.nav6.frc.IMUAdvanced;

import Constants.Const;
import Objects.Action;
import Subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class driveForward extends Action {
	
	private double speed = .75;

	public driveForward(double time) {

		timeout = time;

	}

	public driveForward(double time, double speeed) {

		timeout = time;

		speed = speeed;

	}

	public void startAction() {
		
		super.startAction();

		if (Robot.imu != null) {

			Robot.imu.zeroYaw();

		}

	}

	public void periodic() {
		if (!isTimedOut()) {
			
			if(Robot.imu.getYaw()>Const.yawDeadZone)
				Robot.d.tankdrive1(1*speed,.8*speed);
			
			if(Robot.imu.getYaw()<Const.yawDeadZone)
				Robot.d.tankdrive1(.8*speed,1*speed);
			
		}
	}

	public void endAction() {

		 Robot.d.tankdrive1(0, 0);

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
