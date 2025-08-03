public class CircularSinglyLinkedList {

    // Node class
    private static class Node {
        int elem;
        Node next;

        Node(int elem) {
            this.elem = elem;
            this.next = null;
        }
    }

    private Node head; // points to first node; null if list is empty

    // 1. Create a circular list from an array
    public void createFromArray(int[] arr) {
        if (arr == null || arr.length == 0) return;
        head = new Node(arr[0]);
        Node curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new Node(arr[i]);
            curr = curr.next;
        }
        curr.next = head; // close the circle
    }

    // 2. Iterate through the list once
    public void iterate() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        Node curr = head;
        do {
            System.out.print(curr.elem + " -> ");
            curr = curr.next;
        } while (curr != head);
        System.out.println("(back to head)");
    }

    // 3. Count nodes in the list
    public int count() {
        if (head == null) return 0;
        int cnt = 0;
        Node curr = head;
        do {
            cnt++;
            curr = curr.next;
        } while (curr != head);
        return cnt;
    }

    // 4. Index of element (first occurrence), or -1 if not found
    public int indexOf(int elem) {
        if (head == null) return -1;
        int idx = 0;
        Node curr = head;
        do {
            if (curr.elem == elem) return idx;
            curr = curr.next;
            idx++;
        } while (curr != head);
        return -1;
    }

    // 5. Get node at index (0-based), or null if out of bounds
    public Node getNode(int index) {
        int size = count();
        if (size == 0 || index < 0 || index >= size) return null;
        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    // 6. Update value at given index
    public boolean update(int index, int newValue) {
        Node node = getNode(index);
        if (node == null) return false;
        node.elem = newValue;
        return true;
    }

    // 7. Search for element in the list
    public boolean search(int elem) {
        return indexOf(elem) != -1;
    }

    // 8. Insert element at given index
    public void insert(int index, int elem) {
        Node newNode = new Node(elem);
        int size = count();
        // inserting into empty list
        if (size == 0) {
            head = newNode;
            newNode.next = newNode;
            return;
        }
        // clamp index to [0..size]
        if (index <= 0) {
            // insert before head
            // find last node to update its next
            Node last = head;
            while (last.next != head) {
                last = last.next;
            }
            newNode.next = head;
            head = newNode;
            last.next = head;
        } else if (index >= size) {
            // insert at end: after last
            Node last = head;
            while (last.next != head) {
                last = last.next;
            }
            last.next = newNode;
            newNode.next = head;
        } else {
            // insert in middle
            Node prev = getNode(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }
    }

    // 9. Remove node at given index
    public void remove(int index) {
        int size = count();
        if (size == 0 || index < 0 || index >= size) return;
        if (size == 1) {
            // removing the only node
            head = null;
            return;
        }
        if (index == 0) {
            // remove head: find last to update its next
            Node last = head;
            while (last.next != head) {
                last = last.next;
            }
            head = head.next;
            last.next = head;
        } else {
            // remove after prev
            Node prev = getNode(index - 1);
            prev.next = prev.next.next;
        }
    }

    // 10. Deep copy of the circular list
    public CircularSinglyLinkedList copy() {
        CircularSinglyLinkedList copy = new CircularSinglyLinkedList();
        if (head == null) return copy;
        // copy head
        copy.head = new Node(head.elem);
        Node origCurr = head.next;
        Node copyCurr = copy.head;
        // copy remaining nodes
        while (origCurr != head) {
            copyCurr.next = new Node(origCurr.elem);
            copyCurr = copyCurr.next;
            origCurr = origCurr.next;
        }
        // close the circle
        copyCurr.next = copy.head;
        return copy;
    }

    // 11. Out-of-place reverse
    public CircularSinglyLinkedList reverseOutOfPlace() {
        CircularSinglyLinkedList rev = new CircularSinglyLinkedList();
        if (head == null) return rev;
        Node curr = head;
        do {
            Node n = new Node(curr.elem);
            if (rev.head == null) {
                // first node
                rev.head = n;
                n.next = n;
            } else {
                // prepend and fix last.next
                Node last = rev.head;
                while (last.next != rev.head) {
                    last = last.next;
                }
                n.next = rev.head;
                rev.head = n;
                last.next = rev.head;
            }
            curr = curr.next;
        } while (curr != head);
        return rev;
    }

    // 12. In-place reverse
    public void reverseInPlace() {
        int size = count();
        if (size <= 1) return;
        Node prev = head;
        Node curr = head.next;
        // reverse pointers
        for (int i = 0; i < size - 1; i++) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        // fix head.next to complete circle
        head.next = prev;
        head = prev; // new head
    }

    // 13. Rotate left by k steps
    public void rotateLeft(int k) {
        int size = count();
        if (size == 0) return;
        k = ((k % size) + size) % size;
        for (int i = 0; i < k; i++) {
            head = head.next;
        }
    }

    // 14. Rotate right by k steps
    public void rotateRight(int k) {
        rotateLeft(-k);
    }

    // Demo
    public static void main(String[] args) {
        CircularSinglyLinkedList cl = new CircularSinglyLinkedList();

        cl.createFromArray(new int[]{1,2,3,4,5});
        cl.iterate();                // 1 -> 2 -> 3 -> 4 -> 5 -> (back to head)
        System.out.println("Count: " + cl.count());
        System.out.println("Index of 3: " + cl.indexOf(3));

        cl.update(1, 10);
        cl.iterate();                // 1 -> 10 -> 3 -> 4 -> 5 -> (back)

        System.out.println("Search 4: " + cl.search(4));

        cl.insert(0, 99);
        cl.insert(3, 77);
        cl.insert(cl.count(), 55);
        cl.iterate();                // 99 -> 1 -> 10 -> 77 -> 3 -> 4 -> 5 -> 55 -> (back)

        cl.remove(2);
        cl.iterate();                // 99 -> 1 -> 77 -> 3 -> 4 -> 5 -> 55 -> (back)

        CircularSinglyLinkedList copy = cl.copy();
        System.out.print("Copy: "); copy.iterate();

        CircularSinglyLinkedList rev = cl.reverseOutOfPlace();
        System.out.print("Reversed new: "); rev.iterate();

        cl.reverseInPlace();
        System.out.print("Reversed in-place: "); cl.iterate();

        cl.rotateLeft(2);
        System.out.print("Rotated left 2: "); cl.iterate();

        cl.rotateRight(3);
        System.out.print("Rotated right 3: "); cl.iterate();
    }
}
