package cricket.seek;


import java.util.*;

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

    //是否已访问顶点
    private boolean[] isVisited;

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

        //获取图中的边
        private Edge[] getEdges() {
            int index = 0;
            Edge[] edges = new Edge[numOfEdges];
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (i != j && matrix[i][j] != MAX_WEIGHT) {
                        edges[index++] = new Edge(i, j, matrix[i][j]);
                    }
                }
            }
            return edges;
        }

        //对边按照权值大小进行排序(由小到大)
        private void sortEdges(Edge[] edges, int elen) {
            for (int i = 0; i < elen; i++) {
                for (int j = i + 1; j < elen; j++) {
                    if (edges[i].weight > edges[j].weight) {
                        // 交换"边i"和"边j"
                        Edge tmp = edges[i];
                        edges[i] = edges[j];
                        edges[j] = tmp;
                    }
                }
            }
        }

        //获取节点a属于哪个集合，这里获取a根节点编号，根节点编号代表所属集合
        public int find(int[] id, int element) {
            while (element != id[element]) {
                element = id[element];
            }
            return element;
        }

        //合并a顶点和b顶点所在集合，判断顶点a和顶点b的根节点大小所在集合大小，将节点少的集合的节点添加到节点多的集合上
        public void union(int[] id, int[] weight, int firstElement, int secondElement) {
            int firstRoot = find(id, firstElement);
            int secondRoot = find(id, secondElement);
            //如果已经属于同一个集合了，就不用再合并了。
            if (firstRoot == secondRoot) {
                return;
            }

            if (weight[firstRoot] > weight[secondRoot]) {
                id[secondRoot] = firstRoot;
                weight[firstRoot] += weight[secondRoot];
            } else {
                id[firstRoot] = secondRoot;
                weight[secondRoot] += weight[firstRoot];
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

        //深度优先遍历
        public void dfsTraverse(T vertex)//要遍历的初始顶点
        {
            isVisited = new boolean[vertices.size()];//初始化顶点是否遍历
            if (vertices.size() > 0) {
                Stack<T> stack = new Stack<T>();
                stack.push(vertex);
                while (!stack.empty()) {
                    T cur = stack.peek();
                    int idx = vertices.indexOf(cur);
                    if (isVisited[idx] == false) {
                        System.out.print(cur);
                        isVisited[idx] = true;
                    }
                    int j = 0;
                    for (; j < vertices.size(); ++j)
                        if (idx != j && matrix[idx][j] != MAX_WEIGHT && isVisited[j] == false) {
                            stack.push(vertices.get(j));
                            break;
                        }
                    //没有未访问过的顶点时
                    if (j == vertices.size())
                        stack.pop();//回到上一个顶点，继续试探
                }
                System.out.print(System.getProperty("line.separator"));
            }
        }

        //广度优先搜索
        public void bfsTraverse(T vertex) { //要遍历的初始顶点
            isVisited = new boolean[vertices.size()];//初始化顶点是否遍历
            if (vertices.size() > 0) {
                Queue<T> queue = new LinkedList<T>();
                queue.offer(vertex);
                while (!queue.isEmpty()) {
                    T cur = queue.poll();
                    int idx = vertices.indexOf(cur);
                    if (isVisited[idx] == false) {
                        System.out.print(cur);
                        isVisited[idx] = true;
                    }
                    for (int j = 0; j < vertices.size(); ++j)
                        if (idx != j && matrix[idx][j] != MAX_WEIGHT && isVisited[j] == false) {
                            T v = vertices.get(j);
                            if (!queue.contains(v)) {
                                queue.offer(v);
                            }
                        }
                }
                System.out.print(System.getProperty("line.separator"));
            }
        }

        // Dijkstra算法实现到某顶点的最短路径
        public void dijkstra(T startpoint) {//起始顶点
            int startindex = vertices.indexOf(startpoint);
            ArrayList<ShortestPath> tArray = new ArrayList<ShortestPath>();//T集合中存找到最短路径的对象
            ArrayList<ShortestPath> sArray = new ArrayList<ShortestPath>();//S集合中存未找到最短路径的对象
            //初始化S列表
            for (int i = 0; i < vertices.size(); i++) {
                ShortestPath item = new ShortestPath();
                item.end = vertices.get(i);
                item.pathArray.add(vertices.get(startindex));
                item.pathArray.add(vertices.get(i));
                item.distance = matrix[startindex][i];
                sArray.add(item);
            }
            while (!sArray.isEmpty()) {
                //S集合中找距离起始顶点最近的顶点
                int minIndex = 0;
                int tempDistance = sArray.get(0).distance;
                for (int i = 0; i < sArray.size(); i++) {
                    if (tempDistance > sArray.get(i).distance) {
                        minIndex = i;
                        tempDistance = sArray.get(i).distance;
                    }
                }
                ShortestPath goal = sArray.remove(minIndex);//找到最短路径
                tArray.add(goal);
                //考虑新找到的最短路径顶点是否会影响S集合其他顶点，通过“边”来松弛起始顶点到其余各个顶点的路程
                int index1 = vertices.indexOf(goal.end);//已找到最短路径的顶点下标
                for (int i = 0; i < sArray.size(); i++) {
                    int index2 = vertices.indexOf(sArray.get(i).end);//未找到最短路径顶点下标
                    if (matrix[index1][index2] == MAX_WEIGHT) {//两顶点没有路径
                        continue;
                    } else {
                        int newdistance = goal.distance + matrix[index1][index2];
                        //新找到最短路径的顶点影响了路径长度，导致顶点比原来路径值小
                        if (newdistance < sArray.get(i).distance) {
                            //更新路径长度
                            sArray.get(i).distance = newdistance;
                            //更新路径
                            sArray.get(i).pathArray = new ArrayList<T>(goal.pathArray);
                            sArray.get(i).pathArray.add(sArray.get(i).end);
                        }
                    }
                }
            }
            //打印最短路径
            for (int i = 0; i < tArray.size(); i++) {
                if (tArray.get(i).distance == MAX_WEIGHT) {
                    System.out.println(startpoint + "-->" + tArray.get(i).end + "的最短路径为: 无" + "   长度为: ∞");
                } else {
                    System.out.print(startpoint + "-->" + tArray.get(i).end + "的最短路径为:");
                    for (int j = 0; j < tArray.get(i).pathArray.size(); j++) {
                        System.out.print(tArray.get(i).pathArray.get(j) + "  ");
                    }
                    System.out.println("长度为:" + tArray.get(i).distance);
                }
            }
        }

        //克鲁斯卡尔（Kruskal)最小生成树
        public void kruskal() {
            ArrayList<Edge> rtlist = new ArrayList<Edge>();
            int[] id = new int[vertices.size()];//表示并查集所有元素
            int[] weight = new int[vertices.size()];//表示集合元素数量
            for (int i = 0; i < vertices.size(); i++) {
                id[i] = i;        //初始时数组内的值与数组的下角标一致。即每个数字都自成立一个集合
                weight[i] = 1;    //初始集合大小1
            }
            //获取"图中所有的边"
            Edge[] edges = getEdges();
            // 将边按照"权"的大小进行排序(从小到大)
            sortEdges(edges, numOfEdges);
            int count = 0;
            for (int i = 0; i < edges.length; i++) {
                int p1 = edges[i].from;      // 获取第i条边的"起点"的下标
                int p2 = edges[i].to;        // 获取第i条边的"终点"的下标
                int ida = find(id, p1);
                int idb = find(id, p2);
                if (ida != idb) {
                    rtlist.add(edges[i]);
                    count++;
                    union(id, weight, p1, p2);
                }
                if (count >= vertices.size() - 1)
                    break;
            }
            // 统计并打印"kruskal最小生成树"的信息
            int length = 0;
            for (int i = 0; i < rtlist.size(); i++)
                length += rtlist.get(i).weight;
            System.out.printf("Kruskal=%d: ", length);
            for (int i = 0; i < rtlist.size(); i++)
                System.out.printf("(%s->%s:%s) ", vertices.get(rtlist.get(i).from), vertices.get(rtlist.get(i).to), rtlist.get(i).weight);
            System.out.printf("\n");
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

        //最短路径
        class ShortestPath {
            T end;//终点
            ArrayList<T> pathArray = new ArrayList<T>();//到终点的顶点集合
            int distance;//路径长度
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
