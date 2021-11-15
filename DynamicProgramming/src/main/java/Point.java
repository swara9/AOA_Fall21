public class Point{
    public int x;
    public float y;

    public Point(int x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
