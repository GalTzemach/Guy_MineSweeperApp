package AppLogic.GameLogic;

public class GameEvent extends java.util.EventObject{
	private static final long serialVersionUID = 1L;
	private long time;
	private int level;
	
	public GameEvent(Object source, long time, int level) {
		super(source);
		this.time = time;
		this.level = level;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	

}
