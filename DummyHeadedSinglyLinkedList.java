public class DummyHeadedSinglyLinkedList {

    // Node class
    private static class Node {
        int elem;
        Node next;

        Node(int elem) {
            this.elem = elem;
            this.next = null;
        }
    }

    private Node head;  // dummy head; head.next is first real node

    // Constructor: initialize dummy head
    public DummyHeadedSinglyLinkedList() {
        head = new Node(0);
    }

    // 1. Create list from array
    public void createFromArray(int[] arr) {
        head.next = null;
        Node curr = head;
        if (arr == null || arr.length == 0) return;
        for (int value : arr) {
            curr.next = new Node(value);
            curr = curr.next;
        }
    }

    // 2. Iterate through list
    public void iterate() {
        Node curr = head.next;
        while (curr != null) {
            System.out.print(curr.elem + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
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

    // 4. Index of element (0-based), -1 if not found
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

    // 5. Get node at index, or null if out of bounds
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

    // 6. Update value at index
    public boolean update(int index, int newValue) {
        Node node = getNode(index);
        if (node != null) {
            node.elem = newValue;
            return true;
        }
        return false;
    }

    // 7. Search for element
    public boolean search(int elem) {
        return indexOf(elem) != -1;
    }

    // 8. Insert element at index
    public void insert(int index, int elem) {
        Node prev = head;
        int i = 0;
        // clamp index to [0..count]
        int size = count();
        if (index < 0) index = 0;
        if (index > size) index = size;
        while (i < index) {
            prev = prev.next;
            i++;
        }
        Node newNode = new Node(elem);
        newNode.next = prev.next;
        prev.next = newNode;
    }

    // 9. Remove node at index
    public void remove(int index) {
        if (index < 0) return;
        Node prev = head;
        int i = 0;
        while (prev.next != null && i < index) {
            prev = prev.next;
            i++;
        }
        if (prev.next != null) {
            prev.next = prev.next.next;
        }
    }

    // 10. Deep copy
    public DummyHeadedSinglyLinkedList copy() {
        DummyHeadedSinglyLinkedList copy = new DummyHeadedSinglyLinkedList();
        Node curr = head.next;
        Node copyTail = copy.head;
        while (curr != null) {
            copyTail.next = new Node(curr.elem);
            copyTail = copyTail.next;
            curr = curr.next;
        }
        return copy;
    }

    // 11. Out-of-place reverse
    public DummyHeadedSinglyLinkedList reverseOutOfPlace() {
        DummyHeadedSinglyLinkedList rev = new DummyHeadedSinglyLinkedList();
        Node curr = head.next;
        while (curr != null) {
            Node n = new Node(curr.elem);
            n.next = rev.head.next;
            rev.head.next = n;
            curr = curr.next;
        }
        return rev;
    }

    // 12. In-place reverse
    public void reverseInPlace() {
        Node prev = null;
        Node curr = head.next;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head.next = prev;
    }

    // 13. Rotate left by k
    public void rotateLeft(int k) {
        int size = count();
        if (size == 0) return;
        k = ((k % size) + size) % size;
        if (k == 0) return;
        // find kth node and its prev
        Node prev = head;
        for (int i = 0; i < k; i++) {
            prev = prev.next;
        }
        Node newHead = prev.next;
        prev.next = null;
        // find old tail
        Node tail = newHead;
        while (tail.next != null) tail = tail.next;
        tail.next = head.next;
        head.next = newHead;
    }

    // 14. Rotate right by k
    public void rotateRight(int k) {
        int size = count();
        if (size == 0) return;
        rotateLeft(size - (k % size));
    }

    // Demo
    public static void main(String[] args) {
        DummyHeadedSinglyLinkedList dl = new DummyHeadedSinglyLinkedList();
        dl.createFromArray(new int[]{1,2,3,4,5});
        System.out.print("Original: "); dl.iterate();
        System.out.println("Count: " + dl.count());
        System.out.println("Index of 3: " + dl.indexOf(3));
        dl.update(1, 10); System.out.print("After update: "); dl.iterate();
        System.out.println("Search 4: " + dl.search(4));
        dl.insert(0,99); dl.insert(3,77); dl.insert(dl.count(),55);
        System.out.print("After inserts: "); dl.iterate();
        dl.remove(2); System.out.print("After remove: "); dl.iterate();
        DummyHeadedSinglyLinkedList copy = dl.copy(); System.out.print("Copy: "); copy.iterate();
        DummyHeadedSinglyLinkedList rev = dl.reverseOutOfPlace(); System.out.print("Reversed new: "); rev.iterate();
        dl.reverseInPlace(); System.out.print("Reversed in-place: "); dl.iterate();
        dl.rotateLeft(2); System.out.print("Rotated left 2: "); dl.iterate();
        dl.rotateRight(2); System.out.print("Rotated right 2: "); dl.iterate();
    }
}
