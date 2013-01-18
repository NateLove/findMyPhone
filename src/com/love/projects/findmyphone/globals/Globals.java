package com.love.projects.findmyphone.globals;

public class Globals {
	
	 private static Globals singleton = null;
	 public String volumeOnlyBoolean = "VolumeOnlyBoolean";
	 public String volumeAndAlarmBoolean = "VolumeAndAlarmBoolean";
	 public String volumeOnlyString = "VolumeOnlyString";
	 public String VolumeAndAlarmString = "VolumeAndAlarmString";
	 
	 
	 
	 public static Globals getGlobals()
	 {
		 if(singleton == null)
			 singleton = new Globals();
		 return singleton;
	 }

}
