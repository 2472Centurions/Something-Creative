package Actions;

import Objects.Action;
import Subsystems.Drive;

import com.kauailabs.nav6.frc.IMUAdvanced;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class driveStraightUntilOverDefenses extends Action {
	int range = 1;

	public class driveForwardUntilOverDefenses extends Action {
		public double pitch;
		private boolean anglechange = false;
		private long endTime;

		private double t;

		

		private Drive d;
		IMUAdvanced imu;

		private double speed = .75;

		public driveForwardUntilOverDefenses(double time, Drive RobotDrive) {

			t = time;

			d = RobotDrive;

			d.setPowerCurve(2);

			d.setDeadZone(.04);

			// in = new intakeWheel[0];

		}

		public driveForwardUntilOverDefenses(double time, Drive RobotDrive, double speeed) {

			t = time;

			d = RobotDrive;

			d.setPowerCurve(2);

			d.setDeadZone(.04);

			speed = speeed;

		}

		public driveForwardUntilOverDefenses(double time, Drive RobotDrive, IMUAdvanced IMU) {

			t = time;

			d = RobotDrive;

			imu = IMU;

		}

		public driveForwardUntilOverDefenses(double time, Drive RobotDrive, IMUAdvanced IMU, double PowerCurve) {

			t = time;

			d = RobotDrive;

			imu = IMU;

			

		}

		public void startAction() {

			super.startAction();
			pitch = imu.getPitch();
			setTimeOut((long) (t * 1000));

			if (imu != null) {

				imu.zeroYaw();
			}

		}

		public void periodic() {
			double adjustment = .1 * imu.getYaw();
			if (pitch != imu.getPitch()) {
				anglechange = true;

			}
			if (!timeout()) {

				d.tankdrive1(.8 + adjustment, .8 - adjustment);
				// endFactor = (endTime <= System.currentTimeMillis());

				SmartDashboard.putNumber("End Time", endTime);
			} else
				d.tankdrive1(0, 0);

			if (anglechange = true) {
				d.tankdrive1(.8 + adjustment, .8 - adjustment);
				if (imu.getPitch() < pitch + range && imu.getPitch() > pitch - range)
					endAction();
			}
		}

		public void endAction() {

			SmartDashboard.putBoolean("Ended", true);

		}
	}
}
