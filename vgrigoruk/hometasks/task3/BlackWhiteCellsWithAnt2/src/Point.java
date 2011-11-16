
public class Point {

	private long x=0;
	private long y=0;
	
	public Point(long pX, long pY) {
		x = pX;
		y = pY;
	}
	
	public long getX() {
		return x;
	}
	
	public long getY() {
		return y;
	}
	
	public void setX(long pX) {
		x = pX;
	}
	
	public void setY(long pY) {
		y = pY;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Point) {
			return (x == ((Point)other).getX() ) && (y == ((Point)other).getY());
		}
		else return false;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash += hash*17+(int)x;
		hash += hash*31+(int)y;
		return hash;
	}

	@Override
	public String toString() {
		return "["+x+","+y+"]";
	}
	
	
	
	
}
