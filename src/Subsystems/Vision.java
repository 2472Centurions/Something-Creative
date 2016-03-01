package Subsystems;

import edu.wpi.first.wpilibj.CameraServer;

public class Vision {
	
	public Vision(){ 
		
	}
	
	public static void camera(CameraServer server, String camNum){
		
		server = CameraServer.getInstance();
		
		server.setQuality(50);
		
		server.startAutomaticCapture(camNum);
	
	
	}
}