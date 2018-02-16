# Graph
This program demonstrates different operations performed on a weighted bidirectional graph implemented using an adjacency list.

This graph represents a simple computer network. As the network changes, the following will be reported:

1) Whether or not the network is currently connected
2) Which nodes are currently up or down in the network
3)A listing of the current subnetworks and their vertices and edges (i.e. the connected components within the network)
4)A minimum spanning tree (by latencies) for the network
5)The shortest (i.e. smallest latency) path from arbitrary vertex i to arbitrary vertex j.
6)The distinct paths from vertex i to vertex j whose latencies sum to a value <= x.

Command line arguments:
  A graph will be read in from a file in the following format:
    The first line represents V (the number of vertices in the graph)
    The Second line represents E (the number of edges in the graph)
    The remaining E lines will contain triples to represent the actual edges in the graph. The first two values are 
      the two vertices and the third is the weight of the edge, which in this case will be the latency for the edge (smaller weight indicates a faster traversal on the edge)
      
      
List of Program Commands:
  - R(eport) - displays the current active network (all active nodes and edges, including edge weights); show the status of the network (
    connected or not); show the connected components of the network
  - M(inimum spanning Tree) - shows the vertices and edges (with weights) in the current minimum spanning tree of the network. If the 
    graph is not connected this command should show the minimum spanning tree of each connected subgraph.
  - S i j -display the shortest path (by latency) from vertex i to vertex j in the graph. If vertices i and j are not connected this fact 
    should be stated.
  - P i j x -display each of the distinct paths (differing by at least one edge) from vertex i to vertex j with total weight less 
    than or equal to x. All of the vertices and edges in each path should be shown and the total number of distinct paths should be shown at the end.
  - D i -node i in the graph will go down. All edges incident upon i will be removed from the graph as well
  - U i -node i in the graph will come back up. All edges previously incident upon i (before it went down) will now come back online with their
     previous weight values.
  - C i j x -change the weight of edge (i,j) (and of edge (j, i)) in the graph to value x.  If x is <= 0 the edge(s) should be removed from the 
    graph.  If x >0 and edge (i, j) previously did not exist, create it. 
  - Q -quit the program




