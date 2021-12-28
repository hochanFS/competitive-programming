import java.util.*;

public class IteratorTest {
    private Node head;
    private Node tail;
    private int numberOfElement;

    public boolean add(Object value) {
        Node node = new Node(value, null);
        if (tail == null) {
            tail = head = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        numberOfElement++;
        return true;
    }

    public Object remove() {
        if (head == null) {
            return null;
        }
        Object value = head.getData();
        head = head.getNext();
        if (head == null) {
            tail = null;
        }
        numberOfElement--;
        return value;
    }

    public Iterator<Object> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator {
        private Node cursor;

        public QueueIterator() {
            this.cursor = head;
        }

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public Object next() {
            if (!hasNext()) return null;
            Object object = cursor.getData();
            cursor = cursor.getNext();
            return object;
        }
    }

    private static class Node {
        private Object data;
        private Node next;
        private Node (Object object, Node next) {
            this.data = object;
            this.next = next;
        }

        public Object getData() {
            return data;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }
    }

    public static void main(String[] args) {
        IteratorTest it = new IteratorTest();
        it.add(1);
        it.add("hi");
        it.add("bye");
        for (Iterator x = it.iterator(); x.hasNext(); x.next()) {
            System.out.println(x);
        }
    }
}
