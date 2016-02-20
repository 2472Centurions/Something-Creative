package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;

public class Intake {
	
	CANTalon intakeM;
	
	public Intake(int iM){
	
		intakeM = new CANTalon(iM);
	
	}
	
	public void intakeBall(){
		
		intakeM.set(-.5);
		
	}

	public void outtakeBall(){
		
		intakeM.set(.5);
		
	}
	
	public void stopIntake(){
		
		intakeM.set(0.0);
		
	}
}
