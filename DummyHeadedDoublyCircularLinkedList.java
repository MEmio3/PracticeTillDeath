public class DummyHeadedDoublyCircularLinkedList {

    // Node class for circular doubly linked list with dummy head
    private static class Node {
        int elem;
        Node prev;
        Node next;

        Node(int elem) {
            this.elem = elem;
        }
    }

    private Node head;  // dummy head, part of the circle

    // Constructor: initialize dummy head to point to itself
    public DummyHeadedDoublyCircularLinkedList() {
        head = new Node(0);
        head.next = head;
        head.prev = head;
    }

    // 1. Create circular list from array
    public void createFromArray(int[] arr) {
        // reset to empty circle
        head.next = head;
        head.prev = head;
        if (arr == null || arr.length == 0) return;
        Node curr = head;
        for (int value : arr) {
            Node n = new Node(value);
            curr.next = n;
            n.prev = curr;
            curr = n;
        }
        // close the circle
        curr.next = head;
        head.prev = curr;
    }

    // 2a. Iterate forward
    public void iterateForward() {
        if (head.next == head) {
            System.out.println("List is empty");
            return;
        }
        Node curr = head.next;
        do {
            System.out.print(curr.elem + " <-> ");
            curr = curr.next;
        } while (curr != head);
        System.out.println("(back to head)");
    }

    // 2b. Iterate backward
    public void iterateBackward() {
        if (head.prev == head) {
            System.out.println("List is empty");
            return;
        }
        Node curr = head.prev;
        do {
            System.out.print(curr.elem + " <-> ");
            curr = curr.prev;
        } while (curr != head.prev);
        System.out.println("(back to tail)");
    }

    // 3. Count nodes
    public int count() {
        int cnt = 0;
        Node curr = head.next;
        while (curr != head) {
            cnt++;
            curr = curr.next;
        }
        return cnt;
    }

    // 4. Index of element, or -1
    public int indexOf(int elem) {
        int idx = 0;
        Node curr = head.next;
        while (curr != head) {
            if (curr.elem == elem) return idx;
            curr = curr.next;
            idx++;
        }
        return -1;
    }

    // 5. Get node at index, or null
    public Node getNode(int index) {
        if (index < 0) return null;
        int i = 0;
        Node curr = head.next;
        while (curr != head) {
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
        int size = count();
        // clamp index between 0 and size
        if (index < 0) index = 0;
        if (index > size) index = size;
        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node next = prev.next;
        Node n = new Node(elem);
        // link in
        prev.next = n;
        n.prev = prev;
        n.next = next;
        next.prev = n;
    }

    // 9. Remove node at index
    public void remove(int index) {
        int size = count();
        if (index < 0 || index >= size) return;
        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node toRemove = prev.next;
        Node next = toRemove.next;
        // unlink
        prev.next = next;
        next.prev = prev;
    }

    // 10. Deep copy
    public DummyHeadedDoublyCircularLinkedList copy() {
        DummyHeadedDoublyCircularLinkedList copy = new DummyHeadedDoublyCircularLinkedList();
        Node curr = head.next;
        Node copyTail = copy.head;
        while (curr != head) {
            Node n = new Node(curr.elem);
            copyTail.next = n;
            n.prev = copyTail;
            copyTail = n;
            curr = curr.next;
        }
        // close circle
        copyTail.next = copy.head;
        copy.head.prev = copyTail;
        return copy;
    }

    // 11. Out-of-place reverse
    public DummyHeadedDoublyCircularLinkedList reverseOutOfPlace() {
        DummyHeadedDoublyCircularLinkedList rev = new DummyHeadedDoublyCircularLinkedList();
        Node curr = head.next;
        while (curr != head) {
            // prepend curr.elem
            Node first = rev.head.next;
            Node n = new Node(curr.elem);
            rev.head.next = n;
            n.prev = rev.head;
            n.next = first;
            first.prev = n;
            curr = curr.next;
        }
        return rev;
    }

    // 12. In-place reverse
    public void reverseInPlace() {
        Node curr = head;
        do {
            Node temp = curr.next;
            curr.next = curr.prev;
            curr.prev = temp;
            curr = temp;
        } while (curr != head);
        // head remains dummy; no pointer adjustment needed
    }

    // 13. Rotate left by k steps
    public void rotateLeft(int k) {
        int size = count();
        if (size == 0) return;
        k = ((k % size) + size) % size;
        for (int i = 0; i < k; i++) {
            // remove first real node
            Node first = head.next;
            Node next = first.next;
            head.next = next;
            next.prev = head;
            // append at tail
            Node last = head.prev;
            last.next = first;
            first.prev = last;
            first.next = head;
            head.prev = first;
        }
    }

    // 14. Rotate right by k steps
    public void rotateRight(int k) {
        int size = count();
        if (size == 0) return;
        rotateLeft(size - (k % size));
    }

    // Demo all operations
    public static void main(String[] args) {
        DummyHeadedDoublyCircularLinkedList dl = new DummyHeadedDoublyCircularLinkedList();
        // create
        dl.createFromArray(new int[]{1,2,3,4,5});
        dl.iterateForward();
        dl.iterateBackward();
        System.out.println("Count: " + dl.count());
        System.out.println("Index of 3: " + dl.indexOf(3));
        // update
        dl.update(1, 10);
        dl.iterateForward();
        // search
        System.out.println("Search 4: " + dl.search(4));
        // insert
        dl.insert(0, 99);
        dl.insert(3, 77);
        dl.insert(dl.count(), 55);
        dl.iterateForward();
        // remove
        dl.remove(2);
        dl.iterateForward();
        // copy
        DummyHeadedDoublyCircularLinkedList copy = dl.copy();
        System.out.print("Copy: "); copy.iterateForward();
        // reverse out-of-place
        DummyHeadedDoublyCircularLinkedList rev = dl.reverseOutOfPlace();
        System.out.print("Reversed new: "); rev.iterateForward();
        // reverse in-place
        dl.reverseInPlace();
        System.out.print("Reversed in-place: "); dl.iterateForward();
        // rotate
        dl.rotateLeft(2);
        System.out.print("Rotated left 2: "); dl.iterateForward();
        dl.rotateRight(3);
        System.out.print("Rotated right 3: "); dl.iterateForward();
    }
}
