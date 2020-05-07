package 队列_Queue;

/**
 * Created by 2316549044 on 2020/4/5.
 */
public class CircleQueue<E> {
    private int font;//表头下标的

    private int size;

    private E[] elements;

    public CircleQueue(){
        elements = (E[]) new Object[10];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**入队*/
    public void enQuene(E element){
        elements[(font +size) % elements.length] = element;
        size++;
    }

    /**出队*/
    public E deQueue(){
        E frontElemt = elements[font];
        elements[font] = null;
        font = (font + 1) % elements.length;
        size--;
        return frontElemt;

    }





}
