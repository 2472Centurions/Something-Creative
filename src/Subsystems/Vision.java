package Subsystems;

import edu.wpi.first.wpilibj.CameraServer;

public class Vision {

	
	public Vision(){ 
		
	}
	public static void camera(CameraServer server){
		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam3");
	}
}
