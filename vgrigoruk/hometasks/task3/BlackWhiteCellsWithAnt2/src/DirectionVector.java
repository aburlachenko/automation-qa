
public class DirectionVector {

	private Point vectorEndPoint;
	private static double angle=Math.PI/2;
	
	public DirectionVector() {
		vectorEndPoint = new Point(1, 0);
	}
	
	public Point getCurrentDirection() {
		return vectorEndPoint;
	}
	
	public void turnLeft() {
		vectorEndPoint = turn(-angle);
	}
	
	public void turnRight() {
		vectorEndPoint = turn(angle);
	}
	
	private Point turn(double angle) {
		long x = vectorEndPoint.getX();
		long y = vectorEndPoint.getY();
		long newX = (long)Math.round(x*Math.cos(angle)+y*Math.sin(angle));
		long newY = (long)Math.round(-x*Math.sin(angle)+y*Math.cos(angle));
		return new Point(newX, newY);
	}
}
