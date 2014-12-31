package tetrismina.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
	IDLE("idle"), PLAYING("playing");

	private static final Map<String, UserStatus> lookup = new HashMap<String, UserStatus>();

	static {
		for (UserStatus status : EnumSet.allOf(UserStatus.class)) {
			lookup.put(status.getStatus(), status);
		}
	}

	private String status;

	UserStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public String toString() {
		return this.status;
	}

	public static UserStatus get(String status) {
		return lookup.get(status);
	}
}
