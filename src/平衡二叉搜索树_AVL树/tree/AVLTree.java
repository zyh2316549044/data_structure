package 平衡二叉搜索树_AVL树.tree;

import java.util.Comparator;

public class AVLTree<E> extends BST<E> {

    public AVLTree(){
       this(null);
    }

    public AVLTree(Comparator<E> comparator){
        super(comparator);
    }


    /**
     *
     * @param node 新添加的节点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        //一直循环找到失衡节点
        while ((node = node.parent) != null){
            if (isBalanced(node)){
                //更新高度
                updateheight(node);
            }else {
                //恢复平衡
                rebalance(node);
                //整个树恢复平衡
                break;
            }
            }
        }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null){
            if (isBalanced(node)){
                //更新高度
                updateheight(node);
            }else {
                //恢复平衡
                rebalance(node);
            }
        }
    }



    //恢复平衡（高度最低的不平衡节点）
    private void rebalance(Node<E> grand){
        Node<E> parent = ((AVLNode<E>)grand).tallerChid();
        Node<E> node = ((AVLNode<E>)parent).tallerChid();
        //如果父节点是左子节点高
        if(parent.isLeftChild()){
            //如果左子节点的左节点高就是LL
            if (node.isLeftChild()){
                rotateRight(grand);
            }else {//L
                rotateLeft(parent);
                rotateRight(grand);

            }
        }else {//一开始就是R
            if (node.isLeftChild()){//RL
                rotateRight(parent);
                rotateLeft(grand);
            }else {//RR
                rotateLeft(grand);
            }
        }

    }

    //左旋转
    private void rotateLeft(Node<E> grand){
      Node<E> parent =  grand.right;//传入节点的右节点
      Node<E> child = parent.left;
      grand.right = child;
      parent.left = grand;
      afterRotate(grand,parent,child);
    }

    //右旋转
    private void rotateRight(Node<E> grand){
        Node<E> parent = grand.left;//grand的left节点=parent
        Node<E> child = parent.right;//parent的right节点=child
        grand.left = child;
        parent.right = grand;
        afterRotate(grand,parent,child);
    }

    //旋转都要做的事
    private void afterRotate(Node<E> grand,Node<E> parent,Node<E> child){
        //让parent称为子树的跟节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()){
            grand.parent.left = parent;
        }else if (grand.isRightChild()){
            grand.parent.right = parent;
        }else {//grand是跟节点
            root = parent;
        }

        //更新child的parent
        if (child != null){
            child.parent = grand;
        }
        //更新grand的parent
        grand.parent = parent;

        //更新高度
        updateheight(grand);
        updateheight(parent);
    }


    private boolean isBalanced(Node<E> node){
        //返回 int 值的绝对值。如果参数为非负数，则返回该参数。如果参数为负数，则返回该参数的相反数。
        return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
    }
    //更新高度
    private void updateheight(Node<E> node){
        ((AVLNode<E>) node).updateheight();

    }
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }
    //创建一个节点类
    private static class AVLNode<E> extends BinaryTree.Node<E>{
        int height = 1;//树的高度


        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        //求出高度
        public int balanceFactor(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            return leftHeight - rightHeight;
        }

        //更新高度
        public void updateheight(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            height = 1+Math.max(leftHeight,rightHeight);
        }

        //父节点下高的那个节点
        public Node<E> tallerChid() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            //看看是不是父亲左子点，是就left不是就right
            return isLeftChild() ? left :right;
        }

    }
}

