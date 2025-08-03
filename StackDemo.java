public class StackDemo {
    // ==== NODE FOR STACK ====
    private static class Node {
        int elem;
        Node next;
        Node(int e) { elem = e; next = null; }
    }

    // ==== STACK FIELDS & CORE METHODS ====
    private Node top;

    public StackDemo() {
        top = null;
    }

    // Push onto stack
    public void push(int value) {
        Node n = new Node(value);
        n.next = top;
        top = n;
    }

    // Pop from stack (no error checks)
    public int pop() {
        int v = top.elem;
        top = top.next;
        return v;
    }

    // Peek at top (no error checks)
    public int peek() {
        return top.elem;
    }

    public boolean isEmpty() {
        return top == null;
    }

    // Display stack top→bottom
    public void displayStack() {
        System.out.print("Stack (top→bottom): ");
        for (Node cur = top; cur != null; cur = cur.next) {
            System.out.print(cur.elem + " ");
        }
        System.out.println();
    }

    // ==== PARTIAL-REVERSAL METHODS ====

    /** Reverse exactly the top k elements, leave the rest untouched. */
    public void reverseTopK(int k) {
        // 1) Pop k elements into a buffer
        int[] buf = new int[k];
        for (int i = 0; i < k; i++) {
            buf[i] = pop();
        }
        // 2) Push them back → these k are now reversed at the top
        for (int i = 0; i < k; i++) {
            push(buf[i]);
        }
    }

    /** Reverse elements up to (but not including) the first occurrence of sentinel. */
    public void reverseUntil(int sentinel) {
        // 1) Pop into buffer until sentinel is at top
        java.util.List<Integer> buf = new java.util.ArrayList<>();
        while (!isEmpty() && peek() != sentinel) {
            buf.add(pop());
        }
        // 2) Push them back → that chunk is now reversed
        for (int v : buf) {
            push(v);
        }
    }

    // ==== DEMO MAIN ====
    public static void main(String[] args) {
        StackDemo stack = new StackDemo();
        // build stack 1,2,3,4,5 (push order)
        for (int i = 1; i <= 5; i++) {
            stack.push(i);
        }
        System.out.println("Original:");
        stack.displayStack();     // 5 4 3 2 1

        // reverse top 3
        stack.reverseTopK(3);
        System.out.println("After reverseTopK(3):");
        stack.displayStack();     // 3 4 5 2 1

        // reset
        stack = new StackDemo();
        for (int i = 1; i <= 5; i++) stack.push(i);

        // reverse until sentinel '2'
        stack.reverseUntil(2);
        System.out.println("After reverseUntil(2):");
        stack.displayStack();     // 2 3 4 5 1
    }
}
