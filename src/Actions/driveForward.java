package Actions;

import com.kauailabs.nav6.frc.IMUAdvanced;

import Objects.Action;
import Subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class driveForward extends Action{
	
	private long endTime;
	
	private double t;
	
	private double powerCurve = 2;
	
	private Drive d;
	
	IMUAdvanced imu;
	
	private double speed = .75;
	
	public driveForward(double time, Drive RobotDrive){
		
		t = time;
		
		d = RobotDrive;
		
		d.setPowerCurve(2);
		
		d.setDeadZone(.04);
		
		//in = new intakeWheel[0];
		
	}
	
	public driveForward(double time, Drive RobotDrive, double speeed){
		
		t = time;
		
		d = RobotDrive;
		
		d.setPowerCurve(2);
		
		d.setDeadZone(.04);
		
		speed = speeed;
		
	}
	
	public driveForward(double time, Drive RobotDrive, IMUAdvanced IMU){
		
		t = time;
		
		d = RobotDrive;
		
		imu = IMU;
		
	}
	
	public driveForward(double time, Drive RobotDrive, IMUAdvanced IMU, double PowerCurve){
		
		t = time;
		
		d = RobotDrive;
		
		imu = IMU;
		
		powerCurve = PowerCurve;

		
	}
	
	public void startAction(){
		
		super.startAction();
		setTimeOut((long)(t * 1000));
		//runs code in the Start Action void in Action
		//startTime = System.currentTimeMillis();
		
		//endTime = startTime + (long)(t * 1000);
		
		//endFactor = endTime <= System.currentTimeMillis();
		
		d.setPowerCurve(powerCurve);
		
		SmartDashboard.putNumber("Drive Forward Timeout", endTime);
		
		//setTimeOut();
		
		//if(t > 5){
			
			//overRideFailSafe();
			
		//}
		
		if(imu != null){
			
			imu.zeroYaw();
			
		}
		
	}
	
	public void periodic(){
		
		//endFactor = (endTime <= System.currentTimeMillis());
		
		SmartDashboard.putNumber("End Time", endTime);
		
		if(imu != null){
		
			d.arcadeDrive(speed, (-imu.getYaw() * .06 + .2), 1);
		
		}else{
			
			d.arcadeDrive(speed, 0, 1);
			
		}
		

		
		
	}
	
	public void endAction(){
		
		d.arcadeDrive( 0 , 0 , 1 );

		
		SmartDashboard.putBoolean("Ended", true);
		
	}

}
