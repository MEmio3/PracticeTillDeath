public class DoublyLinkedListPrac 
{
    private Node head;
    private Node tail;
    public class Node 
    {
        int elem;
        Node prev;
        Node next;
        Node(int elem) 
        {
            this.elem = elem;
            this.prev = null;
            this.next = null;
        }
    }
// createFromArray
// iterateForward
// iterateBackward
// count
// indexOf
// getNode
// update
// search
// insert
// remove
// copy
// reverseOutOfPlace
// reverseInPlace
// rotateLeft
// rotateRight
    public void createFromArray(int[] arr) {
        if(arr == null || arr.length == 0) return;
        head = new Node(arr[0]);
        Node current = head;
        for(int i=1; i<arr.length; i++)
        {
            Node newNode = new Node (arr[i]);
            current.next=newNode;
            newNode.prev=current;
            current = newNode;

        }
        tail = current; // last node
    }
    // 2. Iterate forward through the list
    public void iterateForward()
    {
        Node current= head;
        while(current != null)
        {
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
    // 4. Find the index of an element
    public int indexOf(int elem)
    {
        int idx=0;
        Node current = head;
        while (current != null) {
            if (current.elem == elem) return idx;
            current = current.next;
            idx++;
        }
        return -1; // not found
    }
    // 5. Get a node at a specific index
    public Node getNode(int index) {
        int i = 0;
        Node current = head;    
        while (current != null) {
            if (i == index) return current;
            current = current.next;
            i++;
        }
        return null; // index out of bounds
    }
    // 6. Update an element at a specific index
    public boolean update(int index, int newElem) {
        Node nodeToUpdate = getNode(index);
        if (nodeToUpdate != null) {
            nodeToUpdate.elem = newElem;
            return true; // update successful
        } else {
        
            System.out.println("Index out of bounds");
            return false; // update failed
        }        
    }
    // 7. Search for an element
    public boolean search(int elem) {
        Node current = head;
        while (current != null) {
            if (current.elem == elem) return true; // found
            current = current.next;
        }
        return false; // not found
    }
    // 8. Insert a new node at a given index
    public void insert (int index, int elem) {
       if(index <0 || index >count())return;
       Node newNode = new Node(elem);
       if(index == 0)
       {
            // Insert at the beginning
            newNode.next = head;
            if(head != null)
            {
                head.prev = newNode;
            }
            head = newNode;
            if(tail == null)
            {
                tail = newNode; // if list was empty
            }
            return;
       }
       Node prevNode = getNode(index-1);
       if(prevNode != null)
       {
            newNode.next = prevNode.next;
            newNode.prev = prevNode;
            prevNode.next = newNode;
            if(newNode.next != null)
            {
                newNode.next.prev = newNode; // link next node back to new node
            }
            else
            {
                tail = newNode; // if inserted at the end
            }
       }
    }
    // 9. Remove a node at a given index 
    public void remove(int index)
    {
        if( head == null || index <0 || index >= count()) return; // empty list or invalid index
        if(index == 0)
        {
            // Remove head
            head = head.next;
            if(head != null)
            {
                head.prev =null; // new head has no previous
            }
            else
            {
                tail = null; // if list becomes empty
            }
        }
        else
        {
            Node prevNode = getNode(index-1);
            if(prevNode != null && prevNode.next != null)
            {
                Node toRemove = prevNode.next;
                prevNode.next= toRemove.next; // link previous node to next of the node to remove
                if(toRemove.next != null)
                {
                    toRemove.next.prev = prevNode; // link next node back to previous
                }
                else
                {
                    tail = prevNode; // if removed last node, update tail
                }
            } 
        }    
    } 
    // 10. Copy the list (deep copy)
    public DoublyLinkedListPrac copy() {
        DoublyLinkedListPrac newList = new DoublyLinkedListPrac();
        if (head == null) return newList;
        // Copy head
        newList.head = new Node(head.elem);
        Node origCurr = head.next;
        Node copyCurr = newList.head;
        // Copy rest
        while(origCurr != null) 
        {
            copyCurr.next = new Node(origCurr.elem);
            copyCurr.next.prev = copyCurr; // link new node back to current
            copyCurr = copyCurr.next; // move to new node
            origCurr = origCurr.next; // move to next original node
        } 
        newList.tail = copyCurr; // last copied node becomes new tail
        if (newList.tail != null) newList.tail.next = null; // ensure tail's next is null
        if (newList.head != null) newList.head.prev = null; // ensure head's prev is null
        return newList; // return the new copied list
    }
   // 11. Reverse the list (out-of-place)
    public DoublyLinkedListPrac reverseOutOfPlace() {
        DoublyLinkedListPrac reversedList = new DoublyLinkedListPrac();
        if (head == null) return reversedList; // empty list
        // Copy head
        reversedList.head = new Node(head.elem);
        Node origCurr = head.next;
        Node copyCurr = reversedList.head;
        // Copy rest in reverse order
        while (origCurr != null) {
            Node newNode = new Node(origCurr.elem);
            newNode.next = reversedList.head;
            if (reversedList.head != null) reversedList.head.prev = newNode;
            reversedList.head = newNode; // prepend to reversed list
            if (reversedList.tail == null) reversedList.tail = newNode;
            origCurr = origCurr.next; // move to next original node
        }
        return reversedList; // return the new reversed list
    } 
    public void reverseInPlace() {
        if (head == null || head.next == null) return;
        Node current = head;
        Node temp = null;
        tail = head; // update tail to current head
        while (current != null) {
            // Swap next and prev pointers      
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            // Move to the next node (which is now prev)
            current = current.prev; // current.prev is now the next node
        }
        head = temp; // temp is now the new head (last processed node)
        if (head != null) head.prev = null;
        if (tail != null) tail.next = null; // ensure tail's next is null
    }

    // 13. Rotate the list left by k positions
    public void rotateLeft(int k) {
        if (head == null || k <= 0) return;
        int size = count();
        k = k % size;   
        if (k == 0) return; // no rotation needed
        Node newTail = head;
        for (int i = 0; i < k - 1 && newTail != null; i++) {
            newTail = newTail.next; // find the new tail
        }   
        if (newTail == null || newTail.next == null) return; // no rotation needed
        Node newHead = newTail.next; // new head is the next node
        newTail.next = null;
        if (newHead != null) {
            newHead.prev = null; // new head has no previous
        }   
        tail.next = head; // link old tail to old head
        head.prev = tail;
        head = newHead; // update head to new head
        tail = newTail; // update tail to new tail
    }   
    // 14. Rotate the list right by k positions
    public void rotateRight(int k) {
        if (head == null || k <= 0) return;
        int size = count();
        k = k % size;   
        if (k == 0) return;
        Node newTail = head;
        for (int i = 0; i < size - k - 1 && newTail != null; i++) {
            newTail = newTail.next; // find the new tail
        }
        if (newTail == null || newTail.next == null) return; // no rotation needed
        Node newHead = newTail.next;
        tail.next = head; // link old tail to old head
        head.prev = tail;
        newTail.next = null; // new tail's next is null
        if (newHead != null) {
            newHead.prev = null; // new head has no previous
        }
        head = newHead; // update head to new head
        tail = newTail; // update tail to new tail
    }
    // Main method for testing
    public static void main(String[] args) {
        DoublyLinkedListPrac list = new DoublyLinkedListPrac();
        int[] arr = {1, 2, 3, 4, 5};    
        list.createFromArray(arr);
        System.out.println("Iterate Forward:");
        list.iterateForward();
        System.out.println("Iterate Backward:");
        list.iterateBackward();
        System.out.println("Count: " + list.count());
        System.out.println("Index of 3: " + list.indexOf(3));
        System.out.println("Get Node at index 2: " + list.getNode(2).elem);
        list.update(2, 10);
        System.out.println("After updating index 2 to 10:");
        list.iterateForward();
        System.out.println("Search for 10: " + list.search(10));
        list.insert(2, 20);
        System.out.println("After inserting 20 at index 2:");
        list.iterateForward();
        list.remove(2);
        System.out.println("After removing node at index 2:");
        list.iterateForward();
        DoublyLinkedListPrac copiedList = list.copy();
        System.out.println("Copied List:");
        copiedList.iterateForward();
        DoublyLinkedListPrac reversedList = list.reverseOutOfPlace();
        System.out.println("Reversed List (out-of-place):");
        reversedList.iterateForward();
        list.reverseInPlace();
        System.out.println("Reversed List (in-place):");
        list.iterateForward();
        list.rotateLeft(2);
        System.out.println("After rotating left by 2:");
        list.iterateForward();
        list.rotateRight(2);
        System.out.println("After rotating right by 2:");
        list.iterateForward();
    }
    
}
