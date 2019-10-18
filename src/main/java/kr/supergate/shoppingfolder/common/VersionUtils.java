package kr.supergate.shoppingfolder.common;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

public class VersionUtils {
	
	public static Boolean isNewer(String appVersionStr, String comapreVersionStr) {
		DefaultArtifactVersion appVersion = new DefaultArtifactVersion(appVersionStr);
		DefaultArtifactVersion comapreVersion = new DefaultArtifactVersion(comapreVersionStr);
		
		return appVersion.compareTo(comapreVersion) >= 0;
	}
}
