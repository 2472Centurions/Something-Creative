
package org.usfirst.frc.team2472.robot;

import java.util.ArrayList;

import com.kauailabs.nav6.frc.IMUAdvanced;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import Actions.driveForward;
import Actions.driveStraightUntilOverDefenses;
import Actions.driveStraightUntilOverDefenses_022316;
import Actions.intakeBall;
import Actions.shootBall;
import Actions.shootIntake;
import Actions.turnRight;
import Actions.turnToZero;
import Constants.Const;
import Objects.Action;
import Subsystems.Drive;
import Subsystems.Hammer;
import Subsystems.Intake;
import Subsystems.IntakePnue;
import Subsystems.Scissors;
import Subsystems.Screw;
import Subsystems.ScrewPnue;
import Subsystems.Suspension;
import Subsystems.Vision;
import Subsystems.Winch;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
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
	//int bits;
	//SensorBase dist;
	boolean toggleSuspensionOn = true;
	int currSession, sessionFront, sessionBack;
	//exampleAnalog.getOversampleBits();
	//bits = exampleAnalog.getOversampleBits();
	//exampleAnalog.setAverageBits(2);
	//bits = exampleAnalog.getAverageBits();
	Compressor compressor = new Compressor(Const.compressorS);
	long starttime= System.currentTimeMillis();
	public static IntakePnue intakePnue = new IntakePnue(Const.intakePnueS);
	Image frame;

	Joystick JoyL = new Joystick(Const.lStick);
	Joystick JoyR = new Joystick(Const.rStick);
	Joystick JoyPad = new Joystick(Const.jPad);
	Joystick Box = new Joystick(Const.sBox);
	
	public static Drive d = new Drive(Const.FL, Const.FR, Const.BL, Const.BR);
	
	public static IMUAdvanced imu;

	public static Intake intake = new Intake(Const.intakeM);

	public static Winch winch = new Winch(Const.winchM);
	
	public static Hammer hammer = new Hammer(Const.hammerM);
	
	public static Screw screw = new Screw(Const.screwM);
	
	public static ScrewPnue screwPnue = new ScrewPnue(Const.ScrewPnueS1,Const.ScrewPnueS2);
	
	public static Scissors scissors = new Scissors(Const.ScissorsS1,Const.ScissorsS2);
	
	public static Suspension suspension = new Suspension(Const.suspensionS1, Const.suspensionS2);

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
		sessionFront = NIVision.IMAQdxOpenCamera("cam2", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		sessionBack = NIVision.IMAQdxOpenCamera("cam3", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		currSession = sessionFront;
		NIVision.IMAQdxConfigureGrab(currSession);
		scissors.reload();
		suspension.shoot();
		// open the camera at the IP address assigned. This is the IP address
		// that the camera
		// can be accessed through the web interface.
		//camera = new AxisCamera("10.1.91.100");

		// open the camera at the IP address assigned. This is the IP address
		// that the camera
		// can be accessed through the web interface.
		//camera2 = new AxisCamera("10.1.91.100");

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
			step.clear();
			stepSecondary.clear();
			step.add(new shootIntake(2.0));
			stepSecondary.add(new Action());
			step.add(new intakeBall(2.0));
			stepSecondary.add(new driveStraightUntilOverDefenses(4.0));
			step.add(new turnToZero(2.0));
			step.add(new shootBall(1.0));
			stepSecondary.add(new Action());
			step.add(null);
			stepSecondary.add(null);
			// (time to drive forward, drive)
		}
		if (Box.getRawButton(2)) {
			
			step.clear();
			stepSecondary.clear();
			step.add(new driveStraightUntilOverDefenses(4.0));
			stepSecondary.add(new Action());
			step.add(new turnToZero(2.0));
			stepSecondary.add(new Action());
			step.add(null);
			stepSecondary.add(null);
			
		}
		if (Box.getRawButton(3)) {
			step.clear();
			stepSecondary.clear();
			step.add(new driveStraightUntilOverDefenses(4.0));
			stepSecondary.add(new Action());
			step.add(new turnToZero(2.0));
			stepSecondary.add(new Action());
			step.add(new driveStraightUntilOverDefenses_022316(4.0));
			stepSecondary.add(new Action());
			step.add(null);
			stepSecondary.add(null);
		}
		if(Box.getRawButton(4)){
			

			step.clear();
			stepSecondary.clear();
			step.add(new turnRight(5.0,45));
			stepSecondary.add(new Action());
			step.add(null);
			stepSecondary.add(null);
			
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
		
		
		SmartDashboard.putNumber("Pitch", imu.getPitch());
		SmartDashboard.putNumber("YAW", imu.getYaw());

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
		NIVision.IMAQdxGrab(currSession, frame, 1);
		CameraServer.getInstance().setImage(frame);
		
		if(JoyR.getRawButton(3)){
			if (Robot.imu != null && !Const.zeroS) {

				Robot.imu.zeroYaw();
				
				Const.zeroS = true;

			}
			if(JoyL.getY()<0){
			if(Robot.imu.getYaw()>Const.yawDeadZone)
				Robot.d.tankdrive1(.8*-JoyL.getY(),1*-JoyL.getY());
			
			if(Robot.imu.getYaw()<-Const.yawDeadZone)
				Robot.d.tankdrive1(1*-JoyL.getY(),.8*-JoyL.getY());
			
			if(Robot.imu.getYaw()>-Const.yawDeadZone&&Robot.imu.getYaw()<Const.yawDeadZone)
				Robot.d.tankdrive1(1 * -JoyL.getY(), 1 * -JoyL.getY());
			}else{
				if(Robot.imu.getYaw()>Const.yawDeadZone)
					Robot.d.tankdrive1(1*-JoyL.getY(),.8*-JoyL.getY());
				
				if(Robot.imu.getYaw()<-Const.yawDeadZone)
					Robot.d.tankdrive1(.8*-JoyL.getY(),1*-JoyL.getY());
				
				if(Robot.imu.getYaw()>-Const.yawDeadZone&&Robot.imu.getYaw()<Const.yawDeadZone)
					Robot.d.tankdrive1(1 * -JoyL.getY(), 1 * -JoyL.getY());
			}
		}
		else{
			d.tank(JoyR, JoyL);
			
			Const.zeroS = false;
		}
		
		
		SmartDashboard.putNumber("ljoystick", JoyPad.getRawAxis(5));
		long currenttime;
		long ttime = 150000;
		long timeleft;

		currenttime=System.currentTimeMillis()-starttime;
		timeleft=ttime-currenttime;
		SmartDashboard.putNumber("time left", timeleft/1000);
		// double throttle = (-JoyL.getThrottle() + 1.0) / 2.0;
		

		if(Box.getRawButton(5)){
			if(currSession == sessionFront){
				
				NIVision.IMAQdxStopAcquisition(currSession);
				currSession = sessionBack;
				NIVision.IMAQdxConfigureGrab(currSession);
				
			}
			else if(currSession == sessionBack){
				
				NIVision.IMAQdxStopAcquisition(currSession);
				currSession = sessionFront;
				NIVision.IMAQdxConfigureGrab(currSession);
				
			}
			
			
		}

		
		hammer.hammerspin(JoyPad);
		
		screw.screwSpin(JoyPad);

		if (JoyPad.getRawButton(Const.jPadButtonA)) {
			intake.outtakeBall();
		}
		else if (JoyPad.getRawButton(Const.jPadButtonB)) {
			intake.intakeBall();
		}
		else
			intake.stopIntake();
		
		if (JoyPad.getRawButton(Const.jPadButtonX)) {
			scissors.shoot();
		}
		
		if (JoyPad.getRawButton(Const.jPadButtonY)) {
			scissors.reload();
		}
		
		if(JoyPad.getRawAxis(2) == 1.0) {
			screwPnue.shoot();
		}
		
		if(JoyPad.getRawAxis(3) == 1.0) {
			screwPnue.reload();
		}
		
		if(JoyR.getRawButton(1)){
			
				suspension.shoot();
			
		}
		if(JoyL.getRawButton(1)){
			
			suspension.reload();
		
		}
		
		if (JoyPad.getRawButton(Const.jPadButtonStart)) {
			intakePnue.goIn();
		}
		
		if (JoyPad.getRawButton(Const.jPadButtonBack)){
			
			intakePnue.out();
			
		}
		

		// SmartDashboard.putNumber("IMU Yaw", imu.getYaw());
	}

	/**oniyhkn
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		
		if (Box.getRawButton(1)) {
			d.runMotor(Const.FR);
		}
		if (Box.getRawButton(2)) {
			d.runMotor(Const.FL);
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
