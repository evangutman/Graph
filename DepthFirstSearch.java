/*************************************************************************
 *  Compilation:  javac DepthFirstSearch.java
 *  Execution:    java DepthFirstSearch filename s
 *  Dependencies: Graph.java
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstSearch tinyG.txt 0
 *  0 1 2 3 4 5 6
 *  not connected
 *
 *  % java DepthFirstSearch tinyG.txt 9
 *  9 10 11 12
 *  not connected
 *
 *************************************************************************/

//Edits made by Evan Gutman----erg53@pitt.edu

public class DepthFirstSearch {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int count;           // number of vertices connected to s

    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // depth first search from v
    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;  // CS 1501 Summer 2016
        // I added this line to Sedgewick's code.  Otherwise
        // every graph would be considered to be disconnected
        for (Edge e : G.adj(v)) {
            int w=e.other(v);
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    // is there an s-v path?
    public boolean marked(int v) {
        return marked[v];
    }

    // number of vertices connected to s
    public int count() {
        return count;
    }
}