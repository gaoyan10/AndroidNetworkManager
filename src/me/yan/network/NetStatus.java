package me.yan.network;

public enum NetStatus {
	WEAK(1, "weak"), GOOD(0, "good"), BREAK(2, "break"), UNKNOW(-1, "unknown"), WAIT(3, "wait");
	private NetStatus(int value, String status) {
		this.value = value;
		this.status = status;
	}
	private int value = -1;
	private String status = "unknown";
	public String toString() {
		return super.toString() + "[" + status + "]";
	}
}
