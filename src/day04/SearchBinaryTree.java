package day04;

public class SearchBinaryTree {
    //根节点
    private TreeNode root;

    public SearchBinaryTree() {
    }

    public class TreeNode {
        //索引
        private int index;
        //数据
        private int data;
        //左节点
        private TreeNode liftNode;
        //右节点
        private TreeNode rightNode;
        //父节点
        private TreeNode parentNode;

        //构造函数
        public TreeNode(int index, int data) {
            this.data = data;
            this.index = index;
            this.liftNode = null;
            this.parentNode = null;
            this.rightNode = null;
        }

        public TreeNode getParentNode() {
            return parentNode;
        }

        public void setParentNode(TreeNode parentNode) {
            this.parentNode = parentNode;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public TreeNode getLiftNode() {
            return liftNode;
        }

        public void setLiftNode(TreeNode liftNode) {
            this.liftNode = liftNode;
        }

        public TreeNode getRightNode() {
            return rightNode;
        }

        public void setRightNode(TreeNode rightNode) {
            this.rightNode = rightNode;
        }
    }


    /**
     * 创建查找二叉树
     * 添加节点
     */
    public TreeNode put(int data) {
        TreeNode node = null;
        TreeNode parent = null;
        //判断是否存在根节点 不存在直接给根节点赋值，并返回结果
        if (root == null) {
            root = new TreeNode(0, data);
            return root;
        }

        //将根节点赋值给node
        node = root;
        while (node != null) {

            //存储将要添加的节点的父节点
            //将节点赋值给父节点，当最后一次赋值后，
            // node的子树赋值给node，值为null
            parent = node;
            //如果增加的数大于根节点，则添加到右树
            if (data > node.data) {
                //将node的右树赋值给node  继续循环
                node = node.rightNode;
            }
            //如果增加的数小于根节点的值，则添加到左树
            else if (data < node.data) {
                //将node的左树赋值给node，继续循环
                node = node.liftNode;
            } else {
                //如果值相同，则直接返回
                return node;
            }
        }
        //表示将节点添加到对应位置
        node = new TreeNode(0, data);
        //如果添加的节点比当前节点的父节点小
        //将此节点赋值给父节点的左节点
        if (data < parent.data) {
            parent.liftNode = node;
        } else {
            //否则将增加的节点赋值给父节点的右节点
            parent.rightNode = node;
        }
        //将父节点赋值给子节点的的parentNode
        node.parentNode = parent;
        return node;
    }

    /**
     * 删除树的节点
     * <p>
     * 根据输入的key值删除树中对应节点
     * <p>
     * 删除分为四种情况
     * 1.无子节点
     * 直接删除节点，使父节点的liftChild，或者rightChild为null
     * 2.有左节点无右节点
     * 子节点接替父节点的位置
     * 3.有右节点无左节点
     * 4.既有左节点也有右节点
     * 这种情况需要找到被删除节点的后继节点
     * 后继节点接替被删除的节点
     * <p>
     * 后继节点就是比被删除节点大的数据中最小的节点
     * 添加此节点到被删除位置不会改变树的结构
     * <p>
     * 找后继节点分以下情况
     * 1.如果节点有右子树，则该节点的后继节点就是从右子树出发，然后转到
     * 右子树的左子树，一直到左子树的左子树为空
     * <p>
     * 2.如果节点没有右子树，则向上寻找父节点，直到父节点的的左子树等于当前节点
     * 该父节点就是后继节点
     */
    public void deleteNode(int key) throws Exception {
        //查询节点
        TreeNode node = SearchNode(key);
        if (node == null) {
            throw new Exception("该节点无法找到");
        } else {
            //删除该节点
            delete(node);
        }
    }

    private TreeNode SearchNode(int key) {
        TreeNode node = root;
        if (node == null) {
            return null;
        } else {
            //判断是否为空或者与被查询值相同则跳出并返回
            while (node != null && key != node.data) {
                if (key < node.data) {
                    node = node.liftNode;
                } else {
                    node = node.rightNode;
                }
            }
        }
        return node;
    }

    //删除方法
    private void delete(TreeNode node) throws Exception {
        if (node == null) {
            throw new Exception("该子节点为空");
        } else {

            TreeNode parent = node.parentNode;
            //第一种：无子节点

            if (node.liftNode == null && node.rightNode == null) {
                //直接删除节点
                //判断被删除节点是父节点的左节点还是右节点
                if (parent.liftNode == node) {
                    parent.liftNode = null;
                } else {
                    parent.rightNode = null;
                }
                //跳出方法
                return;
            }

            //第二种：有左子节点无右子节点
            else if (node.liftNode != null && node.rightNode == null) {
                //判断被删除节点是父节点的左节点还是右节点
                if (parent.liftNode == node) {
                    parent.liftNode = node.liftNode;
                } else {
                    parent.rightNode = node.liftNode;
                }
                //跳出方法
                return;
            }

            //第三种：有右子节点无左子节点
            else if (node.rightNode != null && node.liftNode == null) {
                //判断被删除节点是父节点的左节点还是右节点
                if (parent.liftNode == node) {
                    parent.liftNode = node.rightNode;
                } else {
                    parent.rightNode = node.rightNode;
                }
                //跳出方法
                return;
            }
            //左右节点都有
            else {
                //获取后继节点
                TreeNode next = getNextNode(node);
                //删除后继节点
                delete(next);
                //后继节点替代被删除节点
                node.data = next.data;
            }
        }
    }

    /**
     * 获取某节点的后继节点
     * <p>
     * 1.如果节点有右子树，则该节点的后继节点就是从右子树出发，然后转到
     * 右子树的左子树，一直到左子树的左子树为空
     * <p>
     * 2.如果节点没有右子树，则向上寻找父节点，直到父节点的的左子树等于当前节点
     * 该父节点就是后继节点
     *
     * @param node
     * @return
     */
    private TreeNode getNextNode(TreeNode node) {
        if (node == null) {
            return null;
        } else {

            //第一种情况
            if (node.rightNode != null) {
                //找某节点的最小关键字节点
                return SearchMinTreeNode(node);
            }
            // 第二种情况
            else {
                TreeNode parent = node.parentNode;
                /**
                 *
                 *如果节点没有右子树，则向上寻找父节点，直到父节点的的左子树等于当前节点
                 *    该父节点就是后继节点
                 */
                while (parent != null && parent.liftNode == node) {
                        node = parent;
                        parent = parent.parentNode;
                }
                return node;
            }
        }
    }

    /**
     * 查找某节点的最小关键字节点
     *
     * @param node
     */
    private TreeNode SearchMinTreeNode(TreeNode node) {
        if (node == null) {
            return null;
        } else {
            //循环查找左树
            while (node.liftNode != null) {
                node = node.liftNode;
            }
            return node;
        }
    }


    /**
     * 中序遍历树  左根右
     *
     * @param
     */
    public void midOrder(TreeNode node) {
        if (node == null) {
            return;
        } else {
            midOrder(node.liftNode);
            System.out.println(node.data);
            midOrder(node.rightNode);
        }
    }

    public static void main(String[] args) {
        SearchBinaryTree searchBinaryTree = new SearchBinaryTree();
        int[] data = new int[]{44, 11, 23, 54, 24, 65, 23, 43, 5};
        for (int i : data) {
            searchBinaryTree.put(i);
        }
        //中序遍历查询树
        searchBinaryTree.midOrder(searchBinaryTree.root);
        try {
            searchBinaryTree.deleteNode(11);
            System.out.println("================");
            searchBinaryTree.midOrder(searchBinaryTree.root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

