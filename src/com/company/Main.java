package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<int[]> buildings = new ArrayList<>();

        buildings = drawMenu();
        //int[][] buildings = {{2,9,10},{3,6,15},{5,12,12},{13,16,10}, {15,17,4}};

        List<int[]> skylineDrawingList;
        skylineDrawingList = getSkyline(buildings);

        skylineDrawingList.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    public static ArrayList<int[]> drawMenu() {
        int i = 0;
        boolean continueAdding = true;
        ArrayList<int[]> buildings = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        do {
            int[] building = new int[3];

            try {
                System.out.println("Edificio: " + i);
                System.out.print("Introduzca Li: ");
                building[0] = scanner.nextInt();    //Li
                System.out.print("Introduzca Ri: ");
                building[1] = scanner.nextInt();    //Ri
                System.out.print("Introduzca Hi: ");
                building[2] = scanner.nextInt();    //Hi

                if(validateBuilding(building)) {
                    buildings.add(building);
                    System.out.println("... Edificio añadido");
                    i++;
                } else {
                    System.out.println("Edificio no añadido, por favor revise que: ");
                    System.out.println("\tLi, Ri, Hi deben ser enteros");
                    System.out.println("\tLi, Ri, Hi deben ser mayores a 0");
                    System.out.println("\tLi < Ri");
                }
            }catch (Exception e) {
                System.out.println("Dato inválido, edificio no añadido");
            }

            scanner.nextLine();
            System.out.print("Agregar otro edificio [s/n]?: ");

            if(scanner.nextLine().toLowerCase().equals("n")) {
                continueAdding = false;
            }
        }while(continueAdding);

        return buildings;
    }

    public static boolean validateBuilding(int[] building) {
        //Validations:
        //  Ri, Hi > 0
        //  Li < Ri

        for(int i = 1; i < 3; i++) {
            if(building[i] <= 0) {  //Ri, Hi > 0
                return false;
            }
        }

        if (building[0] >= building[1]) {
            return false;
        }

        return true;
    }

    public static List<int[]> getSkyline(ArrayList<int[]> buildings) {
        List<int[]> result = new ArrayList<int[]>();

        if (buildings == null || buildings.size() == 0 || buildings.get(0).length == 0) {
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
}

