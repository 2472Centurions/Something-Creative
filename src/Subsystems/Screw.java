package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;


public class Screw {
CANTalon screw = new CANTalon(6);
public void extend(){
	screw.set(1);
}
public void pullin(){
	screw.set(-1);
}


}
