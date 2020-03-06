package com.company;

import java.util.*;

public class Main {

    public static List<int[]> getSkyline(int[][] buildings) {
        List<int[]> result = new ArrayList<int[]>();

        if (buildings == null || buildings.length == 0
                || buildings[0].length == 0) {
            return result;
        }

        List<Edge> edges = new ArrayList<Edge>();

        for (int[] building : buildings) {
            Edge startEdge = new Edge(building[0], building[2], true);
            Edge endEdge = new Edge(building[1], building[2], false);

            edges.add(startEdge);
            edges.add(endEdge);
        }

        Collections.sort(edges, new Comparator<Edge>() {
            public int compare(Edge a, Edge b) {
                if (a.x != b.x)
                    return Integer.compare(a.x, b.x);

                if (a.isLeft && b.isLeft) {
                    return Integer.compare(b.height, a.height);
                }

                if (!a.isLeft && !b.isLeft) {
                    return Integer.compare(a.height, b.height);
                }

                return a.isLeft ? -1 : 1;
            }
        });

        PriorityQueue<Integer> heightHeap = new PriorityQueue<Integer>(10, Collections.reverseOrder());

        for (Edge edge : edges) {
            if (edge.isLeft) {
                if (heightHeap.isEmpty() || edge.height > heightHeap.peek()) {
                    result.add(new int[] { edge.x, edge.height });
                }
                heightHeap.add(edge.height);
            } else {
                heightHeap.remove(edge.height);

                if(heightHeap.isEmpty()){
                    result.add(new int[] {edge.x, 0});
                }else if(edge.height > heightHeap.peek()){
                    result.add(new int[]{edge.x, heightHeap.peek()});
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

        int[][] buildings = {{2,9,10},{3,6,15},{5,12,12},{13,16,10}, {15,17,4}}; //entrada de datos en formato
        List<int[]> sd;
        sd = getSkyline(buildings);

        sd.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }
}

