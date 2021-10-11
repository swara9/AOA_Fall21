import java.util.Comparator;

class EdgeComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge e1, Edge e2) {
        return Integer.compare(e2.weight, e1.weight);
    }
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

    @Override
    public String toString() {
        return "(" + node1 +", " + node2 +", "+weight+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        if (node1 != edge.node1) return false;
        return node2 == edge.node2;
    }

    @Override
    public int hashCode() {
        int result = node1;
        result = 31 * result + node2;
        return result;
    }
}
