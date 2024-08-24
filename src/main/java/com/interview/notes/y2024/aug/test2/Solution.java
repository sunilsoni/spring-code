package com.interview.notes.y2024.aug.test2;

import java.util.*;

public class Solution {
    public static String GraphChallenge(String[] strArr) {
        // Parse input and create graph
        int n = Integer.parseInt(strArr[0]);
        Map<String, Map<String, Integer>> graph = new HashMap<>();
        
        // Build graph
        for (int i = 1; i <= n; i++) {
            graph.put(strArr[i], new HashMap<>());
        }
        for (int i = n + 1; i < strArr.length; i++) {
            String[] edge = strArr[i].split("\\|");
            graph.get(edge[0]).put(edge[1], Integer.parseInt(edge[2]));
            graph.get(edge[1]).put(edge[0], Integer.parseInt(edge[2]));
        }
        
        // Run Dijkstra's algorithm
        String start = strArr[1];
        String end = strArr[n];
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        
        for (String node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }
        distances.put(start, 0);
        pq.offer(new Node(start, 0));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.name.equals(end)) break;
            
            for (Map.Entry<String, Integer> neighbor : graph.get(current.name).entrySet()) {
                int newDist = distances.get(current.name) + neighbor.getValue();
                if (newDist < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), newDist);
                    previous.put(neighbor.getKey(), current.name);
                    pq.offer(new Node(neighbor.getKey(), newDist));
                }
            }
        }
        
        // Reconstruct path
        if (distances.get(end) == Integer.MAX_VALUE) return "-1";
        
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        
        return String.join("-", path);
    }
    
    private static class Node {
        String name;
        int distance;
        
        Node(String name, int distance) {
            this.name = name;
            this.distance = distance;
        }
    }
    
    public static void main(String[] args) {
        // Test cases
        System.out.println(GraphChallenge(new String[] {"4","A","B","C","D", "A|B|2", "C|B|11", "C|D|3", "B|D|2"}));
        System.out.println(GraphChallenge(new String[] {"4", "x","y","z", "w", "x|y|2", "y|z|14", "z|y|31"}));
        // Add more test cases here
    }
}
