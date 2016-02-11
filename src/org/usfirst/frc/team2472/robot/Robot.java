
package org.usfirst.frc.team2472.robot;

import java.util.ArrayList;

import com.kauailabs.nav6.frc.IMUAdvanced;

import Objects.Action;
import Subsystems.Drive;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import Subsystems.driveForward;
import Subsystems.turnRight;
import Test.testIt;

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

	Joystick JoyL = new Joystick(0);
	Joystick JoyR = new Joystick(1);
	Joystick Box = new Joystick(2);
	Drive d = new Drive(3, 0, 1, 2);
	IMUAdvanced imu;

	SerialPort serial_port;

	ArrayList<Objects.Action> step = new ArrayList<Action>();

	ArrayList<Action> stepSecondary = new ArrayList<Action>();

	int currentAction = 0;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		Timer.delay(2);
		try {
			DriverStation.reportError("Starting Try", false);
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

			DriverStation.reportError("No IMU", false);

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
				step.add(new turnRight(33, d, imu));
				step.add(new driveForward(1.0, d));
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
		if (Box.getRawButton(1)) {
			step.add(new driveForward(1, d, 1));
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {

		double throttle = (-JoyL.getThrottle() + 1.0) / 2.0;
		d.tank(JoyL.getX(), JoyR.getX());

		SmartDashboard.putNumber("IMU Yaw", imu.getYaw());

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		testIt t = new testIt();
	}

}
