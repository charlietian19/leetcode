import java.util.AbstractCollection.*;
import java.util.AbstractMap.*;
import java.util.Comparator.*;

public class CheapestFlightsSolution {
    private class DistComparator implements Comparator<int[]>{
        @Override
        public int compare(int[] dist1, int[] dist2)
        {
            // Assume neither string is null. Real code should
            // probably be more robust
            // You could also just return x.length() - y.length(),
            // which would be more efficient.
            if (dist1[1] < dist2[1])
            {
                return -1;
            }
            else if (dist1[1] > dist2[1])
            {
                return 1;
            }
            return 0;
        }
    }
    
    
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        
        HashMap<Integer, ArrayList<int[]> > adjacencyMtx = new HashMap<> ();
        
        for (int[] flight: flights){
            if (!adjacencyMtx.containsKey(flight[0])){
                ArrayList<int[]> neighbors = new ArrayList<> ();
                neighbors.add(new int[]{flight[1], flight[2]});//each neighbor has first elem the node, and second elem the cost from that node
                adjacencyMtx.put(flight[0], neighbors);
            }
            else{
                adjacencyMtx.get(flight[0]).add(new int[]{flight[1], flight[2]});
            }
        }
        
        Comparator <int[]> distComparator = new DistComparator(); 
        PriorityQueue<int[]> searchTree = new PriorityQueue<> (10, distComparator);
        
        searchTree.add(new int[]{src, 0, 0}); //first element in the searchTree node is identity, second is cost to src, third is STOPS USED (kcount)
        
       
        
        while (!searchTree.isEmpty()){
            int[] shortestPathNode = searchTree.poll();
            if (shortestPathNode[0] == dst){
                return shortestPathNode[1]; //if we reached the destination and are considering it as min for the first time, return its cost to goal
            }
            
            if (!adjacencyMtx.containsKey(shortestPathNode[0])){
                return -1;
            }
            
            for (int[] neighbors: adjacencyMtx.get(shortestPathNode[0])){
                int[] node = new int[3];
                node[0] = neighbors[0];
                node[1] = shortestPathNode[1] + neighbors[1];
                node[2] = shortestPathNode[2] + 1;
                if (node[2] <= K){ //if the amount of stops used so far is less than or equal to the stops allowed, keep adding in hopes that we reach a goal
                    searchTree.add(node);
                }
                else if (node[2] == K+1) {
                    if (node[0] == dst){
                        searchTree.add(node);
                    }
                }
            }
            
            
        }
        
        return -1;
        
    }
}


