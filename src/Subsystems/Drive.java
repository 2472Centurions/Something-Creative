
package Subsystems;

import Constants.Const;
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
		
		if(mID == Const.FL)
			moter[0].set(1.0);
		
		if(mID == Const.FR)
			moter[1].set(1.0);
		
		if(mID == Const.BL)
			moter[2].set(1.0);
		
		if(mID == Const.BR)
			moter[3].set(1.0);
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

		if (k.getY() > range || k.getY() < -range) {

			moter[3].set(k.getY());
			moter[1].set(k.getY());
			
		}
		if (j.getY() > range || j.getY() < -range) {
			moter[2].set(-j.getY());
			moter[0].set(-j.getY());
		}
	}
	public void cantaloninit(int ramprate){
		moter[0].setVoltageRampRate(ramprate);
		moter[1].setVoltageRampRate(ramprate);
		moter[2].setVoltageRampRate(ramprate);
		moter[3].setVoltageRampRate(ramprate);
	}
	
	public void tankdrive1(double a, double b){
		
		FL.set(constrain(a));
		BL.set(constrain(a));
		FR.set(constrain(b));
		BR.set(constrain(b));
	}
	
	private double constrain(double In){
		
		double Out = Math.min(In, 1.0);
		
		Out = Math.max(Out, -1.0);
		
		return Out;
		
	}
	
	public void turn(String dir,double power){
		if(dir=="right"){
			FL.set(power);
			BL.set(power);
			FR.set(-power);
			BR.set(-power);
		}
		if(dir=="left"){
			FL.set(-power);
			BL.set(-power);
			FR.set(power);
			BR.set(power);
		}
	}
}
