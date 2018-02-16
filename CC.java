/*************************************************************************
 *  Compilation:  javac CC.java
 *  Dependencies: Graph.java
 *
 *  Compute connected components using depth first search.
 *  Runs in O(E + V) time.
 *
 *************************************************************************/

//Edits made by Evan Gutman---erg53@pitt.edu

public class CC {
    private boolean[] marked;   // marked[v] = has vertex v been marked?
    private int[] id;           // id[v] = id of connected component containing v
    private int[] size;         // size[v] = number of vertices in component containing v
    private int count;          // number of connected components
    public int[] startV;   //starting vertex for each component


    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        startV= new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v] && G.adjDeleted[v].isEmpty()) {
                dfs(G, v);
                startV[count]=v;
                count++;
            }
        }
    }

    // depth first search
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[v]++;
        for (Edge e : G.adj(v)) {   //edit
            int w=e.other(v);   //edit
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    // id of connected component containing v
    public int id(int v) {
        return id[v];
    }

    // size of connected component containing v
    public int size(int v) {
        return size[id[v]];
    }

    // number of connected components
    public int count() {
        return count;
    }

    // are v and w in the same connected component?
    public boolean areConnected(int v, int w) {
        return id(v) == id(w);
    }


}