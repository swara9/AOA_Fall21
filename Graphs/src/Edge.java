import java.util.Comparator;

class EdgeComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge e1, Edge e2) {
        if (e1.weight == e2.weight) {
            return 0;
        }
        else if (e1.weight < e2.weight) {
            return 1;
        }
        else {
            return -1;
        }    }
}

public class Edge {
    public int node1;
    public int node2;
    public int weight;

    public Edge(int node1, int node2, int weight){
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    public Edge(int node1, int node2){
        this.node1 = node1;
        this.node2 = node2;
        this.weight = -1;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "(" + node1 +", " + node2 +", "+weight+")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Edge))
            return false;
        Edge other = (Edge)o;
        return this.node1 == other.node1 && this.node2 == other.node2;
    }
}
