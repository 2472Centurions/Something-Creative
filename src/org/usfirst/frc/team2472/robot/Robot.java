
package org.usfirst.frc.team2472.robot;

import java.util.ArrayList;

import com.kauailabs.nav6.frc.IMUAdvanced;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import Actions.driveForward;
import Constants.Const;
import Objects.Action;
import Subsystems.Drive;
import Subsystems.Dummy;
import Subsystems.Hammer;
import Subsystems.Intake;
import Subsystems.Screw;
import Subsystems.Sizzors;
import Subsystems.Winch;
import edu.wpi.first.wpilibj.Compressor;
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
	//final String defaultAuto = "Default";
	//final String customAuto = "My Auto";
	//String autoSelected;
	//SendableChooser chooser;

	Compressor compressor = new Compressor(Const.compressorS);
	
	public static Sizzors sizzors = new Sizzors(Const.sizzorsS);

	int session, session2;
	Image frame, frame2;
	AxisCamera camera, camera2;

	Joystick JoyL = new Joystick(Const.lStick);
	Joystick JoyR = new Joystick(Const.rStick);
	Joystick JoyPad = new Joystick(Const.gPAD);
	Joystick Box = new Joystick(Const.sBox);
	
	public static Drive d = new Drive(Const.FL, Const.FR, Const.BL, Const.BR);
	
	public static IMUAdvanced imu;

	public static Intake intake = new Intake(Const.intakeM);

	public static Winch winch = new Winch(Const.winchM);
	
	public static Hammer hammer = new Hammer(Const.hammerM);
	
	public static Screw screw = new Screw(Const.screwM);
	
	public static Dummy dummy = new Dummy(Const.dummyS1,Const.dummyS2);

	SerialPort serial_port;

	ArrayList<Action> step = new ArrayList<Action>();

	ArrayList<Action> stepSecondary = new ArrayList<Action>();

	int currentAction = 0;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		compressor.setClosedLoopControl(true);
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

		//chooser = new SendableChooser();
		//chooser.addDefault("Default Auto", defaultAuto);
		//chooser.addObject("My Auto", customAuto);
		//SmartDashboard.putData("Auto choices", chooser);

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
			step.add(new driveForward(3.0, 1.0));
			// (time to drive forward, drive)
		}
		if (Box.getRawButton(2)) {

		}

		//autoSelected = (String) chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		//System.out.println("Auto selected: " + autoSelected);
		if (step.size() > 0) {

			currentAction = 0;

			step.get(currentAction).startAction();

			stepSecondary.get(currentAction).startAction();

		}
		//currentAction = 0;
		//step.get(currentAction).startAction();

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
			intake.intakeBall();
		}
		if (JoyPad.getRawButton(2)) {
			intake.outtakeBall();
		}
		if (JoyPad.getRawButton(3)) {
			winch.Reel();
		}
		if(Box.getRawButton(1)){
			
			dummy.shoot();
			
		}
		if(Box.getRawButton(2)){
			
			dummy.reload();
			
		}
		if(Box.getRawButton(3)){
			
			dummy.off();
			
		}
		if(Box.getRawButton(5)){
			
			sizzors.goIn();
			
		}
		if(Box.getRawButton(6)){
			
			sizzors.out();
			
		}

		// SmartDashboard.putNumber("IMU Yaw", imu.getYaw());
	}

	/**oniyhkn
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		
		if (Box.getRawButton(1)) {
			d.runMotor(Const.FL);
		}
		if (Box.getRawButton(2)) {
			d.runMotor(Const.FR);
		}
		if (Box.getRawButton(3)) {
			d.runMotor(Const.BL);
		}
		if (Box.getRawButton(4)) {
			d.runMotor(Const.BR);
		}
		if (Box.getRawButton(5)) {
			d.stopMotors();
		}
	}

}
