public class SInglyLinkedListPrac 
{
    private Node head;

    // Node class
    private static class Node 
    {
        int elem;
        Node next;
        Node(int elem) {
            this.elem = elem;
            this.next = null;
        }
    }   
    //Create a Linked List from an array
    public void CreateFromArray(int[] arr) 
    {
        if(arr == null || arr.length == 0 ) return;
        head = new Node(arr[0]);
        Node current=head;
        for( int i = 1; i< arr.length; i++)
        {
            current.next =new Node(arr[i]);
            current = current.next;
        }
    }
    // Iteration of the linked list
    public void iterate()
    {
        Node current = head;
        while (current != null)
        {
            System.out.println(current.elem +" -> ");
            current = current.next;
        }
    } 
    // Count the items in the linked list
    public int count()
    {
        int count = 0;
        Node current= head;
        while(current!= null)
        {
            count++;
            current = current.next;
        }
        return count;
    }
    // Retrieve index of an element
    public int indexOf(int elem)
    {   
        int index=0;
        Node current =head;
        while(current != null)
        {
            if(current.elem==elem)
            {
                return index;
            }
            index++;
            current = current.next;
        }
        return -1; // Element not found
    }
    //get Node at index
    public Node getNode(int index)
    {
        if(index<0 || index >= count())
            {
                return null; // Invalid index
            }
        Node current = head;;
        int currentIndex=0;
        for (int i = 0; i<index && current != null; i++)
        {
                if(currentIndex == index)
                {
                    return current;
                }
                current = current.next;
                currentIndex++;
        }
            return null; // Invalid index
    }
    public boolean isEmpty()
    {
        if(head == null)
        {
            return true; // List is empty
        }
        return false; // List is not empty
    }
    //update value in a specific index
    public boolean update(int index, int newValue) 
    {
        Node node = getNode(index);
        if(node!=null)
        {
            node.elem=newValue;
            return true; // Update successful
        }
        return false; // Index out of bounds
    }
    // Search for an element in the list
    public boolean search (int elem)
    {
        Node current = head;
        while (current != null)
        {
            if(current.elem ==elem)
            {
                return true; // Element found
            }
        }
        return false; // Element not found     
    }
    //insert at the end of the list
    public void insertAtEnd(int elem)
    {
      Node newNode = new Node(elem);
      if(head == null) 
        {
          head = newNode;
          newNode.next = null;
        }
        else
        {
            Node current = head;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = newNode;
            newNode.next = null;
        }
    }
    // Insert a node in the list at a specific index
    public void insert(int index, int elem) 
    {
        Node newNode = new Node(elem);
        if(index < 0 || index > count())return;
        if(index == 0)
        {
            newNode.next = head;
            head = newNode;
            return;
        }
        Node current = head;
        int currentIndex = 0;
        while (current != null && currentIndex < index - 1)
        {   
            current = current.next;
            currentIndex++;
        }
        if (current != null)
        {
            newNode.next = current.next;
            current.next = newNode;
        }

    }
    //insert at the beginning of the list
    public void insertAtBeginning(int elem) 
    {
        Node newNode = new Node(elem);
        if (head == null) 
        {
            head = newNode;
        }
        else
        {
            newNode.next = head;
            head = newNode;
        }
    }
    // Remove a node from the list at a specific index
    public void remove(int index)
    {
        if(index <0 || index >= count()) return;
        if(index == 0 && head != null)
        {
            head = head.next; // Remove from the beginning
            return;
        }
        Node current = head;
        int currentIndex = 0;
        while (current != null &&  currentIndex < index -1)
        {
            current = current.next;
            currentIndex++;
        }
        if(current != null && current.next != null)
        {
            current.next = current.next.next; // Remove from the middle or end
        }
    }
    // Clear the linked list
    public void clear() 
    {
        head = null; // Set head to null to clear the list  
    }
    // 10. Copying a linked list
    public SInglyLinkedListPrac copy() 
    {
        SInglyLinkedListPrac newList = new SInglyLinkedListPrac();
        if(head == null) return newList; // If the list is empty, return an empty list
        Node current = head;
        newList.head = new Node(head.elem);
        Node newCurrent = newList.head; 
        while (current != null)
        {
            newCurrent.next = new Node(current.elem);
            current = current.next;
            newCurrent = newCurrent.next;
        }
        return newList; // Return the copied list
    }    

    //reverse the linked list
    public SInglyLinkedListPrac reverseOutOfPlace()
    {
        SInglyLinkedListPrac reversedList = new SInglyLinkedListPrac();
        Node current = head;
        while (current != null)
        {
            Node newNode = new Node(current.elem);
            newNode.next = reversedList.head;
            reversedList.head = newNode; // Insert at the beginning of the new list
            current = current.next;
        }
        return reversedList; // Return the reversed list
    }
    // 11. Out-of-place reverse of a linked list
    public SInglyLinkedListPrac reverseOutOfPlaceUsingArray() 
    {
        int size = count();
        int[] arr = new int[size];
        Node current = head;    
        for (int i = 0; i < size; i++)
        {
            arr[i] = current.elem;
            current = current.next;
        }   
        // Reverse the array
        for (int i = 0, j = size - 1; i < j; i++, j--)
        {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        SInglyLinkedListPrac reversedList = new SInglyLinkedListPrac();
        reversedList.CreateFromArray(arr); // Create a new list from the reversed array
        return reversedList; // Return the reversed list
    }
    // 12. Reverse the linked list in place
    public void reverseInPlace()
    {
        Node prev = null;
        Node current = head;
        Node next = null;       
        while (current != null)
        {
            next = current.next; // Store the next node
            current.next = prev;    // Reverse the link
            prev = current;         // Move prev to current
            current = next;         // Move to the next node
        }   
        head = prev; // Update head to the new first node
    }
    public void reverseinplace()
    {
        Node prev = null;
        Node current = head;
        Node next = null;
        while (current != null)
        {
            next = current.next;
            current.next= prev;
            prev = current;
            current = next;
        }
    }
    public void rotateLeft(int k )
    {
        if (head == null || k<= 0)
            return; // No rotation needed if the list is empty or k is non-positive
        int size = count();
        k = k % size; // Handle cases where k is greater than the size of the list
        if (k == 0) return; // No rotation needed if k is 0 after modulo operation

        Node current = head;
        Node tail = null;
        int count = 1;
        while (current.next != null) {
            tail = current; // Keep track of the last node
            current = current.next;
            count++;
        }
        tail.next = head; // Connect the tail to the head to make it circular
        int newTailIndex = size - k - 1;    
        Node newTail = head;
        for (int i = 0; i < newTailIndex; i++) {
            newTail = newTail.next; // Find the new tail node
        }
        head = newTail.next; // Update head to the new head after rotation
        newTail.next = null; // Break the circular link to make it a proper list
        // Set the next of the new tail to null to end the list
        tail.next = null; // Ensure the old tail's next is null
        // to avoid circular references
        // Ensure the old tail's next is null to avoid circular references
        // Set the next of the new tail to null to end the list
        
    }
        public void rotateRight(int k)
    {
        if(head == null || head.next == null || k <= 0) return;
        Node current = head;
        int size = count();
        k = k % count(); // Handle rotations greater than size
        if (k == 0) return; // No rotation needed if k is 0
        for(int i = 1; i < size - k; i++) 
        {
            current = current.next;
        }
        Node newHead =current.next; // This will be the new head after rotation
        current.next = null; // Break the link to make it a proper list
        Node tail = newHead;
        while (tail.next != null)
        {
            tail = tail.next; // Find the tail of the new head
        }
        tail.next = head;
        head = newHead; // Update head to the new head
    }
   
    public static void main(String[] args) {

    SInglyLinkedListPrac list = new SInglyLinkedListPrac();

    // 1. Create a linked list from an array
    int[] arr = {1, 2, 3, 4, 5};
    list.CreateFromArray(arr);
    System.out.println("Original List:");
    list.iterate();

    // 2. Iterate through the linked list
    System.out.println("Iterating through the list:");
    list.iterate();

    // 3. Count the items in the linked list
    System.out.println("Count of items in the list: " + list.count());

    // 4. Retrieve index of an element
    int elemToFind = 3;
    System.out.println("Index of element " + elemToFind + ": " + list.indexOf(elemToFind));

    // 5. Retrieve a node from an index
    int indexToRetrieve = 2;
    Node node = list.getNode(indexToRetrieve);
    System.out.println("Element at index " + indexToRetrieve + ": " + (node != null ? node.elem : "Index out of bounds"));

    // 6. Update value at a specific index
    int indexToUpdate = 1;
    int newValue = 10;
    if (list.update(indexToUpdate, newValue)) {
        System.out.println("Updated value at index " + indexToUpdate + " to " + newValue);
        list.iterate();
    } else {
        System.out.println("Failed to update index " + indexToUpdate);
    }

    // 7. Search for an element in the list
    int searchElement = 4;
    System.out.println("Element " + searchElement + " found: " + list.search(searchElement));

    // 8. Insert a node in the list
    System.out.println("Inserting 99 at index 0:");
    list.insert(0, 99); // Insert at the beginning
    list.iterate();

    System.out.println("Inserting 77 at index 3:");
    list.insert(3, 77); // Insert in the middle
    list.iterate();

    System.out.println("Inserting 55 at the end:");
    list.insert(list.count(), 55); // Insert at the end
    list.iterate();

    // 9. Remove a node from the list
    System.out.println("Removing the node at index 2:");
    list.remove(2); // Remove from the middle
    list.iterate();

    // 10. Copying the linked list
    SInglyLinkedListPrac copiedList = list.copy();
    System.out.println("Copied list:");
    copiedList.iterate();

    // 11. Out-of-place reverse of a linked list
    SInglyLinkedListPrac reversedList = list.reverseOutOfPlace();
    System.out.println("Reversed list (out of place):");
    reversedList.iterate();

    // 12. In-place reverse of a linked list
    list.reverseInPlace();
    System.out.println("Reversed list (in place):");
    list.iterate();

    // 13. Rotating the list left
    System.out.println("Rotating the list left by 2 positions:");
    list.rotateLeft(2);
    list.iterate();

    // 14. Rotating the list right
    System.out.println("Rotating the list right by 2 positions:");
    list.rotateRight(2);
    list.iterate();
}

    

}
