
package Subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	CANTalon[] moter = new CANTalon[4];
	CANTalon FL, FR, BL, BR;

	double x;
	double y;
	double z;
	double X;
	double Y;
	double Z;
	private RobotDrive d;
	double range, powerCurve;

	public Drive(int FL, int FR, int BL, int BR) {
		moter[0] = new CANTalon(FL);
		moter[1] = new CANTalon(FR);
		moter[2] = new CANTalon(BL);
		moter[3] = new CANTalon(BR);
		d = new RobotDrive(moter[0], moter[1], moter[2], moter[3]);
	}

	public void runMotor(int mID) {
		if (mID == 0 || mID == 2)
			moter[mID].set(-1.0);
		else
			moter[mID].set(1.0);
	}

	public void runMotorBack(int mID) {

		moter[mID].set(-1.0);

	}

	public void stopMotors() {

		moter[0].set(0.0);
		moter[1].set(0.0);
		moter[2].set(0.0);
		moter[3].set(0.0);

	}

	public void setPowerCurve(double PowerCurve) {

		powerCurve = PowerCurve;

		// powerCurve = 1.1;
	}

	public void setDeadZone(double DeadZone) {

		range = DeadZone;
	}

	public void arcadeDrive(double forward, double rotation, double throtle) {

		y = forward;

		z = rotation;

		if (z < -range) {

			X = Math.pow(z, powerCurve);

		} else if (z > range) {

			X = -(Math.pow(z, powerCurve));

		} else {

			X = 0;

		}

		if (y < range) {

			Y = -Math.pow(y, powerCurve);

		} else if (y > range) {

			Y = (Math.pow(y, powerCurve));

		} else {

			Y = 0;

		}

		d.arcadeDrive(Y * throtle, X * throtle);

	}

	public void tank(Joystick j, Joystick k) {

		if (j.getY() > range || j.getY() < -range) {

			moter[3].set(-j.getY());
			moter[1].set(-j.getY());
			
		}
		if (k.getY() > range || k.getY() < -range) {
			moter[2].set(k.getY());
			moter[0].set(k.getY());
		}
	}
public void cantaloninit(int ramprate){
	moter[0].setVoltageRampRate(ramprate);
	moter[1].setVoltageRampRate(ramprate);
	moter[2].setVoltageRampRate(ramprate);
	moter[3].setVoltageRampRate(ramprate);
}
}
