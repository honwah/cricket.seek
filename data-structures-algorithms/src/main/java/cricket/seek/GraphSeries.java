package cricket.seek;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 关于图的求解
 */
public class GraphSeries {
    @Override
    public String toString() {
        return "整理关于图的求解";
    }


    //图的类型，有向，无向
    public enum GraphType {
        DIRECTED, UNDIRECTED
    }

    //图的邻接矩阵表示
    class AdjacencyMatrixGraph<T extends Comparable<T>> {
        final int MAX_WEIGHT = Integer.MAX_VALUE;//最大权重，代表边不存在
        ArrayList<T> vertices;//存储顶点集合
        int[][] matrix;//邻接矩阵
        int numOfEdges;//边数量
        GraphType graphType;

        public AdjacencyMatrixGraph(int vertexNum, GraphType graphType) {//顶点个数
            this.graphType = graphType;
            vertices = new ArrayList<>(vertexNum);
            matrix = new int[vertexNum][vertexNum];
            numOfEdges = 0;
            //初始化邻接矩阵
            for (int i = 0; i < vertexNum; i++) {
                for (int j = 0; j < vertexNum; j++) {
                    matrix[i][j] = i == j ? 0 : MAX_WEIGHT;
                }
            }
        }

        public AdjacencyMatrixGraph(T[] vertices, Edge[] edges, GraphType graphType) {
            this(vertices.length, graphType);
            for (int i = 0; i < vertices.length; i++) {
                this.vertices.add(vertices[i]);
            }
            for (int i = 0; i < edges.length; i++) {
                insertEdge(edges[i]);
            }
        }

        //返回节点数
        public int getNumOfVertices() {
            return vertices.size();
        }

        //返回边数
        public int getNumOfEdges() {
            return numOfEdges;
        }

        //添加顶点，返回顶点索引,存在时不添加
        public int insertVertex(T vertex) {
            if (vertices.contains(vertex)) {
                return -1;
            }
            vertices.add(vertex);
            if (getNumOfVertices() > matrix.length) {//新增顶点导致邻接矩阵不足
                int[][] temp = matrix;
                matrix = new int[temp.length * 2][temp.length * 2];//扩充两倍
                for (int i = 0; i < temp.length; i++) {
                    for (int j = 0; j < temp.length; j++) {
                        matrix[i][j] = temp[i][j]; //拷贝原值到新矩阵
                    }
                    for (int j = temp.length; j < temp.length * 2; j++) {//新矩阵右上赋值
                        matrix[i][j] = MAX_WEIGHT;
                    }
                }
                for (int i = temp.length; i < temp.length * 2; i++) {//新矩阵下半部分赋值
                    for (int j = 0; j < temp.length * 2; j++) {
                        matrix[i][j] = i == j ? 0 : MAX_WEIGHT;
                    }
                }
            }
            return getNumOfVertices() - 1;
        }

        //删除顶点
        public boolean delVertex(T vertex) {
            int lengthOfMatrix = this.matrix.length;
            int idx = vertices.indexOf(vertex);
            if (idx > -1) {
                vertices.remove(idx);
                for (int i = 0; i < idx; i++) {
                    for (int j = idx + 1; j < lengthOfMatrix; j++) { //元素左移一列
                        if (j - 1 == idx && matrix[i][j - 1] != MAX_WEIGHT && matrix[i][j - 1] != 0) {
                            numOfEdges--;//删除的顶点所在列有边时
                        }
                        matrix[i][j - 1] = matrix[i][j];
                    }
                }
                for (int i = idx + 1; i < lengthOfMatrix; i++) {//元素上移一行
                    for (int j = 0; j < idx; j++) {
                        if (i - 1 == idx && matrix[i - 1][j] != MAX_WEIGHT && matrix[i - 1][j] != 0) {
                            numOfEdges--;//删除的顶点所在行有边时
                        }
                        matrix[i - 1][j] = matrix[i][j];
                    }
                }
                for (int i = idx + 1; i < lengthOfMatrix; i++) {//向左上角移动一格
                    for (int j = idx + 1; j < lengthOfMatrix; j++) {
                        if (i - 1 == idx && j - 1 == idx && matrix[i - 1][j - 1] != MAX_WEIGHT && matrix[i - 1][j - 1] != 0) {
                            numOfEdges--;//删除的顶点所在行有边时
                        }
                        matrix[i - 1][j - 1] = matrix[i][j];
                    }
                }
                return true;
            } else {
                return false;
            }
        }


        //添加边
        public boolean insertEdge(int from, int to, int weight) {
            //边存在时不添加
            if (from >= 0 && from < vertices.size() && to >= 0 && to < vertices.size() && from != to && matrix[from][to] == MAX_WEIGHT) {
                matrix[from][to] = weight;
                if (graphType == GraphType.UNDIRECTED) {
                    matrix[to][from] = weight;
                }
                numOfEdges++;
                return true;
            } else {
                return false;
            }
        }

        //添加边
        public boolean insertEdge(Edge edge) {
            return insertEdge(edge.from, edge.to, edge.weight);
        }

        //删除边
        public boolean delEdge(int from, int to) {
            if (from >= 0 && from < vertices.size() && to >= 0 && to < vertices.size() && from != to) {
                matrix[from][to] = MAX_WEIGHT;
                if (graphType == GraphType.UNDIRECTED) {
                    matrix[to][from] = MAX_WEIGHT;
                }
                numOfEdges--;
                return true;
            } else {
                return false;
            }
        }


        @Override
        public String toString() {//打印矩阵
            StringBuilder sb = new StringBuilder();
            sb.append(this.vertices.toString() + System.getProperty("line.separator"));
            for (int[] edge : matrix) {
                sb.append(Arrays.toString(edge) + System.getProperty("line.separator"));
            }
            return sb.toString().replaceAll(String.valueOf(MAX_WEIGHT), "∞");
        }
    }

}

//带权值的边
class Edge {
    int from; //起点
    int to; //终点
    int weight; //权重

    //无权重
    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
        this.weight = 1;
    }

    //有权重
    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}