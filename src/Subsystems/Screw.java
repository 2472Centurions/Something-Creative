package Subsystems;

import edu.wpi.first.wpilibj.CANTalon;


public class Screw {
CANTalon screw = new CANTalon(40);
public void extend(){
	screw.set(1);
}
public void pullin(){
	screw.set(-1);
}


}
