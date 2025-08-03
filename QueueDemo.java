public class QueueDemo {
    // ===== NODE FOR QUEUE =====
    private static class Node {
        int elem;
        Node next;
        Node(int e) { elem = e; next = null; }
    }

    // ===== LINKED‐LIST QUEUE IMPLEMENTATION =====
    private Node front, rear;

    public QueueDemo() {
        front = rear = null;
    }

    public void enqueue(int x) {
        Node n = new Node(x);
        if (rear == null) {
            front = rear = n;
        } else {
            rear.next = n;
            rear = n;
        }
    }

    public int dequeue() {
        int v = front.elem;
        front = front.next;
        if (front == null) rear = null;
        return v;
    }

    public int peek() {
        return front.elem;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        int c = 0;
        for (Node cur = front; cur != null; cur = cur.next) c++;
        return c;
    }

    public void display() {
        System.out.print("Queue (front→rear): ");
        for (Node cur = front; cur != null; cur = cur.next) {
            System.out.print(cur.elem + " ");
        }
        System.out.println();
    }

    // ===== INNER STACK USED FOR REVERSALS =====
    private static class LinkedListStack {
        private static class SNode {
            int elem;
            SNode next;
            SNode(int e) { elem = e; next = null; }
        }
        private SNode top;
        void push(int x) {
            SNode n = new SNode(x);
            n.next = top;
            top = n;
        }
        int pop() {
            int v = top.elem;
            top = top.next;
            return v;
        }
        boolean isEmpty() { return top == null; }
    }

    // ===== QUEUE OPERATIONS =====

    /** Reverse the entire queue in-place. */
    public void reverse() {
        LinkedListStack s = new LinkedListStack();
        while (!isEmpty()) s.push(dequeue());
        while (!s.isEmpty()) enqueue(s.pop());
    }

    /** Reverse only the first k elements, leave the rest in same order. */
    public void reverseTopK(int k) {
        LinkedListStack s = new LinkedListStack();
        int n = size();
        // 1) Pop k into stack
        for (int i = 0; i < k; i++) {
            s.push(dequeue());
        }
        // 2) Push them back → reversed chunk at rear
        while (!s.isEmpty()) {
            enqueue(s.pop());
        }
        // 3) Rotate the untouched (n–k) items to front
        for (int i = 0; i < n - k; i++) {
            enqueue(dequeue());
        }
    }

    /** Reverse elements until (but not including) the first occurrence of sentinel. */
    public void reverseUntil(int sentinel) {
        LinkedListStack s = new LinkedListStack();
        int n = size(), k = 0;
        // 1) Dequeue into stack until peek() == sentinel
        while (peek() != sentinel) {
            s.push(dequeue());
            k++;
        }
        // 2) Push that chunk back → reversed
        while (!s.isEmpty()) {
            enqueue(s.pop());
        }
        // 3) Rotate the untouched (n–k) items to front
        for (int i = 0; i < n - k; i++) {
            enqueue(dequeue());
        }
    }

    /** Generate and print the first n binary numbers (1, 10, 11, 100, …). */
    public static void generateBinaryNumbers(int n) {
        QueueDemo q = new QueueDemo();
        q.enqueue(1);
        for (int i = 0; i < n; i++) {
            int v = q.dequeue();
            System.out.print(v + " ");
            q.enqueue(v * 10);
            q.enqueue(v * 10 + 1);
        }
        System.out.println();
    }

    /** Check if the queue is a palindrome. */
    public boolean isPalindrome() {
        LinkedListStack s = new LinkedListStack();
        QueueDemo temp = new QueueDemo();
        // 1) Empty original into stack & temp
        while (!isEmpty()) {
            int v = dequeue();
            s.push(v);
            temp.enqueue(v);
        }
        // 2) Refill original & compare with stack
        boolean ok = true;
        while (!temp.isEmpty()) {
            int qv = temp.dequeue();
            int sv = s.pop();
            enqueue(qv);  // restore original
            if (qv != sv) ok = false;
        }
        return ok;
    }

    // ===== DEMO MAIN =====
    public static void main(String[] args) {
        QueueDemo q = new QueueDemo();
        for (int i = 1; i <= 5; i++) q.enqueue(i);
        System.out.println("Original:");
        q.display();

        q.reverse();
        System.out.println("Reversed full:");
        q.display();

        q = new QueueDemo();
        for (int i = 1; i <= 5; i++) q.enqueue(i);
        q.reverseTopK(3);
        System.out.println("Reverse top 3:");
        q.display();

        q = new QueueDemo();
        for (int i = 1; i <= 5; i++) q.enqueue(i);
        q.reverseUntil(4);
        System.out.println("Reverse until 4:");
        q.display();

        System.out.println("Binary up to 5:");
        generateBinaryNumbers(5);

        QueueDemo p = new QueueDemo();
        int[] a = {1,2,3,2,1};
        for (int v : a) p.enqueue(v);
        System.out.println("Is palindrome? " + p.isPalindrome());
    }
}
