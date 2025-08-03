public class DoublyLinkedList {

    // Node class for doubly linked list
    private static class Node {
        int elem;
        Node prev;
        Node next;

        Node(int elem) {
            this.elem = elem;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head;  // first node
    private Node tail;  // last node

    // 1. Create a list from an array
    public void createFromArray(int[] arr) {
        if (arr == null || arr.length == 0) return;
        head = new Node(arr[0]);
        Node current = head;
        for (int i = 1; i < arr.length; i++) {
            Node newNode = new Node(arr[i]);
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }
        tail = current; // last node
    }

    // 2. Iterate forward through the list
    public void iterateForward() {
        Node current = head;
        while (current != null) {
            System.out.print(current.elem + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // 2b. (Optional) Iterate backward through the list
    public void iterateBackward() {
        Node current = tail;
        while (current != null) {
            System.out.print(current.elem + " <-> ");
            current = current.prev;
        }
        System.out.println("null");
    }

    // 3. Count the nodes
    public int count() {
        int cnt = 0;
        Node current = head;
        while (current != null) {
            cnt++;
            current = current.next;
        }
        return cnt;
    }

    // 4. Find index of an element (first occurrence)
    public int indexOf(int elem) {
        int idx = 0;
        Node current = head;
        while (current != null) {
            if (current.elem == elem) return idx;
            current = current.next;
            idx++;
        }
        return -1;
    }

    // 5. Get node by index
    public Node getNode(int index) {
        int i = 0;
        Node current = head;
        while (current != null) {
            if (i == index) return current;
            current = current.next;
            i++;
        }
        return null; // out of bounds
    }

    // 6. Update element at index
    public boolean update(int index, int newValue) {
        Node node = getNode(index);
        if (node != null) {
            node.elem = newValue;
            return true;
        }
        return false;
    }

    // 7. Search for an element
    public boolean search(int elem) {
        return indexOf(elem) != -1;
    }

    // 8. Insert a new node at given index
    public void insert(int index, int elem) {
        Node newNode = new Node(elem);
        if (index == 0) {
            // at beginning
            newNode.next = head;
            if (head != null) head.prev = newNode;
            head = newNode;
            if (tail == null) tail = newNode;
            return;
        }
        Node prev = getNode(index - 1);
        if (prev != null) {
            newNode.next = prev.next;
            newNode.prev = prev;
            prev.next = newNode;
            if (newNode.next != null) {
                newNode.next.prev = newNode;
            } else {
                // inserted at end
                tail = newNode;
            }
        }
    }

    // 9. Remove node at given index
    public void remove(int index) {
        if (head == null) return;
        if (index == 0) {
            // remove head
            head = head.next;
            if (head != null) head.prev = null;
            else tail = null;
            return;
        }
        Node prev = getNode(index - 1);
        if (prev != null && prev.next != null) {
            Node toRemove = prev.next;
            prev.next = toRemove.next;
            if (toRemove.next != null) {
                toRemove.next.prev = prev;
            } else {
                // removed tail
                tail = prev;
            }
        }
    }

    // 10. Copy the list (deep copy)
    public DoublyLinkedList copy() {
        DoublyLinkedList newList = new DoublyLinkedList();
        if (head == null) return newList;
        // copy head
        newList.head = new Node(head.elem);
        Node origCurr = head.next;
        Node copyCurr = newList.head;
        // copy rest
        while (origCurr != null) {
            Node n = new Node(origCurr.elem);
            copyCurr.next = n;
            n.prev = copyCurr;
            copyCurr = n;
            origCurr = origCurr.next;
        }
        newList.tail = copyCurr;
        return newList;
    }

    // 11. Out-of-place reverse
    public DoublyLinkedList reverseOutOfPlace() {
        DoublyLinkedList rev = new DoublyLinkedList();
        Node curr = head;
        while (curr != null) {
            Node n = new Node(curr.elem);
            // prepend to rev
            n.next = rev.head;
            if (rev.head != null) rev.head.prev = n;
            rev.head = n;
            if (rev.tail == null) rev.tail = n;
            curr = curr.next;
        }
        return rev;
    }

    // 12. In-place reverse
    public void reverseInPlace() {
        if (head == null) return;
        // swap next/prev for every node
        Node curr = head, temp = null;
        tail = head;  // old head becomes new tail
        while (curr != null) {
            temp = curr.prev;
            curr.prev = curr.next;
            curr.next = temp;
            curr = curr.prev; // move to next in original order
        }
        // fix head
        if (temp != null) {
            head = temp.prev;
        }
    }

    // 13. Rotate left by k nodes
    public void rotateLeft(int k) {
        int size = count();
        if (head == null || k <= 0 || size == 0) return;
        k %= size;
        if (k == 0) return;

        Node newHead = getNode(k);
        Node newTail = newHead.prev;

        // break link
        newTail.next = null;
        newHead.prev = null;

        // link old tail to old head
        tail.next = head;
        head.prev = tail;

        head = newHead;
        tail = newTail;
    }

    // 14. Rotate right by k nodes
    public void rotateRight(int k) {
        int size = count();
        if (k <= 0 || size == 0) return;
        rotateLeft(size - (k % size));
    }

    // Demo of all operations
    public static void main(String[] args) {
        DoublyLinkedList dl = new DoublyLinkedList();

        // 1. create
        dl.createFromArray(new int[] {1,2,3,4,5});
        System.out.print("Forward: ");
        dl.iterateForward();
        System.out.print("Backward: ");
        dl.iterateBackward();

        // 3. count
        System.out.println("Count: " + dl.count());

        // 4. indexOf
        System.out.println("Index of 3: " + dl.indexOf(3));

        // 5. getNode
        Node n = dl.getNode(2);
        System.out.println("Node at 2: " + (n != null ? n.elem : "out of bounds"));

        // 6. update
        dl.update(1, 10);
        System.out.print("After update: ");
        dl.iterateForward();

        // 7. search
        System.out.println("Search 4: " + dl.search(4));

        // 8. insert
        dl.insert(0, 99);
        dl.insert(3, 77);
        dl.insert(dl.count(), 55);
        System.out.print("After inserts: ");
        dl.iterateForward();

        // 9. remove
        dl.remove(2);
        System.out.print("After remove: ");
        dl.iterateForward();

        // 10. copy
        DoublyLinkedList copy = dl.copy();
        System.out.print("Copied: ");
        copy.iterateForward();

        // 11. reverse out‑of‑place
        DoublyLinkedList rev = dl.reverseOutOfPlace();
        System.out.print("Reversed (new): ");
        rev.iterateForward();

        // 12. reverse in‑place
        dl.reverseInPlace();
        System.out.print("Reversed (in‑place): ");
        dl.iterateForward();

        // 13 & 14. rotate
        dl.rotateLeft(2);
        System.out.print("Rotated left 2: ");
        dl.iterateForward();

        dl.rotateRight(2);
        System.out.print("Rotated right 2: ");
        dl.iterateForward();
    }
}
