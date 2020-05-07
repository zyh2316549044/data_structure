package 链表_LinkedList;

/**
 * Created by 2316549044 on 2020/3/20.
 */
public class LinkedList<E> extends AbstractList<E>{

    private Node firstNode;//第一个链表(包含元素，连接点)


    /*
     链表的节点
     */
    private static class Node<E>{
        //每一个节点里面有一个自己元素和一个指定下一个节点的
        E elementE;//元素
        public Node<E> NextNode;//指向下一个节点的连接点

        public Node(E elementE, Node<E> nextNode) {
            this.elementE = elementE;
            NextNode = nextNode;
        }
    }




    @Override
    public void clear() {

        size = 0;
        firstNode = null;//将第一个链表元素为空之后后面连接的元素都会挂掉
    }

    /**
     * 通过索引获取元素位置
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        return node(index).elementE;
    }


    /**
     * 通过索引修改元素
     * @param index
     * @param element
     * @return
     */
    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);//取出当前索引元素
        E oldE = node.elementE;
        node.elementE = oldE;
        return oldE;
    }

    /**
     * 通过索引获取元素
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //如果为0就直接将链表的头节点指向头节点的连接点的下一个
        if (index == 0){
            firstNode = new Node(element,firstNode);
        }else {
            //跟据索引获取前一个节点
            Node<E> prev = node(index - 1);
            //将前一个连接点的元素赋值给新元素和连接点
            prev.NextNode = new Node<>(element,prev.NextNode);
        }
        size++;

    }

    @Override
    public E remove(int index) {
        rangeCheck(index);//判断这个索引是否有问题
        //节点信息拿出来
        Node<E> node = firstNode;
        if (index == 0) {
            //直接将当前节点信息指向下一个
            firstNode = firstNode.NextNode;
        }else {
            //跟据索引获取前一个节点
            Node<E> prev = node(index - 1);
            //节点从新赋值
            node = prev.NextNode;
            //将节点的连接点指向下一个的下一个
            prev.NextNode = prev.NextNode.NextNode;
        }
        size--;
        return node.elementE;
    }

    /***
     *
     * 传入对应索引的位置节点
     * @param index
     * @return
     */
    private Node<E> node(int index){
        rangeCheck(index);//判断这个索引是否有问题

        Node<E> node = firstNode;//从第一个索引元素开始

        //将传入的索引次数遍历出来
        for (int i = 0; i <index ; i++) {

            node = node.NextNode;//遍历一次指向下一个元素赋值给node
        }

        return node;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        Node<E> node = firstNode;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(node.elementE);

            node = node.NextNode;
        }
        string.append("]");
        return string.toString();
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            Node<E> node = firstNode;
            for (int i = 0; i < size; i++) {
                if (node.elementE == null) return i;

                node = node.NextNode;
            }
        } else {
            Node<E> node = firstNode;
            for (int i = 0; i < size; i++) {
                if (element.equals(node.elementE)) return i;

                node = node.NextNode;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

}
