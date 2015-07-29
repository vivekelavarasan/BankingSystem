package bank.project;

public class Result {
	private int value;
	private boolean status;

	public Result(boolean t_status, int t_value) {
		super();
		status = t_status;
		value = t_value;
	}
	public int get_value() {
		return value;
	}
	public boolean is_ok() {
		return status;
	}
}
