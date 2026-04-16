import javax.swing.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner Lev = new Scanner(System.in);
        PhoneList list = new PhoneList();
        System.out.println("Welcome to the Phone List By Levi Ackerman");
        System.out.println("Check Our Available Services !");
        while(true) {
            list.printMenu();
            int choice = Lev.nextInt();
            if (choice == 1) {
                System.out.print("Enter Name: ");
                String name = Lev.next();
                System.out.print("Enter Phone Number: ");
                String phone = Lev.next();
                list.AddContact(name, phone);
                System.out.println("Do you Need Another Service ? (Y/N)");
                char h = Lev.next().charAt(0);
                if (h == 'Y' || h == 'y') {
                    continue;
                }else{
                    System.out.println("Thank you for using our service");
                    break;
                }
            }else if (choice == 2) {
                list.Display();
                System.out.println("Do you Need Another Service ? (Y/N)");
                char h = Lev.next().charAt(0);
                if (h == 'Y' || h == 'y') {
                    continue;
                }else{
                    System.out.println("Thank you for using our service");
                    break;
                }
            }else if (choice == 3) {
                System.out.print("Enter Name for Search: ");
                String name = Lev.next();
                list.Search(name);
                System.out.println("Do you Need Another Service ? (Y/N)");
                char h = Lev.next().charAt(0);
                if (h == 'Y' || h == 'y') {
                    continue;
                }else{
                    System.out.println("Thank you for using our service");
                    break;
                }
            }else if (choice == 4) {
                System.out.print("Enter Name For Delete: ");
                String name = Lev.next();
                list.DeleteContent(name);
                System.out.println("Do you Need Another Service ? (Y/N)");
                char h = Lev.next().charAt(0);
                if (h == 'Y' || h == 'y') {
                    continue;
                }else{
                    System.out.println("Thank you for using our service");
                    break;
                }
            }else{
                System.out.println("Thank you for using our service");
                break;
            }
        }
    }
}