public class PhoneList {
    public  ContactNode head;

    public PhoneList(){
        head = null;
    }

    public void AddContact(String name, String phone) {
        ContactNode newNode = new ContactNode(name, phone);
        if (IsinList(name)) {
            System.out.println("Phone Contact with Name " + name + " already exists");
            System.out.println();
            return;
        }
        if (IsinListP(phone)){
            System.out.println("Phone Contact with PhoneNumber " + phone + " already exists");
            System.out.println();
            return;
        }
        if (head == null) {
            head = newNode;
        } else {
            ContactNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        System.out.println("Contact added successfully.");
        System.out.println();
    }

    public void Display() {
        if (isEmpty()) {
            System.out.println("Empty List");
        } else {
            ContactNode current = head;
            while (current != null) {
                System.out.println("[ Name: " + current.firstName + ", PhoneNumber: " + current.phoneNumber + "]");
                current = current.next;
            }
            System.out.println();
        }
    }

    public void Search(String name) {
        ContactNode current = head;
        boolean found = false;
        if (isEmpty()) {
            System.out.println("No contacts found");
        } else {
            while (current != null) {
                if (current.firstName.equals(name)) {
                    System.out.println("Contact found");
                    System.out.println("[ Name: " + current.firstName + ", PhoneNumber: " + current.phoneNumber + "]");
                    System.out.println();
                    found = true;
                    break;
                }
                current = current.next;
            }
            if (!found) {
                System.out.println("There is no such contact with this name.");
            }
        }
    }

    public void DeleteContent(String name) {
        if (isEmpty()) {
            System.out.println("No contacts found for Deleting");
            System.out.println();
        } else {
            if (head.firstName.equals(name)) {
                head = head.next;
                System.out.println("Contact removed successfully.");
                System.out.println();
                return;
            }

            ContactNode current = head;
            while (current.next != null) {
                if (current.next.firstName.equals(name)) {
                    current.next = current.next.next;
                    System.out.println("Contact removed successfully.");
                    System.out.println();
                    return;
                }
                current = current.next;
            }
            System.out.println("Contact not found.");
            System.out.println();
        }
    }


    public boolean isEmpty(){
        return head == null;
    }

    public boolean IsinList( String name ){
        ContactNode current = head;
        while(current != null){
            if(current.firstName.equals(name)){
                return true;
            }
            current = current.next;
        }
        return false;
    }
    public boolean IsinListP( String phone ){
        ContactNode current = head;
        while(current != null){
            if(current.phoneNumber.equals(phone)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void printMenu(){
        System.out.println("1)-Insert A Contact Name\n2)-Print Menu\n3)-Search Menu\n4)-Delete Menu\nAny Other Button For Exit");
        System.out.println();
    }
}
