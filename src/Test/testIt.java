package Test;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;

public class testIt {
	CANTalon FR = new CANTalon(0);
	CANTalon FL = new CANTalon(1);
	CANTalon BR = new CANTalon(2);
	CANTalon BL = new CANTalon(3);
	Joystick box = new Joystick(1);
	public testIt(){
		if(box.getRawButton(1)){
			FR.set(1);
		}
		if(box.getRawButton(4)){
			FL.set(1);
		}
		if(box.getRawButton(3)){
			BR.set(1);
		}
		if(box.getRawButton(2)){
			BL.set(1);
		}
	}
}
