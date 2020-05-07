package 二叉搜索树_BST;

import 二叉搜索树_BST.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by 2316549044 on 2020/4/7.
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {

    private int size;
    //根节点属性
    private Node<E> roots;

    //将传入的值指定为可比较类型
    private Comparable<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparable<E> comparator) {
        this.comparator = comparator;
    }


    //节点类：代表二分搜索树的节点信息
    private static class Node<E> {

        E element;
        //左子节点
        Node<E> left;
        //右子节点
        Node<E> right;
        //父节点
        Node parent;

        //树上一个节点创建时必然有元素和父节点
        public Node(E element, Node parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

    }

    /**
     * 判断是一个完全二叉树
     */
    public boolean isCopmlete() {
        if (roots == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(roots);

        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();//将头结点出队
            if (leaf && !node.isLeaf()) {
                return false;
            }
            if (node.hasTwoChildren()) {
                queue.offer(node.left);//如果节点的左子节点为空就加入一个
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) {
                return false;
            } else {//后面遍历的必须是叶子节点
                leaf = true;
            }
        }
        return true;

    }
    private Node<E> successor(Node<E> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    public void remove(E element) {
        remove(node(element));
    }

    public boolean contains(E element) {
        return node(element) != null;
    }

    private void remove(Node<E> node) {
        if (node == null) return;

        size--;

        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<E> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.element = s.element;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<E> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                roots = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            roots = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
        }
    }

    private Node<E> node(E element) {
        Node<E> node = roots;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else { // cmp < 0
                node = node.left;
            }
        }
        return null;
    }



    //层序遍历（使用队列的先进先出）
    public void leverlPrderTraversl(){

        //计算二叉树的高度
        int height = 0;
        int le = 1;//第一层的高度

        if (roots == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(roots);
        System.out.println(queue.element());
        while (!queue.isEmpty()){
            Node<E> node = queue.poll();//将头结点出队
            if (node.left != null){
                queue.offer(node.left);//如果节点的左子节点为空就加入一个
            }
            if (node.right != null){
                queue.offer(node.right);//如果节点的左子节点为空就加入一个
            }
        }

        if(le == 0){
            le = queue.size();//高度等于队列的长度，因为只有队列头出去才会加入二叉数的左子集和右子集
            height++;
        }

    }



    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(E element){
        elementNotNullCheck(element);
        //添加第一个节点(也就是根节点信息)
        if (roots == null){
            roots = new Node<>(element,null);
            size++;
            return;
        }

        //找到父节点,设置两个父节点位置，要将循环为空找到插入点的位置进行替换
        Node<E> parent = roots;
        Node<E> node = roots;
        int cmp = 0;
        //循环开始：父节点不为空
        while (node != null){
            //判断传入的元素和父节点元素比较
            cmp = compare(element,node.element);
            //在这里将父节点元素先取出（后面循环找到后会将元素替换为空）
            parent = node;
            if (cmp > 0){
                //等于是一层一层比较将最上层的根节点信息保存到根的左边或者右边，直到找到最后的节点为空，将空值返回出去设置成新传入的节点
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else {//相等就覆盖
                node.element = element;
                return;
            }
        }

        //创建一个新的节点(父节点在上面记录)
        Node<E> newNode = new Node<>(element,parent);
        //看看插入到找到的那个父节点的位置
        if (cmp > 0){
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;
    }

    //返回值等于0，代表e1和e2相等，返回值小于0，代表e1小于e2，返回值大于0，代表e1大于e2
    public int compare(E e1,E e2){
        if (comparator != null) {
            return compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }


    //判断为空
    public void elementNotNullCheck(E element){
        if (element == null){
            throw new IllegalArgumentException("element must be null");
        }
    }




















    //实现打印方法
    @Override
    public Object root() {
        return roots;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>) node).element;
    }

}
