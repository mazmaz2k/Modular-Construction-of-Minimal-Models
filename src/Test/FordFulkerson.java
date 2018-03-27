package Test;


import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileReader; 
import java.io.StringReader; 
import java.util.ArrayList; 

public class FordFulkerson {

    int[][] graphAdjMatrixWithCapacity;// Adj Matrix with capacity 
    int nodesCount; 
    int source; 
    int sink; 

    // Initialize the edges to cost 0 in Graph 
    void initGraph() { 
        for (int i = 0; i < nodesCount; i++) {
            for (int j = 0; j < nodesCount; j++) {
                graphAdjMatrixWithCapacity[i][j] = 0; 
            } 
        } 
    } 

    int readTotalGraphCount(BufferedReader bufReader) throws Exception {

        return Integer.parseInt(bufReader.readLine()); 
    } 

    void readNextGraph(BufferedReader bufReader) throws Exception {
        try { 
            nodesCount = Integer.parseInt(bufReader.readLine()); 
            int edgesCount = Integer.parseInt(bufReader.readLine());
            graphAdjMatrixWithCapacity = new int[nodesCount][nodesCount]; 
            initGraph(); 
            for (int k = 0; k < edgesCount; k++) {
                String[] strArr = bufReader.readLine().split(" "); 
                int u = Integer.parseInt(strArr[0]);
                int v = Integer.parseInt(strArr[1]);
                int cap = Integer.parseInt(strArr[2]);
                graphAdjMatrixWithCapacity[u][v] = cap; 
            } 
            source = Integer.parseInt(bufReader.readLine()); 
            sink = Integer.parseInt(bufReader.readLine()); 
        } catch (Exception e) { 
            e.printStackTrace(); 
            throw e; 
        } 
    } 

    // Finds nbr of vertex i with residual capacity, else returns -1
    int getNbrWithCapacity(int i, int from) {
        for (int j = from + 1; j < nodesCount; j++) {
            if ((graphAdjMatrixWithCapacity[i][j] > 0) 
                    && (graphAdjMatrixWithCapacity[i][j] < Integer.MAX_VALUE)) { 
                return j; 
            } 
        } 
        return -1; 
    } 

    // Finds Augmenting Path in the graph and returns min flow in that path, or
    // returns -1 , 
    // if no augmenting path remains 
    int findAugmentingPathUsingDFS(int i, int currBottleneckFLow,
            ArrayList<Integer> augmentingPath, ArrayList<Integer> markedNodes) { 
        int currMinFlow = 0;
        int nbrVertex = 0;
        int prevBottleneckFLow = currBottleneckFLow; 
        while (true) { 
            nbrVertex = getNbrWithCapacity(i, nbrVertex); 
            if (nbrVertex == -1) { 
                return -1; 
            } 
            if (markedNodes.contains(nbrVertex)) { 
                continue; 
            } 
            if (currBottleneckFLow > graphAdjMatrixWithCapacity[i][nbrVertex]) { 
                currBottleneckFLow = graphAdjMatrixWithCapacity[i][nbrVertex]; 
            } 
            augmentingPath.add(nbrVertex); 

            if (nbrVertex == sink) { 
                return currBottleneckFLow; 
            } 
            if ((currMinFlow = findAugmentingPathUsingDFS(nbrVertex,
                    currBottleneckFLow, augmentingPath, markedNodes)) != -1) { 
                return currMinFlow; 
            } 
            augmentingPath.remove(augmentingPath.size() - 1);
            markedNodes.remove(nbrVertex); 
            currBottleneckFLow = prevBottleneckFLow; 
        } 
    } 

    // Modifies Residual Graph, subtracting flow flow from forward vertex, and
    // adding flow flow to back vertex 
    void modifyResidualGraph(int flow, ArrayList<Integer> augmentingPath) {
        int i = source; 
        for (int j : augmentingPath) {
            graphAdjMatrixWithCapacity[i][j] = graphAdjMatrixWithCapacity[i][j] 
                    - flow; 
            graphAdjMatrixWithCapacity[j][i] = graphAdjMatrixWithCapacity[j][i] 
                    + flow; 
            i = j; 
        } 
    } 

    int fordFulkersonExecute() { 
        ArrayList<Integer> augmentingPath = new ArrayList<Integer>(); 
        int bottleNeckFlow = -1; 
        int totalMaxFlow = 0;
        ArrayList<Integer> markedNodes = new ArrayList<Integer>(); 
        while ((bottleNeckFlow = findAugmentingPathUsingDFS(source,
                Integer.MAX_VALUE, augmentingPath, markedNodes)) != -1) { 
            printAugmentingPath(augmentingPath, bottleNeckFlow); 
            modifyResidualGraph(bottleNeckFlow, augmentingPath); 
            totalMaxFlow += bottleNeckFlow; 
            augmentingPath.clear(); 
        } 
        return totalMaxFlow; 
    } 

    void printAugmentingPath(ArrayList<Integer> augmentingPath,
            int bottleNeckFlow) { 
        StringBuilder strBuild = new StringBuilder(); 
        strBuild.append(source + "-> "); 
        for (int j : augmentingPath) {
            strBuild.append(j + "-> "); 
        } 
        strBuild.setLength(strBuild.length() - 3); 
        System.out.println("Augmenting Path Taken is " + strBuild 
                + "\n Added capacity along this path = " + bottleNeckFlow); 
    } 

    void printGraphAdjMatrix(int[][] graph) { 
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] == Integer.MAX_VALUE) { 
                    System.out.print("0 \t"); 
                } else { 
                    System.out.print(graph[i][j] + " \t"); 
                } 
            } 
            System.out.println(); 
        } 
        System.out.println(); 
    } 

    public static void main(String[] args) {
        BufferedReader bufReader = null; 
        if (args.length > 0) {
            // Unit Test Mode 
            bufReader = new BufferedReader( 
                    new StringReader( 
                            "2\n4\n5\n0 1 10\n0 2 10\n1 2 10\n1 3 20\n2 3 10\n0\n3\n4\n5\n0 1 10\n0 2 10\n1 2 10\n1 3 20\n2 3 10\n0\n3\n")); 
        } else { 
            File file = new File("C:\\graphAlgos\\ff1.txt"); 
            try { 
                bufReader = new BufferedReader(new FileReader(file));
            } catch (Exception e) { 
                e.printStackTrace(); 
                return; 
            } 
        } 
        FordFulkerson ff = new FordFulkerson(); 
        try { 
            int totalGraphs = ff.readTotalGraphCount(bufReader);
            for (int i = 0; i < totalGraphs; i++) {
                System.out.println("************** Start Graph " + (i + 1) 
                        + "******************************");
                ff.readNextGraph(bufReader); 
                System.out.println("Total Max Flow = " 
                        + ff.fordFulkersonExecute()); 
                System.out.println("Residual Graph Is "); 
                ff.printGraphAdjMatrix(ff.graphAdjMatrixWithCapacity); 
                System.out.println("************** End Graph " + (i + 1) 
                        + "******************************");
            } 
        } catch (Exception e) { 
            System.err.println("Exiting : " + e); 
        } finally { 
            try { 
                bufReader.close(); 
            } catch (Exception f) { 

            } 
        } 
    } 
} 