package Actions;

import java.awt.Robot;

import com.kauailabs.nav6.frc.IMUAdvanced;

import Objects.Action;
import Subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class driveForward extends Action {

	private long endTime;

	private double t;

	private double powerCurve = 2;

	private double deadzone;
	
	private Drive d;
	IMUAdvanced imu;

	private double speed = .75;

	public driveForward(double time, Drive RobotDrive) {

		t = time;

		d = RobotDrive;

		d.setPowerCurve(2);

		d.setDeadZone(.04);

		// in = new intakeWheel[0];

	}

	public driveForward(double time, Drive RobotDrive, double speeed) {

		t = time;

		d = RobotDrive;

		d.setPowerCurve(2);

		d.setDeadZone(.04);

		speed = speeed;

	}

	public driveForward(double time, Drive RobotDrive, IMUAdvanced IMU) {

		t = time;

		d = RobotDrive;

		imu = IMU;

	}

	public driveForward(double time, Drive RobotDrive, IMUAdvanced IMU, double PowerCurve, double deadzone) {

		t = time;

		d = RobotDrive;

		imu = IMU;

		powerCurve = PowerCurve;
		
		deadzone = deadzone;
	}

	public void startAction() {

		super.startAction();
		setTimeOut((long) (t * 1000));

		if (imu != null) {

			imu.zeroYaw();

		}

	}

	public void periodic() {
		if (!timeout()) {
			
			if(imu.getYaw()>deadzone)
				d.tankdrive1(1,.8);
			
			if(imu.getYaw()<deadzone)
				d.tankdrive1(.8,1);
			// endFactor = (endTime <= System.currentTimeMillis());

			SmartDashboard.putNumber("End Time", endTime);
			
		}else d.tankdrive1(0, 0);
	}

	public void endAction() {

		SmartDashboard.putBoolean("Ended", true);

	}

}
