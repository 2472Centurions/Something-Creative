package Subsystems;

import com.kauailabs.nav6.frc.IMUAdvanced;

import Objects.Action;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class turnRight extends Action {

	private long endTime;

	private double t;

	private double powerCurve = 2;

	double error = 2.5;
	private double angie = 0;

	private Drive d;

	IMUAdvanced imu;

	private boolean done;

	private double speed = .75;

	public turnRight(double time, Drive RobotDrive) {

		t = time;

		d = RobotDrive;

		d.setPowerCurve(2);

		d.setDeadZone(.04);

		// double error = 0;

	}

	public turnRight(double time, Drive RobotDrive, double speeed) {
		t = time;

		d = RobotDrive;

		d.setPowerCurve(2);

		d.setDeadZone(.04);

		speed = speeed;
	}

	public turnRight(double angle, double time, Drive RobotDrive, IMUAdvanced IMU) {

		t = time;

		d = RobotDrive;

		imu = IMU;

		angie = angle;

	}

	public void startAction() {

		super.startAction();
		setTimeOut((long) (t * 1000));

		d.setPowerCurve(powerCurve);

		SmartDashboard.putNumber("Drive Forward Timeout", endTime);

		if (imu != null) {

			imu.zeroYaw();

		}

	}

	public void periodic() {
		if (imu.getYaw() < angie - error)
			d.turn("right", 1);
		if (imu.getYaw() > angie + error)
			d.turn("left", 1);
		if (imu.getYaw() < angie + error && imu.getYaw() > angie - error) {
			done = true;
			if (done) {
				d.kill();
				endAction();
			}
		}

	}

	public void endAction() {

	}

}
