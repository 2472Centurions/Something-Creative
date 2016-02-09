
package org.usfirst.frc.team2472.robot;

import java.util.ArrayList;

import com.kauailabs.nav6.frc.IMUAdvanced;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import Actions.driveForward;
import Objects.Action;
import Subsystems.Drive;
import Subsystems.Intake;
import Subsystems.Sizzors;
import Subsystems.Winch;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser chooser;

	Compressor c = new Compressor(0);

	CANTalon c7 = new CANTalon(7);
Sizzors s = new Sizzors();
	int session, session2;
	Image frame, frame2;
	AxisCamera camera, camera2;

	Joystick JoyL = new Joystick(0);
	Joystick JoyR = new Joystick(1);
	Joystick JoyPad = new Joystick(2);
	Joystick Box = new Joystick(3);
	Drive d = new Drive(4, 2, 1, 3);
	IMUAdvanced imu;

	Intake i = new Intake(5);

	Winch w = new Winch(6);

	SerialPort serial_port;

	ArrayList<Objects.Action> step = new ArrayList<Action>();

	ArrayList<Action> stepSecondary = new ArrayList<Action>();

	int currentAction = 0;

	int FL = 0;
	int FR = 1;
	int BL = 2;
	int BR = 3;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		c.setClosedLoopControl(true);
		d.cantaloninit(24);
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

		// open the camera at the IP address assigned. This is the IP address
		// that the camera
		// can be accessed through the web interface.
		camera = new AxisCamera("10.1.91.100");

		frame2 = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

		// open the camera at the IP address assigned. This is the IP address
		// that the camera
		// can be accessed through the web interface.
		camera2 = new AxisCamera("10.1.91.100");

		Timer.delay(2);
		try {
			DriverStation.reportError("Starting Try", false);
			Timer.delay(1.0);
			serial_port = new SerialPort(57600, SerialPort.Port.kUSB);
			DriverStation.reportError("Serial Port OK", false);
			// You can add a second parameter to modify the
			// update rate (in hz) from 4 to 100. The default is 100.
			// If you need to minimize CPU load, you can set it to a
			// lower value, as shown here, depending upon your needs.

			// You can also use the IMUAdvanced class for advanced
			// features.

			byte update_rate_hz = 50;
			// imu = new IMU(serial_port,update_rate_hz);
			imu = new IMUAdvanced(serial_port, update_rate_hz);
			DriverStation.reportError("IMU OK", false);
		} catch (Exception ex) {
			String ere = ex.getMessage();
			DriverStation.reportError("No IMU", false);
			DriverStation.reportError(ere, false);
		}

		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
		if (Box.getRawButton(1)) {
			step.add(new driveForward(1.0, d));
			// (time to drive forward, drive)
		}
		if (Box.getRawButton(2)) {

		}

		autoSelected = (String) chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		if (step.size() > 0) {

			currentAction = 0;

			step.get(currentAction).startAction();

			// stepSecondary.get(currentAction).startAction();

		}
		currentAction = 0;
		step.get(currentAction).startAction();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {

		if (step.size() > 0 && step.get(currentAction) != null) {

			if (!step.get(currentAction).isFinished()) {
				step.get(currentAction).periodic();
			}

			if (!stepSecondary.get(currentAction).isFinished()) {
				stepSecondary.get(currentAction).periodic();
			}

			if (step.get(currentAction).isFinished() && stepSecondary.get(currentAction).isFinished()) {

				currentAction++;

				if (step.get(currentAction) != null) {

					step.get(currentAction).startAction();
					stepSecondary.get(currentAction).startAction();

				}

			}

		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {

		// double throttle = (-JoyL.getThrottle() + 1.0) / 2.0;
		d.tank(JoyL, JoyR);

		if (JoyPad.getRawButton(1)) {
			i.intakeBall();
		}
		if (JoyPad.getRawButton(2)) {
			i.outtakeBall();
		}
		if (JoyPad.getRawButton(3)) {
			w.Reel();
		}

		// SmartDashboard.putNumber("IMU Yaw", imu.getYaw());
	}

	/**oniyhkn
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		if (Box.getRawButton(6)) {
			s.goIn();
		}
		if (Box.getRawButton(7)) {
			s.out();
		}
		if (Box.getRawButton(8)) {

			s.off();

		}
		if (Box.getRawButton(1)) {
			d.runMotor(FL);
		}
		if (Box.getRawButton(2)) {
			d.runMotor(FR);
		}
		if (Box.getRawButton(3)) {
			d.runMotor(BL);
		}
		if (Box.getRawButton(4)) {
			d.runMotor(BR);
		}
		if (Box.getRawButton(5)) {
			d.stopMotors();

			c7.set(0.0);
		}
		// if(Box.getRawButton(6)){
		// i.set(1.0);
		// }
		// if(Box.getRawButton(7)){
		// w.set(1.0);
		// }
		if (Box.getRawButton(8)) {
			c7.set(1.0);
		}

	}

}
