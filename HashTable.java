public class HashTable {
    // ---- bucket node ----
    // Represents one key-value pair in a bucket's linked list
    private static class Node {
        String key;    // the key
        String value;  // the associated value
        Node next;     // pointer to the next node in this bucket

        // Constructor: initialize key/value and null next-pointer
        Node(String k, String v) {
            this.key   = k;
            this.value = v;
            this.next  = null;
        }
    }

    private Node[] table;   // array of bucket heads
    private int size;       // total number of buckets

    // Constructor: create a hashtable with the given number of buckets
    public HashTable(int size) {
        this.size  = size;
        this.table = new Node[size]; // all buckets start out null
    }

    // Compute bucket index for a key
    private int hash(String key) {
        // 1) key.hashCode() might be negative, so take absolute value
        // 2) mod by table size to map into [0 .. size-1]
        return Math.abs(key.hashCode()) % size;
    }

    // Insert or update a key-value pair
    public void put(String key, String value) {
        int idx       = hash(key);     // which bucket to use
        Node current  = table[idx];    // head of that bucket's chain

        // 1) Traverse the chain looking for an existing key
        while (current != null) {
            if (current.key.equals(key)) {
                // Key found → update its value and exit
                current.value = value;
                return;
            }
            current = current.next;    // move to next node
        }

        // 2) Key not found → create and insert new node at front
        Node newNode   = new Node(key, value);
        newNode.next   = table[idx];  // link to old head
        table[idx]     = newNode;     // update bucket head
    }

    // Retrieve the value for a given key
    public String get(String key) {
        int idx      = hash(key);      // bucket index
        Node current = table[idx];     // start of chain

        // Walk the chain looking for the key
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;  // found → return it
            }
            current = current.next;    // otherwise keep going
        }
        return null;                  // not found → return null
    }

    // Print all buckets and their contents
    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.print("Bucket " + i + ": ");
            Node current = table[i];
            // Walk each chain and print key:value pairs
            while (current != null) {
                System.out.print("[" + current.key + ":" + current.value + "] ");
                current = current.next;
            }
            System.out.println("null"); // end of this bucket
        }
    }

    // Quick demo
    public static void main(String[] args) {
        HashTable ht = new HashTable(5);

        // 1) Insert some pairs
        ht.put("apple",  "red");
        ht.put("banana", "yellow");
        ht.put("orange", "orange");

        // 2) Update an existing key
        ht.put("banana", "green"); // overwrites the old "yellow"

        // 3) Show the internal buckets
        ht.display();

        // 4) Retrieve and print single values
        System.out.println("apple -> " + ht.get("apple")); // prints "red"
        System.out.println("pear  -> " + ht.get("pear"));  // prints "null" (not found)
    }
}
