public class CircularDoublyLinkedList {

    // Node class for circular doubly linked list
    private static class Node {
        int elem;
        Node prev;
        Node next;

        Node(int elem) {
            this.elem = elem;
        }
    }

    private Node head;  // points to first node; head.prev is last node when list non-empty

    // 1. Create a circular list from an array
    public void createFromArray(int[] arr) {
        if (arr == null || arr.length == 0) return;
        // create head
        head = new Node(arr[0]);
        Node curr = head;
        // append rest
        for (int i = 1; i < arr.length; i++) {
            Node n = new Node(arr[i]);
            curr.next = n;
            n.prev = curr;
            curr = n;
        }
        // close the circle: link last and head
        curr.next = head;
        head.prev = curr;
    }

    // 2a. Iterate forward through the list
    public void iterateForward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        Node curr = head;
        do {
            System.out.print(curr.elem + " <-> ");
            curr = curr.next;
        } while (curr != head);
        System.out.println("(back to head)");
    }

    // 2b. Iterate backward through the list
    public void iterateBackward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        Node curr = head.prev;  // start from last
        do {
            System.out.print(curr.elem + " <-> ");
            curr = curr.prev;
        } while (curr != head.prev);
        System.out.println("(back to tail)");
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

    // 4. Index of element (first occurrence)
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

    // 5. Get node at index (0-based)
    public Node getNode(int index) {
        int size = count();
        if (index < 0 || index >= size) return null;
        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    // 6. Update value at index
    public boolean update(int index, int newValue) {
        Node node = getNode(index);
        if (node == null) return false;
        node.elem = newValue;
        return true;
    }

    // 7. Search for element
    public boolean search(int elem) {
        return indexOf(elem) != -1;
    }

    // 8. Insert at given index
    public void insert(int index, int elem) {
        Node newNode = new Node(elem);
        int size = count();
        // empty list: new node points to itself
        if (size == 0) {
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
            return;
        }
        // clamp index
        if (index <= 0) {
            // insert before head
            Node last = head.prev;
            newNode.next = head;
            newNode.prev = last;
            last.next = newNode;
            head.prev = newNode;
            head = newNode;
        } else if (index >= size) {
            // insert at end (before head)
            Node last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        } else {
            // insert in middle
            Node prev = getNode(index - 1);
            Node next = prev.next;
            prev.next = newNode;
            newNode.prev = prev;
            newNode.next = next;
            next.prev = newNode;
        }
    }

    // 9. Remove node at index
    public void remove(int index) {
        int size = count();
        if (size == 0 || index < 0 || index >= size) return;
        // single node
        if (size == 1) {
            head = null;
            return;
        }
        if (index == 0) {
            // remove head
            Node last = head.prev;
            head = head.next;
            last.next = head;
            head.prev = last;
        } else {
            // remove in middle or end
            Node prev = getNode(index - 1);
            Node toRemove = prev.next;
            Node next = toRemove.next;
            prev.next = next;
            next.prev = prev;
        }
    }

    // 10. Deep copy
    public CircularDoublyLinkedList copy() {
        CircularDoublyLinkedList copy = new CircularDoublyLinkedList();
        if (head == null) return copy;
        // copy head
        copy.head = new Node(head.elem);
        Node orig = head.next;
        Node currCopy = copy.head;
        // copy remaining
        while (orig != head) {
            Node n = new Node(orig.elem);
            currCopy.next = n;
            n.prev = currCopy;
            currCopy = n;
            orig = orig.next;
        }
        // close circle
        currCopy.next = copy.head;
        copy.head.prev = currCopy;
        return copy;
    }

    // 11. Out-of-place reverse
    public CircularDoublyLinkedList reverseOutOfPlace() {
        CircularDoublyLinkedList rev = new CircularDoublyLinkedList();
        if (head == null) return rev;
        Node curr = head;
        do {
            Node n = new Node(curr.elem);
            if (rev.head == null) {
                rev.head = n;
                n.next = n;
                n.prev = n;
            } else {
                // prepend n before rev.head
                Node first = rev.head;
                Node last = first.prev;
                n.next = first;
                n.prev = last;
                last.next = n;
                first.prev = n;
                rev.head = n;
            }
            curr = curr.next;
        } while (curr != head);
        return rev;
    }

    // 12. In-place reverse
    public void reverseInPlace() {
        int size = count();
        if (size <= 1) return;
        Node curr = head;
        do {
            Node temp = curr.next;
            curr.next = curr.prev;
            curr.prev = temp;
            curr = temp;
        } while (curr != head);
        head = head.next; // new head was previous tail
    }

    // 13. Rotate left by k steps
    public void rotateLeft(int k) {
        int size = count();
        if (size == 0) return;
        k = ((k % size) + size) % size;
        for (int i = 0; i < k; i++) head = head.next;
    }

    // 14. Rotate right by k steps
    public void rotateRight(int k) {
        rotateLeft(-k);
    }

    // Demo of all operations
    public static void main(String[] args) {
        CircularDoublyLinkedList cd = new CircularDoublyLinkedList();
        cd.createFromArray(new int[]{1,2,3,4,5});
        cd.iterateForward();            // forward
        cd.iterateBackward();           // backward
        System.out.println("Count: " + cd.count());
        System.out.println("Index of 3: " + cd.indexOf(3));
        cd.update(1, 10); cd.iterateForward();
        System.out.println("Search 4: " + cd.search(4));
        cd.insert(0, 99); cd.insert(3, 77); cd.insert(cd.count(), 55);
        cd.iterateForward();
        cd.remove(2); cd.iterateForward();
        CircularDoublyLinkedList copy = cd.copy(); System.out.print("Copy: "); copy.iterateForward();
        CircularDoublyLinkedList rev = cd.reverseOutOfPlace(); System.out.print("Reversed new: "); rev.iterateForward();
        cd.reverseInPlace(); System.out.print("Reversed in-place: "); cd.iterateForward();
        cd.rotateLeft(2); System.out.print("Rotated left 2: "); cd.iterateForward();
        cd.rotateRight(3); System.out.print("Rotated right 3: "); cd.iterateForward();
    }
}
