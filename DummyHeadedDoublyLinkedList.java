public class DummyHeadedDoublyLinkedList {

    // Node class for doubly linked list with dummy head
    private static class Node {
        int elem;
        Node prev;
        Node next;

        Node(int elem) {
            this.elem = elem;
        }
    }

    private Node head;  // dummy head: head.next is first real node

    // Constructor: initialize dummy head
    public DummyHeadedDoublyLinkedList() {
        head = new Node(0);
    }

    // 1. Create list from array
    public void createFromArray(int[] arr) {
        // clear existing
        head.next = null;
        if (arr == null || arr.length == 0) return;
        Node curr = head;
        for (int value : arr) {
            Node n = new Node(value);
            curr.next = n;
            n.prev = curr;
            curr = n;
        }
        // last node's next is null by default
    }

    // 2a. Iterate forward
    public void iterateForward() {
        Node curr = head.next;
        while (curr != null) {
            System.out.print(curr.elem + " <-> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    // 2b. Iterate backward
    public void iterateBackward() {
        // find last node
        Node curr = head.next;
        if (curr == null) {
            System.out.println("null");
            return;
        }
        while (curr.next != null) {
            curr = curr.next;
        }
        // traverse back to dummy head
        while (curr != head) {
            System.out.print(curr.elem + " <-> ");
            curr = curr.prev;
        }
        System.out.println("head");
    }

    // 3. Count real nodes
    public int count() {
        int cnt = 0;
        Node curr = head.next;
        while (curr != null) {
            cnt++;
            curr = curr.next;
        }
        return cnt;
    }

    // 4. Index of element
    public int indexOf(int elem) {
        int idx = 0;
        Node curr = head.next;
        while (curr != null) {
            if (curr.elem == elem) return idx;
            curr = curr.next;
            idx++;
        }
        return -1;
    }

    // 5. Get node by index
    public Node getNode(int index) {
        if (index < 0) return null;
        int i = 0;
        Node curr = head.next;
        while (curr != null) {
            if (i == index) return curr;
            curr = curr.next;
            i++;
        }
        return null;
    }

    // 6. Update value
    public boolean update(int index, int newValue) {
        Node node = getNode(index);
        if (node != null) {
            node.elem = newValue;
            return true;
        }
        return false;
    }

    // 7. Search element
    public boolean search(int elem) {
        return indexOf(elem) != -1;
    }

    // 8. Insert at index
    public void insert(int index, int elem) {
        int size = count();
        if (index < 0) index = 0;
        if (index > size) index = size;
        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node newNode = new Node(elem);
        Node next = prev.next;
        prev.next = newNode;
        newNode.prev = prev;
        newNode.next = next;
        if (next != null) {
            next.prev = newNode;
        }
    }

    // 9. Remove at index
    public void remove(int index) {
        if (index < 0) return;
        Node prev = head;
        int i = 0;
        while (prev.next != null && i < index) {
            prev = prev.next;
            i++;
        }
        Node toRemove = prev.next;
        if (toRemove != null) {
            Node next = toRemove.next;
            prev.next = next;
            if (next != null) {
                next.prev = prev;
            }
        }
    }

    // 10. Deep copy
    public DummyHeadedDoublyLinkedList copy() {
        DummyHeadedDoublyLinkedList copy = new DummyHeadedDoublyLinkedList();
        Node curr = head.next;
        Node copyTail = copy.head;
        while (curr != null) {
            Node n = new Node(curr.elem);
            copyTail.next = n;
            n.prev = copyTail;
            copyTail = n;
            curr = curr.next;
        }
        return copy;
    }

    // 11. Out-of-place reverse
    public DummyHeadedDoublyLinkedList reverseOutOfPlace() {
        DummyHeadedDoublyLinkedList rev = new DummyHeadedDoublyLinkedList();
        Node curr = head.next;
        while (curr != null) {
            Node n = new Node(curr.elem);
            // prepend to rev
            Node first = rev.head.next;
            rev.head.next = n;
            n.prev = rev.head;
            n.next = first;
            if (first != null) first.prev = n;
            curr = curr.next;
        }
        return rev;
    }

    // 12. In-place reverse
    public void reverseInPlace() {
        Node curr = head.next;
        Node prev = null;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            curr.prev = next;
            prev = curr;
            curr = next;
        }
        head.next = prev;
        if (prev != null) prev.prev = head;
    }

    // 13. Rotate left by k
    public void rotateLeft(int k) {
        int size = count();
        if (size == 0) return;
        k = ((k % size) + size) % size;
        for (int i = 0; i < k; i++) {
            // remove first real
            Node first = head.next;
            head.next = first.next;
            if (first.next != null) first.next.prev = head;
            // append at end
            Node tail = head;
            while (tail.next != null) tail = tail.next;
            tail.next = first;
            first.prev = tail;
            first.next = null;
        }
    }

    // 14. Rotate right by k
    public void rotateRight(int k) {
        int size = count();
        if (size == 0) return;
        rotateLeft(size - (k % size));
    }

    // Demo all operations
    public static void main(String[] args) {
        DummyHeadedDoublyLinkedList dl = new DummyHeadedDoublyLinkedList();
        dl.createFromArray(new int[]{1,2,3,4,5});
        System.out.print("Forward: "); dl.iterateForward();
        System.out.print("Backward: "); dl.iterateBackward();
        System.out.println("Count: " + dl.count());
        System.out.println("Index of 3: " + dl.indexOf(3));
        dl.update(1, 10); System.out.print("After update: "); dl.iterateForward();
        System.out.println("Search 4: " + dl.search(4));
        dl.insert(0, 99); dl.insert(3, 77); dl.insert(dl.count(), 55);
        System.out.print("After inserts: "); dl.iterateForward();
        dl.remove(2); System.out.print("After remove: "); dl.iterateForward();
        DummyHeadedDoublyLinkedList copy = dl.copy(); System.out.print("Copy: "); copy.iterateForward();
        DummyHeadedDoublyLinkedList rev = dl.reverseOutOfPlace(); System.out.print("Reversed new: "); rev.iterateForward();
        dl.reverseInPlace(); System.out.print("Reversed in-place: "); dl.iterateForward();
        dl.rotateLeft(2); System.out.print("Rotated left 2: "); dl.iterateForward();
        dl.rotateRight(2); System.out.print("Rotated right 2: "); dl.iterateForward();
    }
}
