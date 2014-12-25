import java.util.LinkedList;

/*************************************************************************
 *  Compilation:  javac Edge.java
 *  Execution:    java Edge
 *
 *  Immutable milesed edge.
 *
 *************************************************************************/

/**
 *  The <tt>Edge</tt> class represents a milesed edge in an 
 *  {@link EdgemilesedGraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value miles. The data type
 *  provides methods for accessing the two endpoints of the edge and
 *  the miles. The natural order for this data type is by
 *  ascending order of miles.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Edge { //implements Comparable<Edge> { 

    private final int start;
    private final int end;
    private final int miles;
    private final double cost;
    

    /**
     * Initializes an edge between vertices <tt>v/tt> and <tt>w</tt> of
     * the given <tt>miles</tt>.
     * param v one vertex
     * param w the other vertex
     * param miles the miles of the edge
     * @throws IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt> 
     *    is a negative integer
     * @throws IllegalArgumentException if <tt>miles</tt> is <tt>NaN</tt>
     */
    public Edge(int start, int end, int miles, double cost) {
        if (start < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (end < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (Double.isNaN(cost)) throw new IllegalArgumentException("miles is NaN");
        this.start = start;
        this.end = end;
        this.miles = miles;
        this.cost=cost;
    }

    /**
     * Returns the miles of the edge.
     * @return the miles of the edge
     */
    public int miles() {
        return miles;
    }

    public double cost(){
        return cost;
    }

    /**
     * Returns either endpoint of the edge.
     * @return either endpoint of the edge
     */
    public int start() {
        return start;
    }

    public int either() {
        return start;
    }

    public int end(){
        return end;
    }

    /**
     * Returns the endpoint of the edge that is different from the given vertex
     * (unless the edge represents a self-loop in which case it returns the same vertex).
     * @param vertex one endpoint of the edge
     * @return the endpoint of the edge that is different from the given vertex
     *   (unless the edge represents a self-loop in which case it returns the same vertex)
     * @throws java.lang.IllegalArgumentException if the vertex is not one of the endpoints
     *   of the edge
     */
    public int other(int vertex) {
        if      (vertex == start) return end;
        else if (vertex == end) return start;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    /**
     * Compares two edges by miles.
     * @param that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *    this edge is less than, equal to, or greater than that edge
     */
    public int compareMile(Edge that) {
        if      (this.miles() < that.miles()) return -1;
        else if (this.miles() > that.miles()) return +1;
        else                                    return  0;
    }

        public int compareCost(Edge that) {
        if      (this.cost() < that.cost()) return -1;
        else if (this.cost() > that.cost()) return +1;
        else                                    return  0;
    }

    /**
     * Returns a string representation of the edge.
     * @return a string representation of the edge
     */
    public String toString() {
        return String.format("%d-%d %.5f", start, end, miles);
    }

}
