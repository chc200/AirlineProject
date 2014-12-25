import java.util.LinkedList;

/*************************************************************************
 *  Compilation:  javac DirectedEdge.java
 *  Execution:    java DirectedEdge
 *
 *  Immutable weighted directed edge.
 *
 *************************************************************************/
/**
 *  The <tt>DirectedEdge</tt> class represents a weighted edge in an 
 *  {@link EdgeWeightedDigraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the directed edge and
 *  the weight.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public class DirectedEdge { 

    private final int start;
    private final int end;
    private final double miles;
    private final double cost;

    /**
     * Initializes a directed edge from vertex <tt>v</tt> to vertex <tt>w</tt> with
     * the given <tt>weight</tt>.
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     * @throws java.lang.IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
     *    is a negative integer
     * @throws IllegalArgumentException if <tt>weight</tt> is <tt>NaN</tt>
     */
    public DirectedEdge(int start, int end, int miles, double cost) {
        if (start < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (end < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (Double.isNaN(cost)) throw new IllegalArgumentException("miles is NaN");
        this.start = start;
        this.end = end;
        this.miles = miles;
        this.cost=cost;
    }
    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return start;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return end;
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
    public double weight() {
        if(miles ==0)
            return cost;
        else
            return miles;
    }

    /**
     * Returns a string representation of the directed edge.
     * @return a string representation of the directed edge
     */
    /*public String toString() {
        return start + "->" + end + " " + String.format("%5.2f", weight);
    }*/

    /**
     * Unit tests the <tt>DirectedEdge</tt> data type.
     */
    /*public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(12, 23, 3.14);
        StdOut.println(e);
    }*/
}
