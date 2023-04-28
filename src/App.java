import java.util.Scanner;

public class App {
    static String customerName;
    static int priceTotal = 0;
    static String itemNames[] = {"Classic Hamburger", "Cheeseburger", "Crispy Chicken Sandwich", "Chicken Nuggets (6 pieces)", 
                        "French Fries (regular)", "Onion Rings (regular)", "Caesar Salad", "Soft Drink (regular)", "Chocolate Milkshake", "Vanilla Ice Cream"}; 
    static int itemPrices[] = {249, 274, 299, 199, 124, 149, 249, 99, 199, 74};
    static int orderList[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static String paymentMethods[] = {"GCash", "Cash"};

    static void threadSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }    

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    static void getName() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        customerName = scan.nextLine();
        listMenu();
    }

    static void listMenu() {
        clearScreen();
        System.out.println("Welcome, " + customerName);
        System.out.println("Select your desired items in our menu below\n");

        for(int i = 0; i < 10; i++) {
            System.out.println(i+1 + " - " + itemNames[i] + " - " + itemPrices[i] + " Pesos");
        }
        listCart();
        getInput();
    }

    static void listCart() {
        System.out.println("\nCart: ");
        for(int i = 0; i < 10; i++) {
            if(orderList[i] > 0){
                System.out.println(orderList[i] + "x " + itemNames[i] + " = " + itemPrices[i]*orderList[i] + " Pesos");
            }
        }
        System.out.println("\nSubtotal: " + priceTotal + " Pesos");
    }

    static void getInput() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your selection (1-10, clear or checkout): ");
        String choicePre = scan.nextLine();
        try {
            int choice = Integer.parseInt(choicePre);
            choice -= 1;
            System.out.print("Enter desired quantity: ");
            int quantity = scan.nextInt();
            addItem(choice, quantity);
        } catch(Exception e) {
            if("checkout".equals(choicePre)){
                checkOut();
            } else if("clear".equals(choicePre)){
                clearCart();
            } else {
                System.out.println("Invalid Selection!");
                threadSleep(2000);
                listMenu();
            }
        }
    }

    static void addItem(int choice, int quantity) {
        priceTotal += itemPrices[choice] * quantity;
        orderList[choice] += quantity;

        listMenu();
    }

    static void clearCart() {
        priceTotal = 0;
        for(int i = 0; i < 10; i++){
            orderList[i] = 0;
        }

        listMenu();
    }
    
    static void checkOut() {
        Scanner scan = new Scanner(System.in);
        clearScreen();
        System.out.println("Checkout Page");
        listCart();
        System.out.println("\nAvailable Payment Methods:");
        for(int i = 0; i < 2; i++){
            System.out.println(i+1 + " - " + paymentMethods[i]);
        }
        System.out.print("Enter your desired payment method: ");
        int choice = scan.nextInt();
        if(choice == 1){
            checkOutGCash();
        } else if(choice == 2){
            checkOutCash();
        } else {
            System.out.println("Invalid Selection!");
            threadSleep(2000);
            checkOut();
        }

        System.out.println("\nThank you for your order.\nPlease wait until your order is ready");
    }

    static void checkOutGCash() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please send the exact amount of " + priceTotal + " Pesos to '0997 940 5620'");
        System.out.print("Enter the reference number in the receipt to verify transaction: ");
        long refNum = Long.parseLong(scan.nextLine());
        System.out.println("Verifying transcation...");
        threadSleep(4000);
        System.out.println("Transaction verified");
    }

    static void checkOutCash() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter cash amount: ");
        int cash = scan.nextInt();
        int change = cash - priceTotal;
        System.out.println("Change: " + change);
    }

    public static void main(String[] args) throws Exception {
        getName();
    }
}
