
package Subsystems;

import com.kauailabs.nav6.frc.IMU;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	CANTalon[] moter = new CANTalon[4];
	CANTalon FL,FR,BL,BR;
	
	double x;
	double y;
	double z;
	double X;
	double Y;
	double Z;
	private RobotDrive d;
	double range,powerCurve;
	public Drive(int FL, int FR, int BL, int BR){
		moter[0] = new CANTalon(FL);
		moter[1] = new CANTalon(FR);
		moter[2] = new CANTalon(BL);
		moter[3] = new CANTalon(BR);
		d = new RobotDrive(moter[0],moter[1],moter[2],moter[3]);
	}

	public void setPowerCurve(double PowerCurve){
		
    	powerCurve = PowerCurve;
    	
    	//powerCurve = 1.1;
    }
	
	public void setDeadZone(double DeadZone){
		
		range = DeadZone;
	}
	
	public void arcadeDrive(double forward, double rotation, double throtle){

		
	       
        y = forward;
      
        z = rotation;
      
        if(z < -range){
            
            X =  Math.pow(z , powerCurve);
           
        }else if(z > range) {
            
            X =  -(Math.pow(z, powerCurve));
            
        }else{
            
            X = 0;
            
        }
        
        if(y < range){
            
            Y =  -Math.pow(y , powerCurve);
            
        }else if(y > range) {
            
            Y =  (Math.pow(y , powerCurve));
            
        }else{
            
            Y = 0;
            
        }
        
        d.arcadeDrive(Y * throtle, X * throtle);
        
        
        
    }
	
	public void tank(double XX1, double XX2){
		
		if(XX1 < -range){
			
			X =  Math.pow(XX1 , powerCurve);
       
		}else if(XX1 > range) {
        
			X =  -(Math.pow(XX1, powerCurve));
        
		}else{
        
			X = 0;
        
        }
    
		if(XX2 < range){
        
			Y =  Math.pow(XX2 , powerCurve);
        
		}else if(XX2 > range) {
        
			Y =  -(Math.pow(XX2 , powerCurve));
        
		}else{
        
			Y = 0;
        
    	}
		
		d.tankDrive(X, Y);
		
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
	public void kill(){
		FL.set(0);
		BL.set(0);
		FR.set(0);
		BR.set(0);
	}
	
}
