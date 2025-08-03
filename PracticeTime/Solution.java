public class Solution 
{   
    public class Node {
        int elem;
        Node next;  
        Node(int elem) {
            this.elem = elem;
            this.next = null;
        }
    }
    private Node head;
    public void rotateLeft(int k) 
    {
        if (head == null || head.next == null || k <= 0) return;
        Node current = head;
        int length = 1;
        Node tail = null;

        // Find the length of the list and the tail node
        while (current.next != null)
        {   
            tail=current;
            current= current.next;
            length++;
        }
        Node newHead = current.next; // This will be the new head after rotation
        while (tail.next != null)
        {
        tail= tail.next;
        }
        tail.next = head;
        head = newHead; // Update head to the new head
    }
    public void rotateRight(int k)
    {
        if(head == null || head.next == null || k <= 0) return;
        Node current = head;
        k = k % count(); // Handle rotations greater than size
        if (k == 0) return; // No rotation needed if k is 0
        for(int i = 1; i < k; i++) 
        {
            current = current.next;
        }
    }
}
