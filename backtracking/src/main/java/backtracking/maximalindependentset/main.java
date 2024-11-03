package backtracking.maximalindependentset;

import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author PC
 */
public class main {
	
	// khởi tạo biến maxIndependentSet để lưu tập độc lập lớn nhất
	static Set<String> maxIndependentSet = new HashSet<>();
	
	// khởi tạo maxSize để lưu kích thước lớn nhất của tập độc lập
    static int maxSize = 0;

    
    public static void main(String[] args) {
        // Khởi tạo đồ thị đơn giản sử dụng JGraphT
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Thêm các đỉnh vào đồ thị
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (String vertex : vertices) {
            graph.addVertex(vertex);
        }

        // Thêm các cạnh vào đồ thị
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        graph.addEdge("B", "E");
        graph.addEdge("C", "E");
        graph.addEdge("C", "F");
        graph.addEdge("D", "F");
        graph.addEdge("D", "G");
        graph.addEdge("E", "H");
        graph.addEdge("F", "J");
        graph.addEdge("H", "I");
        graph.addEdge("I", "J");

        // Gọi hàm quay lui để tìm tập hợp độc lập lớn nhất
        findMaxIndependentSet(graph, new HashSet<>(), 0, vertices);

        // In kết quả
        System.out.println("Tập hợp độc lập lớn nhất: " + maxIndependentSet);
        System.out.println("Kích thước lớn nhất: " + maxSize);
    }

    // Hàm quay lui để tìm tập hợp độc lập lớn nhất
    private static void findMaxIndependentSet(Graph<String, DefaultEdge> graph, Set<String> currentSet, int currentIndex, String[] vertices) {
        // Nếu đã xét hết các đỉnh
        if (currentIndex >= vertices.length) {
            // Nếu kích thước của tập hiện tại lớn hơn kích thước lớn nhất
            if (currentSet.size() > maxSize) {
                maxSize = currentSet.size();
                maxIndependentSet = new HashSet<>(currentSet);
            }
            return;
        }

        String vertex = vertices[currentIndex];

        // Lựa chọn không chọn đỉnh hiện tại
        findMaxIndependentSet(graph, currentSet, currentIndex + 1, vertices);

        // Kiểm tra nếu có thể chọn đỉnh hiện tại (không kề với bất kỳ đỉnh nào trong currentSet)
        boolean canAdd = true;
        for (DefaultEdge edge : graph.edgesOf(vertex)) {
            String neighbor = graph.getEdgeSource(edge).equals(vertex) ? graph.getEdgeTarget(edge) : graph.getEdgeSource(edge);
            if (currentSet.contains(neighbor)) {
                canAdd = false;
                break;
            }
        }

        // Nếu có thể chọn, thêm đỉnh vào currentSet
        if (canAdd) {
            currentSet.add(vertex);
            findMaxIndependentSet(graph, currentSet, currentIndex + 1, vertices);
            currentSet.remove(vertex); // Quay lui
        }
    }
}