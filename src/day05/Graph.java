package day05;

import java.util.LinkedList;

//图论
public class Graph {
    private int vertexSize;//顶点数量
    private int[] vertexs;//顶点数组

    //二维数组表示邻接矩阵
    private int[][] matrix;
    private static final int MAX_WEIGHT = 1000;
    private boolean[] isVisited;

    public Graph(int vertextSize) {
        this.vertexSize = vertextSize;
        matrix = new int[vertextSize][vertextSize];
        vertexs = new int[vertextSize];
        for (int i = 0; i < vertextSize; i++) {
            vertexs[i] = i;
        }
        isVisited = new boolean[vertextSize];
    }


    /**
     * 获取某个顶点的出度
     * <p>
     * 一个顶点的出边条数
     *
     * @return
     */
    public int getOutDegree(int index) {
        int degree = 0;
        for (int j = 0; j < matrix[index].length; j++) {
            int weight = matrix[index][j];
            if (weight != 0 && weight != MAX_WEIGHT) {
                degree++;
            }
        }
        return degree;
    }


    /**
     * 入度
     * @return
     */

    /**
     * 获取某个顶点的第一个邻接点
     */
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexSize; j++) {
            if (matrix[index][j] > 0 && matrix[index][j] < MAX_WEIGHT) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接点的下标来取得下一个邻接点
     *
     * @param v     表示要找的顶点
     * @param index 表示该顶点相对于哪个邻接点去获取下一个邻接点
     */
    public int getNextNeighbor(int v, int index) {
        for (int j = index + 1; j < vertexSize; j++) {
            if (matrix[v][j] > 0 && matrix[v][j] < MAX_WEIGHT) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 对外公开的深度优先遍历
     */
    public void depthFirstSearch() {
        //{null,null,null,null,null,null}
        isVisited = new boolean[vertexSize];
        for (int i = 0; i < vertexSize; i++) {
            if (!isVisited[i]) {
                System.out.println("访问到了：" + i + "顶点");
                depthFirstSearch(i);
            }
        }
        isVisited = new boolean[vertexSize];
    }

    /**
     * 图的深度优先遍历算法
     * <p>
     * 类似树的前序遍历
     */
    private void depthFirstSearch(int i) {
        isVisited[i] = true;
        int w = getFirstNeighbor(i);//
        while (w != -1) {
            if (!isVisited[w]) {
                //需要遍历该顶点
                System.out.println("访问到了：" + w + "顶点");
                depthFirstSearch(w);
            }
            w = getNextNeighbor(i, w);//第一个相对于w的邻接点
        }
    }

    /**
     * 图的广度优先搜索算法
     */
    public void broadFirstSearch() {
        isVisited = new boolean[vertexSize];
        for (int i = 0; i < vertexSize; i++) {
            if (!isVisited[i]) {
                broadFirstSearch(i);
            }
        }
    }

    /**
     * 实现广度优先遍历
     *
     * @param i
     */
    private void broadFirstSearch(int i) {
        int u, w;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        System.out.println("访问到：" + i + "顶点");
        isVisited[i] = true;
        queue.add(i);//第一次把v0加到队列
        while (!queue.isEmpty()) {
            u = (Integer) (queue.removeFirst()).intValue();
            w = getFirstNeighbor(u);
            while (w != -1) {
                if (!isVisited[w]) {
                    System.out.println("访问到了：" + w + "顶点");
                    isVisited[w] = true;
                    queue.add(w);
                }
                w = getNextNeighbor(u, w);
            }
        }
    }

    /**
     * prim 普里姆算法
     */
    public void prim() {
        //待定最小权值数组
        int[] lowcost = new int[vertexSize];//最小待定顶点权值的数组,为0表示已经获取最小权值
        int[] adjvex = new int[vertexSize];//放顶点权值的下标
        int min, minId, sum = 0;

        //构建待定最小权值数组
        //下标需要从1开始，因为矩阵第一列和第一行值相同，行表示v0相连的节点，列表示和v0相连的节点

        for (int i = 1; i < vertexSize; i++) {
            //待定最小权值数组为 》》》》》V0到其他边的邻接值，取第一行
            lowcost[i] = matrix[0][i];
        }

        for (int i = 1; i < vertexSize; i++) {
            /**
             * 先让最小值等于最大值，去和待定数组中的值比较，小于最大值并且大于零的
             * 让将值赋值给最小值，循环遍历寻找最小值
             */
            min = MAX_WEIGHT;
            //最小权值的下标初始化
            minId = 0;

            //遍历当前待定最小权值数组中最小的权值
            for (int j = 1; j < vertexSize; j++) {
                //如果当前边小于最小值且大于0
                if (lowcost[j] < min && lowcost[j] > 0) {
                    //让最小值等于当前循环出的最小值
                    min = lowcost[j];
                    minId = j;
                }
            }


            //打印当前确定的最小权值
            System.out.println("顶点：" + adjvex[minId] + "权值：" + min);

            //总权值
            sum += min;

            //找到最小待定顶点权值的数组中权值最小的，将值赋值为0，表示已经找到最小权值
            lowcost[minId] = 0;

            //比较矩阵中最小权值（此次循环中）的行中的值和待定数组中的值
            for (int j = 1; j < vertexSize; j++) {
                //如果最小待定数组中的边不为0，
                // 且矩阵中最小权值（此次循环中）的行中的值小于待定数组中的值
                if (lowcost[j] != 0 && matrix[minId][j] < lowcost[j]) {
                    //待定数组中值等于矩阵中最小权值（此次循环中）的行中的值
                    lowcost[j] = matrix[minId][j];
                    //顶点权值数组中的值改为
                    adjvex[j] = minId;
                }
            }

        }
        System.out.println("最小生成树权值和:" + sum);
    }


    /**
     * 获取两个顶点之间的权值
     *
     * @return
     */
    public int getWeight(int v1, int v2) {
        int weight = matrix[v1][v2];
        return weight == 0 ? 0 : (weight == MAX_WEIGHT ? -1 : weight);
    }


    public int[] getVertexs() {
        return vertexs;
    }

    public void setVertexs(int[] vertexs) {
        this.vertexs = vertexs;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(9);

        int[] a1 = new int[]{0, 10, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 11, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT};
        int[] a2 = new int[]{10, 0, 18, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 16, MAX_WEIGHT, 12};
        int[] a3 = new int[]{MAX_WEIGHT, MAX_WEIGHT, 0, 22, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 8};
        int[] a4 = new int[]{MAX_WEIGHT, MAX_WEIGHT, 22, 0, 20, MAX_WEIGHT, MAX_WEIGHT, 16, 21};
        int[] a5 = new int[]{MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 20, 0, 26, MAX_WEIGHT, 7, MAX_WEIGHT};
        int[] a6 = new int[]{11, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 26, 0, 17, MAX_WEIGHT, MAX_WEIGHT};
        int[] a7 = new int[]{MAX_WEIGHT, 16, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 17, 0, 19, MAX_WEIGHT};
        int[] a8 = new int[]{MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 16, 7, MAX_WEIGHT, 19, 0, MAX_WEIGHT};
        int[] a9 = new int[]{MAX_WEIGHT, 12, 8, 21, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 0};

        graph.matrix[0] = a1;
        graph.matrix[1] = a2;
        graph.matrix[2] = a3;
        graph.matrix[3] = a4;
        graph.matrix[4] = a5;
        graph.matrix[5] = a6;
        graph.matrix[6] = a7;
        graph.matrix[7] = a8;
        graph.matrix[8] = a9;

//		int degree = graph.getOutDegree(4);
//		System.out.println("vo的出度:"+degree);
//		System.out.println("权值："+graph.getWeight(2,3));
//		graph.depthFirstSearch();
//		graph.broadFirstSearch();
        graph.prim();
    }
}
