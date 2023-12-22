package graph;

import java.util.*;
import java.util.stream.IntStream;

public class GraphTraversal {
    public static class Vertex {
        private char name;
        private int index;


        public void setName(char name) {
            this.name = name;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public char getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex vertex)) return false;
            return getName() == vertex.getName() && getIndex() == vertex.getIndex();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName(), getIndex());
        }

        @Override
        public String toString() {
            return name + "";
        }
    }
//    {0, 1, 1, 0, 0, 1, 0, 0, 0},
//    {1, 0, 0, 0, 0, 0, 0, 0, 0},
//    {1, 0, 0, 0, 0, 1, 1, 0, 0},
//    {0, 0, 0, 0, 1, 0, 0, 0, 1},
//    {0, 0, 0, 1, 0, 0, 0, 0, 1},
//    {1, 0, 1, 0, 0, 0, 0, 1, 0},
//    {0, 0, 1, 0, 0, 0, 0, 1, 0},
//    {0, 0, 0, 0, 0, 1, 1, 0, 0},
//    {0, 0, 0, 1, 1, 0, 0, 0, 0}

    private static Map<Vertex, List<Vertex>> adjacencyList = new HashMap<>();
    private static Map<Vertex, Integer> visitedVertices = new HashMap<>();

    private static Map<Vertex, String> cycleDetection = new HashMap<>();
    private static Map<Integer, Vertex> vertexMap = new HashMap<>();
    private static List<Vertex> out = new ArrayList<>();

    private static  int edgeCount = 0;

    private static int counter = 0;

    static void prepareForTraversal(int[][] arr) {
        var letters = IntStream.rangeClosed('A', 'Z')
                .mapToObj(c -> (char) c)
                .iterator();
        for (int i = 0; i < arr.length; i++) {
            var data = letters.next();
            Vertex vertex = new Vertex();
            vertex.setName(data);
            vertex.setIndex(i);
            adjacencyList.put(vertex, new ArrayList<>());
            vertexMap.put(i, vertex);
        }
        for (int i = 0; i < arr.length; i++) {
            var vertex = vertexMap.get(i);
            var adjacents = adjacencyList.get(vertex);
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 1) {
                    adjacents.add(vertexMap.get(j));
                }
            }
        }
    }

    public static Vertex nextUnvisitedAdjacent(Vertex v) {
        List<Vertex> listOfAdjacent = adjacencyList.get(v);
        Iterator<Vertex> it = listOfAdjacent.iterator();
        //this loop will visit each element matched with v in the adjacency list
        //ONLY ONCE, since whenever a list element is encountered,
//it is removed after processing
        while (it.hasNext()) {
            Vertex u = it.next();
            if (!visitedVertices.containsKey(u)) {
                return u;
            }
        }
//unvisited adjacent vertex not found
        return null; //returning null
    }


    public static boolean dfs(int[][] arr) {
//        prepareForTraversal(arr);
        var itr = vertexMap.values().iterator();
        while (itr.hasNext()) {
            var vertex = itr.next();
            if (visitedVertices.containsKey(vertex))
                continue;
            counter++;
            Stack<Vertex> stack = new Stack<>();
            mark(vertex, "visiting");
            stack.push(vertex);
            while (!stack.isEmpty()) {
                var v = stack.peek();
                var nextUnvisitedAdjacent = nextUnvisitedAdjacent(v);
                if (nextUnvisitedAdjacent != null) {
                    mark(nextUnvisitedAdjacent, "visiting");
                    stack.push(nextUnvisitedAdjacent);
                } else {
                    var x = stack.pop();
                    var cycleCheck = countOfvisitedAdjacents(x);
                    if(cycleCheck[1] == 2){
                        return true;
                    }
                    mark(x, "visited");
                }
            }
        }
        System.out.println(out);
        System.out.println(visitedVertices);
        return false;
    }

    private static int[] countOfvisitedAdjacents(Vertex v) {
        var cnt = 0;
        List<Vertex> listOfAdjacent = adjacencyList.get(v);
        Iterator<Vertex> it = listOfAdjacent.iterator();
        //this loop will visit each element matched with v in the adjacency list
        //ONLY ONCE, since whenever a list element is encountered,
//it is removed after processing
        var visited = 0;
        var visiting = 0;
        while (it.hasNext()) {
            Vertex u = it.next();
            if (cycleDetection.containsKey(u)) {
                if(cycleDetection.get(u).equals("visited")){
                    visited ++;
                } else if (cycleDetection.get(u).equals("visiting")) {
                    visiting++;
                }
            }
        }
//unvisited adjacent vertex not found
        return new int[]{visited, visiting}; //returning null

    }

    public static void mark(Vertex vertex, String value) {
        visitedVertices.put(vertex, counter);
        cycleDetection.put(vertex, value);
        out.add(vertex);
    }

    public static void bfs(int[][] arr) {
        var itr = vertexMap.values().iterator();
        while (itr.hasNext()) {
            var vertex = itr.next();
            if (visitedVertices.containsKey(vertex))
                continue;
            counter++;
            Queue<Vertex> queue = new LinkedList<>();
            mark(vertex, "visiting");
            queue.add(vertex);
            while (!queue.isEmpty()) {
                var v = queue.remove();
                while (true) {
                    var nextUnvisitedAdjacent = nextUnvisitedAdjacent(v);
                    if (nextUnvisitedAdjacent == null)
                        break;
                    mark(nextUnvisitedAdjacent, "visiting");
                    queue.add(nextUnvisitedAdjacent);
                }
            }
        }
        System.out.println(out);
        System.out.println(visitedVertices);

    }


    public static void main(String[] args) {
        int[][] twoDArray = {
                {0, 1, 1, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0}
        };// with cycle
        int[][] twoDArray2 = {
                {0, 1, 1, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0}
        };// without cycle

        prepareForTraversal(twoDArray);
        System.out.println(dfs(twoDArray));
//        prepareForTraversal(twoDArray2);
//        System.out.println(dfs(twoDArray2));

        //bfs(twoDArray);
    }
}
//    Algorithm: Depth First Search (DFS)
//    Input: A simple connected undirected graph G = (V,E)
//    Output: G, with all vertices marked as visited.
//            while there are more vertices do //never reads a vertex twice
//    s  next vertex
// if s is unvisited then
//    mark s as visited
//    initialize a stack S
//S.push(s)
//            while S   do
//    v  S.peek()
//            if some vertex adjacent to v not yet visited then
//    w  next unvisited vertex adjacent to v
//    mark w
//    push w onto S
//else
//        S.pop()


