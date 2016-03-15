package Actions;

import Objects.Action;
import org.usfirst.frc.team2472.robot.Robot;

import Constants.Const;


public class driveStraightUntilOverDefenses_022316 extends Action {

		public double pitch;
		
		private boolean anglechange = false;

		private double speed = 1.0;
		
		private double tolerance = 5.0;
		
		private long flatTime = 0;
		
		private boolean done = false;

		public driveStraightUntilOverDefenses_022316(double time) {

			timeout = time;
			
		}

		public driveStraightUntilOverDefenses_022316(double time, double speeed) {

			timeout = time;

			speed = speeed;

		}

		public void startAction() {

			super.startAction();
			
			pitch = Robot.imu.getPitch();

			if (Robot.imu != null) {

				Robot.imu.zeroYaw();
			}

		}

		public void periodic() {
			
			//double adjustment = .1 * Robot.imu.getYaw();
			
			if (Math.abs(pitch - Robot.imu.getPitch()) >= tolerance) {
				
				anglechange = true;

			}
			
			if (!isTimedOut()) {

				//Robot.d.tankdrive1(.8*speed + adjustment, .8*speed - adjustment);
				
				if(Robot.imu.getYaw()>Const.yawDeadZone)
					Robot.d.tankdrive1(.8*speed,1*speed);
				
				if(Robot.imu.getYaw()<-Const.yawDeadZone)
					Robot.d.tankdrive1(1*speed,.8*speed);
				
				if(Robot.imu.getYaw()>-Const.yawDeadZone&&Robot.imu.getYaw()<Const.yawDeadZone)
					Robot.d.tankdrive1(1*speed, 1*speed);
				
			}
				
				

			if (anglechange == true) {
				
				//Robot.d.tankdrive1(.8*speed + adjustment, .8*speed - adjustment);
				
				if(Robot.imu.getYaw()>Const.yawDeadZone)
					Robot.d.tankdrive1(-.8*speed,-1*speed);
				
				if(Robot.imu.getYaw()<-Const.yawDeadZone)
					Robot.d.tankdrive1(-1*speed,-.8*speed);
				
				if(Robot.imu.getYaw()>-Const.yawDeadZone&&Robot.imu.getYaw()<Const.yawDeadZone)
					Robot.d.tankdrive1(-1*speed, -1*speed);
				
				if (Math.abs(pitch - Robot.imu.getPitch()) >= tolerance ){
					
					flatTime = System.currentTimeMillis();
					
				}
				else{
					
					if(System.currentTimeMillis() - flatTime > 1000){
						
						done = true;
						
					}
					
				}
			}
			
		}

		public void endAction() {

			Robot.d.tankdrive1(0, 0);

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
