package com.mygdx.claninvasion.model.map;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    private ArrayList<ArrayList<Integer>> adj;
    private final WorldMap map;
    private final int size;
    private final int arraysize;

    public Graph(int size , WorldMap map) {
        this.size = size*size;
        this.arraysize = size;
        adj = new ArrayList<>(this.size);
        for (int i = 0; i < this.size; i++) {
            adj.add(new ArrayList<>(this.size));
        }

        this.map = map;

        for (int i = 0; i < this.size ; i ++) {
            this.addEdgesToSurroundingBlock(i);
        }
    }

    public void addEdgesToSurroundingBlock(int coordinate) {
        int x = convertCoordinateToX(coordinate);
        int y = convertCoordinateToY(coordinate);
        if(y -  1 >= 0 && map.getCell(((x * arraysize) + y) -  1).getOccupier() == null  ) {
            addLeftBlockCoordinate(coordinate ,((x * arraysize) + y) -  1 );
        }

        if( y +  1 < arraysize && map.getCell(((x * arraysize) + y) +  1).getOccupier() == null) {
            addRightBlockCoordinate(coordinate , ((x * arraysize) + y) +  1);
        }

        if(((x * arraysize) + y) +  arraysize < adj.size() && map.getCell(((x * arraysize) + y) +  arraysize).getOccupier() == null){
            addLowerBlockCoordinate(  coordinate , ((x * arraysize) + y) +  arraysize );
        }

        if( ((x * arraysize ) + y) - arraysize >= 0 && map.getCell(((x * arraysize) + y) -  arraysize).getOccupier() == null){
            addUpperBlockCoordinate(coordinate ,  ((x * arraysize ) + y) - arraysize);
        }


    }

    public void addUpperBlockCoordinate(int coordinate , int addedElement ) {
        adj.get(coordinate).add(addedElement);
    }

    public void addLowerBlockCoordinate(int coordinate , int addedElement) {
        adj.get(coordinate).add(addedElement );
    }

    public void addLeftBlockCoordinate(int coordinate , int addedElement) {
        adj.get(coordinate).add(addedElement);
    }

    public void addRightBlockCoordinate(int coordinate , int addedElement) {
        adj.get(coordinate).add(addedElement );
    }


    public int convertCoordinateToX( int coordinate ) {
        return   coordinate / (arraysize);
    }

    public int convertCoordinateToY( int coordinate) {
        return (coordinate   % (arraysize)) ;
    }

    /*
     * A utility function to print the adjacency list representation of graph
     * */
    public void printGraph() {
        for (int i = 0; i < adj.size(); i++) {
            System.out.println("\nAdjacency list of vertex"
                    + i);
            System.out.print("head");
            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(" -> "
                        + adj.get(i).get(j));
            }
            System.out.println();
        }

    }



    /*function to print the shortest distance and path
     between source vertex and destination vertex
    */
    public LinkedList<Integer> getShortestDistance(int s, int dest, int v) {
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        int[] pred = new int[v];
        int[] dist = new int[v];

        if (!BFS(adj, s, dest, v, pred, dist)) {
            System.out.println("Given source and destination" + "are not connected");
            return null;
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        // Print distance
        System.out.println("Shortest path length is: " + dist[dest]);

        return path;

    }

    /* a modified version of BFS that stores predecessor
    * of each vertex in array pred
    * and its distance from source in array dist
    * @returns Boolean
    */
    private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src,
                               int dest, int v, int[] pred, int[] dist) {
        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Integer type
        LinkedList<Integer> queue = new LinkedList<>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean[] visited = new boolean[v];

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (!visited[adj.get(u).get(i)]) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    // stopping condition (when we find
                    // our destination)
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }
}
