/**
 * Main graph class taken from Sedgewick's EdgeWeightedGraph.java with edits/additions
 */

//Evan Gutman---erg53@pitt.edu



import java.util.*;



public class Graph{
    /*Variables for recursive backtracking*/
    boolean[] marked;    // marked[v] = is there an s-v path?
    int source;         // source vertex
    int destination;
    double weightTracker;
    double maxWeight;
    int numberOfPaths;
    Stack<Edge> edgeStack= new Stack<Edge>();


    /*Variables used for taking nodes down and bringing them back up*/
    Bag<Edge>[] adjDeleted;


    /*variables used for the graph*/
    private int V;
    private int E;
    private Bag<Edge>[] adj;

    /**
     * Create an empty edge-weighted graph with V vertices.
     */
    public Graph(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        adjDeleted=(Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) adj[v] = new Bag<Edge>();   //initialize array of bags to empty bag
        for (int v = 0; v < V; v++) adjDeleted[v] = new Bag<Edge>();    //initialize array of deletedBags to empty bags
    }

    /**
     * Create a random edge-weighted graph with V vertices and E edges.
     * The expected running time is proportional to V + E.
     */
    public Graph(int V, int E) {
        this(V);
        if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            double weight = Math.round(100 * Math.random()) / 100.0;
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    /**
     * Create a weighted graph from input stream.
     */
    public Graph(In in) {
        this(in.readInt());
        E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
            Edge c = new Edge(w, v, weight);
            addEdge(c);
        }
    }

    /**
     * Return the number of vertices in this graph.
     */
    public int V() {
        return V;
    }



    /**
     * Return the number of edges in this graph.
     */
    public int E() {
        return E;
    }




    /**
     * Add the edge e to this graph.
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        //adj[w].add(e);
        //E++;
    }

    /**
     * delete edge
     */

    public void deleteEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        adj[v].remove(e);
    }




    /**
     * Return the edges incident to vertex v as an Iterable.
     * To iterate over the edges incident to vertex v, use foreach notation:
     * <tt>for (Edge e : graph.adj(v))</tt>.
     */
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    /**
     * Return all edges in this graph as an Iterable.
     * To iterate over the edges, use foreach notation:
     * <tt>for (Edge e : graph.edges())</tt>.
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }



    /**
     * Return a string representation of this graph.
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


    public static void main (String [] args){



        Graph graph=new Graph(new In(args[0])); //initialize the graph
        boolean end=false;  //flag when user enters the Q command
        Scanner sc=new Scanner(System.in);  //create scanner object for user input
        //EdgeWeightedGraph G= new EdgeWeightedGraph(new In(args[0]));    //initialize the graph from input file




        System.out.println("Menu:\n");
        System.out.println("Commands:\tAction:\n");
        System.out.println("[R]\t\t\tReport");
        System.out.println("[M]\t\t\tMinimum Spanning Tree");
        System.out.println("[S i j]\t\tShortest path from vertex i to vertex j");
        System.out.println("[P i j x]\tDistinct paths from vertex i to vertex j with a total weight less than or equal to x");
        System.out.println("[D i]\t\tTake node i down");
        System.out.println("[U i]\t\tBring node i back up");
        System.out.println("[C i j x]\tChange weight of edge between (i,j)");
        System.out.println("[Q]\t\t\tQuit program");

        while(!end) {    //user input loop until user quits program
            System.out.println("\nPlease enter a command: ");
            String s[] = sc.nextLine().split(" ");  //takes the user input as a string array so can parse into appropriate types based on the command
            char c = s[0].charAt(0);
            if(c == 'R'){
                graph.report(graph);
            } else if (c == 'C') {
                int fromI = Integer.parseInt(s[1]);
                int toJ = Integer.parseInt(s[2]);
                double weight = Double.parseDouble(s[3]);
                graph.change(graph,fromI, toJ, weight);
            } else if (c == 'M') {
                graph.mst(graph);
            } else if (c == 'S') {
                int fromI = Integer.parseInt(s[1]);
                int toJ = Integer.parseInt(s[2]);
                graph.shortPath(graph, fromI, toJ);
            } else if (c == 'P') {
                int fromI = Integer.parseInt(s[1]);
                int toJ = Integer.parseInt(s[2]);
                double weight = Double.parseDouble(s[3]);
                graph.distinctPath(graph, fromI, toJ, weight);
            }else if (c == 'D'){
                int vertexDown=Integer.parseInt(s[1]);
                graph.down(graph, vertexDown);
            }else if(c=='U'){
                int vertexUp=Integer.parseInt(s[1]);
                graph.up(graph, vertexUp);
            } else if(c=='Q') {
                end = true;
            }else{
                System.out.println("Unknown command. Please enter one of the commands listed above in the proper format");
            }

        }
        System.exit(0);


    }


    //report method will display the current active nodes and down nodes, show if the graph is connected, and display the connected components
    private void report(Graph G){
        CC cc=new CC(G);
        if(cc.count()==1){  //gets the number of connected components
            System.out.println("The network is currently connected");
        }else{
            System.out.println("The network is currently disconnected");
        }
        System.out.println();
        //have to display nodes up and nodes down


        StringBuilder u= new StringBuilder();   //Stringbuilder to hold nodes that are up
        StringBuilder d= new StringBuilder();   //Stringbuilder to hold nodes that are down

        Bag<Edge> temp;
        for (int v = 0; v < V; v++) {
            temp=adjDeleted[v];
            if(!temp.isEmpty()){
                d.append(v+ " ");   //if bag in deleted array of bags is empty, add to deleted node stringbuilder
            }else{
                u.append(v+ " ");   //else put in the stringbuilder of nodes that are up
            }
        }

        System.out.println();
        System.out.println("The following nodes are currently up:");
        if(u.length()==0){
            System.out.println("None");
        }else{
            System.out.println(u.toString());
        }

        System.out.println("The following nodes are currently down:");
        if(d.length()==0){
            System.out.println("None");
        }else{
            System.out.println(d.toString());
        }

        System.out.println();


        //loop to display the vertices and edges in the different connected components using the depthfirstsearch class
        System.out.println("The connected components are:");
        int v=0;
        while(v<cc.count()){    //will iterate the number of connected components there are
            System.out.println("Component "+v + ":");
            System.out.println();
            DepthFirstSearch dps= new DepthFirstSearch(G, cc.startV[v]);

            for (int i = 0; i < G.V(); i++) {
                if (dps.marked(i)) {
                    System.out.print(i + ": ");
                    for (Edge e : G.adj(i)) {   //print all adjacent edges to the specific vertex in the connected component
                        System.out.print(e + " ");
                    }
                    System.out.println();
                }

            }
            v++;
        }

    }


    private void change(Graph g, int i, int j, double x){

        boolean exist=false; //does the connection exist?
        Edge firstSearch=null;  //check if edge exists before do anything
        for (Edge e : g.adj(i)){
            int temp=e.other(i);
            if(temp==j){
                firstSearch=e;
                exist=true;
            }
        }

        //checks if the edge was found or not
        if(!exist && x<=0){
            System.out.println("The edge you are trying to delete does not exist: Invalid action");
            return;
        }


        if(x<=0) {   //if new weight of edge negative or equal to zero, delete it
            g.deleteEdge(firstSearch);  //delete the edge found initially when doing the first search

            for (Edge e : g.adj(j)){    //delete the edges counter part
                int temp=e.other(j);
                if(temp==i){
                    g.deleteEdge(e);
                }
            }
            System.out.println("Edge "+ i +"->"+ j +" has been deleted");
            E--;

        }else{ //if new weight of edge is positive but does not exist
            if(exist) {//edge exists, change it
                firstSearch.updateWeight(x);

                for (Edge e : g.adj(j)){
                    int temp=e.other(j);
                    if(temp==i){
                        e.updateWeight(x);
                    }
                }

                System.out.println("Weight of edge "+ i +"->"+ j +" has been changed to " + x);
            }else{//create edge if it does not exist already
               Edge e=new Edge(i, j, x);
               g.addEdge(e);
               Edge c = new Edge(j, i, x);
               g.addEdge(c);
               E++;
               System.out.println("Edge "+ i +"->"+ j +" has been add to the graph with a weight of " + x);
            }
        }

    }

    //Minimum spanning tree method. Will display multiple mst if graph is not connected
    private void mst(Graph g){
        System.out.println("The edges in the MST follow:");
        CC cc=new CC(g);
        if(cc.count()==1){  //if only one connected component, then only need to call mst class once
            PrimMSTTrace mst = new PrimMSTTrace(g, cc.startV[0]);   //cc.startV[] is the starting vertex when initially performing the DFS
            for (Edge e : mst.edges())
                System.out.println(e);
        }else{  //if more than one connected component, call multiple times
            int v=0;
            while(v<cc.count()){
                System.out.println("Component "+v + ":");
                PrimMSTTrace mst = new PrimMSTTrace(g, cc.startV[v]);
                for (Edge e : mst.edges()) {
                    System.out.println(e);
                }
                System.out.println();
                v++;
            }
        }


    }

    //Will display the shortest path. if path does not exist, will display "does not exist"
    private void shortPath(Graph g, int i, int j){
        DijkstraSP sp= new DijkstraSP(g, i);

            if (sp.hasPathTo(j)) {

                System.out.printf("The shortest path from %d to %d (%.2f)  ", i, j, sp.distTo(j));
                for (Edge e : sp.pathTo(j)) {
                    System.out.print(e + "   ");
                }
                System.out.println();
            }
            else {
                System.out.printf("Vertices %d to %d are not connected\n", i, j);
            }

    }

    //method will take specified node down and store it temporarily until brought back up
    private void down(Graph g, int v){

        //go through deleted vertex and delete counter part edges
        for (Edge e : g.adj(v)){
            int temp=e.other(v);
            for(Edge t: g.adj(temp)){
                if(v==t.other(temp)){
                    g.deleteEdge(t);
                    E--;
                    break;
                }
            }
        }

        System.out.printf("Vertex %d has gone down\n", v);
        adjDeleted[v]=adj[v];    //put edges from deleted vertex into deleted vertex holder
        adj[v]=new Bag<Edge>();    //delete all edges going to targeted vertex

    }

    //Method will bring specified node back up from temporary storage spot
    private void up(Graph g, int v){
        adj[v]=adjDeleted[v];   //bring back the bag that was previously deleted
        adjDeleted[v]=new Bag<Edge>();   //empty bag in deleted bag array

        //go through vertex just brought back and add the counter edges
        for (Edge e : g.adj(v)){
            int temp=e.other(v);
            Edge t=new Edge(temp, v, e.weight());
            g.addEdge(t);
            E++;
        }

        System.out.printf("Vertex %d has come back up\n", v);
    }



    //method will give the distinct paths under a specified weight between two specified nodes
    private void distinctPath(Graph G, int i, int j, double weight){
        this.source = i;
        this.destination=j;
        marked = new boolean[G.V()];
        this.maxWeight=weight;
        this.weightTracker=0;
        this.numberOfPaths=0;
        System.out.printf("Distinct Paths from %d to %d\n", i, j);
        dp(G, source, marked);  //first call to recurive method
        System.out.printf("Total Paths: %d", numberOfPaths);

    }

    //recursive method to find all of the distinct paths
    private void dp(Graph G, int v, boolean [] markedCopy) {
        marked[v] = true;
        markedCopy = Arrays.copyOf(marked, marked.length);  //copy marked array so will adjust when backtracking

        if(v!=destination && weightTracker<maxWeight) {  //if new node is equal to destination vertex, break
            for (Edge e : G.adj(v)) {
                marked=Arrays.copyOf(markedCopy, markedCopy.length);
                int w = e.other(v);   //get next vertex visiting
                if (!marked[w]) {
                    //edgeTo[w] = v;
                    weightTracker+=e.weight();   //get weight of edge traversing and add it to the total weight tracker
                    edgeStack.push(e);
                    dp(G, w, markedCopy);
                    Edge t=edgeStack.pop(); //when backtracking, pop the top of the stack and delete the weight from weightTracker
                    weightTracker-=t.weight();
                }
            }
        }else if (v==destination && weightTracker<=maxWeight){
            System.out.printf("Path %d: Total Weight: (%.2f)\t", ++numberOfPaths, weightTracker);
            System.out.println(edgeStack.toString());
        }
    }




}