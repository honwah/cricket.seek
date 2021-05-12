package cricket.seek;

import cricket.seek.GraphSeries.AdjacencyMatrixGraph;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for GraphSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({GraphSeriesTest.AdjacencyMatrixGraphTest.class})
public class GraphSeriesTest {

    private static final GraphSeries gs = new GraphSeries();


    public static class AdjacencyMatrixGraphTest {

        @Ignore("已验证测试")
        @Test
        public void test() {
            String[] vertices = {"A", "B", "C", "D", "E"};
            Edge[] edges = {new Edge(0, 1, 10), new Edge(1, 0, 7),
                    new Edge(1, 2, 7), new Edge(1, 3, 6),
                    new Edge(2, 3, 8), new Edge(2, 4, 3),
                    new Edge(3, 1, 6), new Edge(3, 2, 8),
                    new Edge(4, 2, 3), new Edge(4, 3, 9)};
            AdjacencyMatrixGraph<String> amg = gs.new AdjacencyMatrixGraph<>(vertices, edges, GraphSeries.GraphType.DIRECTED);
            System.out.println("顶点个数："+amg.getNumOfVertices());
            System.out.println("边个数："+amg.getNumOfEdges());
            System.out.println(amg);
            System.out.println("插入F");
            amg.insertVertex("F");
            System.out.println(amg);
            System.out.println("插入G");
            amg.insertVertex("G");
            System.out.println(amg);
            System.out.println("插入边{5,1,50}，{5,6,100}，{6,0,200}");
            amg.insertEdge(new Edge(5, 1, 50));
            amg.insertEdge(new Edge(5, 6, 100));
            amg.insertEdge(new Edge(6, 0, 200));
            System.out.println("边个数："+amg.getNumOfEdges());
            System.out.println(amg);
            System.out.println("删除G");
            amg.delVertex("G");
            System.out.println("边个数："+amg.getNumOfEdges());
            System.out.println(amg);
        }

        @Ignore("已验证测试")
        @Test
        public void testDFS() {
            String[] vertices = {"A", "B", "C", "D", "E","F","G","H"};
            Edge[] edges = {new Edge(0, 1, 10), new Edge(0, 2, 7),
                    new Edge(0, 5, 7), new Edge(2, 3, 6),
                    new Edge(1, 5, 8), new Edge(2, 1, 3),
                    new Edge(5, 7, 6), new Edge(7, 6, 8),
                    new Edge(6, 1, 3), new Edge(6, 2, 9),
                    new Edge(6, 4, 9)
            };
            AdjacencyMatrixGraph<String> amg = gs.new AdjacencyMatrixGraph<>(vertices, edges, GraphSeries.GraphType.DIRECTED);
            System.out.println("顶点个数："+amg.getNumOfVertices());
            System.out.println("边个数："+amg.getNumOfEdges());
            System.out.println(amg);
            amg.dfsTraverse("A");
        }

        @Ignore("已验证测试")
        @Test
        public void testBFS() {
            String[] vertices = {"A", "B", "C", "D", "E","F","G","H"};
            Edge[] edges = {new Edge(0, 1, 10), new Edge(0, 2, 7),
                    new Edge(0, 5, 7), new Edge(2, 3, 6),
                    new Edge(1, 5, 8), new Edge(2, 1, 3),
                    new Edge(5, 7, 6), new Edge(7, 6, 8),
                    new Edge(6, 1, 3), new Edge(6, 2, 9),
                    new Edge(6, 4, 9)
            };
            AdjacencyMatrixGraph<String> amg = gs.new AdjacencyMatrixGraph<>(vertices, edges, GraphSeries.GraphType.DIRECTED);
            System.out.println("顶点个数："+amg.getNumOfVertices());
            System.out.println("边个数："+amg.getNumOfEdges());
            System.out.println(amg);
            amg.bfsTraverse("A");
        }

        @Ignore("已验证测试")
        @Test
        public void testdijkstra() {
            String[] vertices = {"V1", "V2", "V3", "V4", "V5","V6"};
            Edge[] edges = {new Edge(0, 2, 10), new Edge(0, 4, 30),
                    new Edge(0, 5, 100), new Edge(1, 2, 5),
                    new Edge(2, 3, 50), new Edge(3, 5, 10),
                    new Edge(4, 3, 20), new Edge(4, 5, 60)
            };
            AdjacencyMatrixGraph<String> amg = gs.new AdjacencyMatrixGraph<>(vertices, edges, GraphSeries.GraphType.DIRECTED);
            System.out.println("顶点个数："+amg.getNumOfVertices());
            System.out.println("边个数："+amg.getNumOfEdges());
            System.out.println(amg);
            amg.dijkstra("V1");
        }

        @Test
        public void testkruskal() {
            String[] vertices = {"A", "B", "C", "D", "E","F","G"};
            Edge[] edges = {new Edge(0, 1, 12), new Edge(0, 5, 16),
                    new Edge(0, 6, 14), new Edge(1, 2, 10),
                    new Edge(1, 5, 7), new Edge(3, 2, 3),
                    new Edge(2, 4, 5), new Edge(4, 3, 4),
                    new Edge(2, 5, 6),new Edge(5, 4, 2),
                    new Edge(5, 6, 9),new Edge(4, 6, 8)
            };
            AdjacencyMatrixGraph<String> amg = gs.new AdjacencyMatrixGraph<>(vertices, edges, GraphSeries.GraphType.DIRECTED);
            System.out.println("顶点个数："+amg.getNumOfVertices());
            System.out.println("边个数："+amg.getNumOfEdges());
            System.out.println(amg);
            amg.kruskal();
        }
    }
}