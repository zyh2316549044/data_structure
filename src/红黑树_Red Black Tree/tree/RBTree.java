package tree;

import java.util.Comparator;

/**
 * 红黑树必须满足的五大性质
 * 节点是red或者black
 * 跟节点是Black
 * 叶子节点（外别节点，空节点）都是black
 * Red节点的子节点都是BlACK
 * red节点的parent都时black
 *
 * @param <E>
 */
public class RBTree<E> extends BBST<E>{

    private static final boolean RED = false;
    private static final boolean BlACK = true;
    /**
     * 构造
     */
    public RBTree() {
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }


    @Override
    /**添加节点*/
    protected void afterAdd(Node<E> node) {
       Node<E> parent = node.parent;

       //添加的是根节点，直接染成黑色，或者直接上益到了跟节点，也染成黑色
        if (parent == null){
            black(node);
            return;
        }
       //如果父节点为黑色直接返回，不用操作
        if (isBlack(parent)) return;

        //叔父节点
        Node<E> uncle = parent.sibling();
        //祖父节点
        Node<E> grand = parent.parent;
        if (isRed(uncle)){
            black(parent);//父节点染黑
            black(uncle);//叔父节点染黑
            //祖父节点当做新添加的节点，直接染红
            afterAdd(red(grand));
            return;
        }
        //叔父节点不是红色
        if (parent.isLeftChild()){//L
            if (node.isLeftChild()){//LL
                 black(parent);//先把父节点染黑
                 red(grand);//再把祖父节点染红
                 rotateRight(grand);//右旋转祖父节点
            }else {//LR
                black(node);//染黑自己
                red(grand);//染红祖父节点
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else {//R
            if (node.isLeftChild()){//RL
                black(node);//染黑自己
                red(grand);//染红祖父节点
                rotateRight(parent);
                rotateLeft(grand);
            }else {//RR
                black(parent);//先把父节点染黑
                red(grand);//再把祖父节点染红
                rotateLeft(grand);//右旋转祖父节点
            }
        }
    }

    /**删除节点*/
    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        //如果删除的节点是红色直接删除不用处理
        if (isRed(node)){
         return;
        }
        //判断用于代替删除节点的左右节点是否为红色
        if (isRed(replacement)){
            //如果是红色直接染成黑色取代当前删除的节点
            black(replacement);
            return;
        }
        //被删除的黑色节点没有子节点（根节点处理方法）
        Node<E> parent = node.parent;
        if(parent == null)return;
        //被删除的黑色节点没有子节点（叶子节点处理方法）
        //拿到兄弟节点判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right :parent.left;
        if (left){//被删除的节点在左边，兄弟节点再右边

            if (isRed(sibling)){//兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);//右旋转
                //更换兄弟
                sibling = parent.right;
            }
            //红色处理完黑色节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){
                //兄弟节点没有一个红色子节点，父节点要象下兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent,null);
                }
            }else {//到这里至少有一个红色,向兄弟节点借元素
                //兄弟节点左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)){
                    rotateRight(sibling);
                    sibling = parent.right;//旋转完后left是当前节点的兄弟节点
                }

                color(sibling,colorOf(parent));//拿到父节点颜色复制给兄弟节点
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        }else {//被删除的节点在右边，兄弟节点在左边

            if (isRed(sibling)){//兄弟节点是红色
               black(sibling);
               red(parent);
               rotateRight(parent);//右旋转
               //更换兄弟
                sibling = parent.left;
            }
            //红色处理完黑色节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){
                //兄弟节点没有一个红色子节点，父节点要象下兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent,null);
                }
            }else {//到这里至少有一个红色,向兄弟节点借元素
                //兄弟节点左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)){
                    rotateLeft(sibling);
                    sibling = parent.left;//旋转完后left是当前节点的兄弟节点
                }

                color(sibling,colorOf(parent));//拿到父节点颜色复制给兄弟节点
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }

    }



    /**染色*/
    private Node<E> color(Node<E> node,boolean color){
        if (node == null) return node;
        ((RBNode<E>)node).color = color;
        return node;
    }
    /**给一个节点染红色*/
    private Node<E> red(Node<E> node){
        return color(node,RED);
    }

    /**给一个节点染黑色*/
    private Node<E> black(Node<E> node){
        return color(node,BlACK);
    }

    /**判断当前节点是否为空如果是空的就染成黑色*/
    private boolean colorOf(Node<E> node){
        return node == null ? BlACK : ((RBNode<E>)node).color;
    }

    /**判断当前节点是否黑*/
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BlACK;
    }
    /**判断当前节点是否红*/
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }


    private static class RBNode<E> extends Node<E>{

        boolean color;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }


        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }



}
