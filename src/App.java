import java.util.Scanner;

public class App {
    static String customerName;
    static int priceTotal = 0;
    static String itemNames[] = {"Classic Hamburger", "Cheeseburger", "Crispy Chicken Sandwich", "Chicken Nuggets (6 pieces)", 
                        "French Fries (regular)", "Onion Rings (regular)", "Caesar Salad", "Soft Drink (regular)", "Chocolate Milkshake", "Vanilla Ice Cream"}; 
    static int itemPrices[] = {249, 274, 299, 199, 124, 149, 249, 99, 199, 74};
    static int orderList[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static String paymentMethods[] = {"GCash", "PayMaya", "Cash"};
    static String discountCodes[] = {"DUMMY50", "EVERYTHINGFREE"};
    static boolean isCoupon;

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
        showTitle();
        System.out.print("Please enter your name: ");
        customerName = scan.nextLine();
        listMenu();
    }

    static void showTitle() {
        System.out.println("\u001B[35m");
        System.out.println("  _____                                  _       _____           _        ");
        System.out.println(" |  __ \\                                ( )     |  __ \\         | |       ");
        System.out.println(" | |  | |_   _ _ __ ___  _ __ ___  _   _|/ ___  | |__) |___  ___| |_ ___  ");
        System.out.println(" | |  | | | | | '_ ` _ \\| '_ ` _ \\| | | | / __| |  _  // _ \\/ __| __/ _ \\ ");
        System.out.println(" | |__| | |_| | | | | | | | | | | | |_| | \\__ \\ | | \\ \\  __/\\__ \\ || (_) |");
        System.out.println(" |_____/ \\__,_|_| |_| |_|_| |_| |_|\\__, | |___/ |_|  \\_\\___||___/\\__\\___/ ");
        System.out.println("                                    __/ |                                 ");
        System.out.println("                                   |___/                                  ");
        System.out.println("\u001B[37m");
    }

    static void listMenu() {
        clearScreen();
        showTitle();
        System.out.println("Welcome, " + customerName);
        System.out.println("Select your desired items in our menu below\n");

        for(int i = 0; i < 10; i++) {
            System.out.println(i+1 + " - " + itemNames[i] + " - " + itemPrices[i] + " Pesos");
        }
        listCart(false);
        getInput();
    }

    static void listCart(boolean isCheckout) {
        System.out.println("\nCart: ");
        for(int i = 0; i < 10; i++) {
            if(orderList[i] > 0){
                System.out.println(orderList[i] + "x " + itemNames[i] + " = " + itemPrices[i]*orderList[i] + " Pesos");
            }
        }
        if(isCheckout == false){
            System.out.println("\nSubtotal: " + priceTotal + " Pesos");
        } else {
            System.out.println("\nTotal: " + priceTotal + " Pesos");
        }
        if(isCoupon == true){
            System.out.println("COUPON ACTIVATED!");
        }
    }

    static void getInput() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your selection (1-10) \ntype \"clear\" to clear cart \ntype \"menu\" to change name \ntype \"checkout\" to finish order: ");
        String choicePre = scan.next();
        try {
            int choice = Integer.parseInt(choicePre);
            choice--;
            System.out.print("Enter desired quantity: ");
            int quantity = scan.nextInt();
            addItem(choice, quantity);
        } catch(Exception e) {
            if("clear".equalsIgnoreCase(choicePre)){
                clearCart();
            } else if("checkout".equalsIgnoreCase(choicePre)){
                checkOut();
                selectPayment();
            } else if("menu".equalsIgnoreCase(choicePre)){
                clearScreen();
                getName();
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
        showTitle();
        System.out.println("Checkout Page");
        listCart(true);
        if(priceTotal == 0 && isCoupon == false){
            System.out.println("There is nothing in the cart. Returning... ");
            threadSleep(3000);
            listMenu();
        }
    }

    static void selectPayment() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nAvailable Payment Methods:");
        for(int i = 0; i < 3; i++){
            System.out.println(i+1 + " - " + paymentMethods[i]);
        }
        System.out.print("Enter your desired payment method (1 - 3)\ntype \"return\" to go back \ntype \"coupon\" if you want to use a coupon: ");
        String choicePre = scan.next();
        try{
            int choice = Integer.parseInt(choicePre);
            if(choice == 1){
                clearScreen();
                showTitle();
                listCart(true);
                checkOutGCash();
            } else if(choice == 2){
                clearScreen();
                showTitle();
                listCart(true);
                checkOutMaya();
            } else if(choice == 3){
                clearScreen();
                showTitle();
                listCart(true);
                checkOutCash();
            } else {
                System.out.println("Invalid Selection!");
                threadSleep(2000);
                checkOut();
                selectPayment();
            }
        } catch(Exception e){
            if("return".equalsIgnoreCase(choicePre)){
                listMenu();
            } else if("coupon".equalsIgnoreCase(choicePre)){
                addCoupon();
            } else {
                System.out.println("Invalid Selection!");
                threadSleep(2000);
                checkOut();
                selectPayment();
            }

        System.out.println("\nThank you for your order.\nPlease wait until your order is ready");
        threadSleep(3000);
        isCoupon = false;
        priceTotal = 0;
        for(int i = 0; i < 10; i++){
            orderList[i] = 0;
            }
            
        System.out.print("\nReturning to main menu in: ");
        for(int i = 5; i > 0; i--){
            System.out.print(i);
            threadSleep(1000);
            System.out.print("\033[1D\033[0K");
            }
        clearScreen();
        getName();
        }    
    }

    static void addCoupon() {
        Scanner scan = new Scanner(System.in);
        checkOut();
        if(isCoupon == true){
            System.out.println("You can't activate more than 1 coupon at a time.");
            threadSleep(4000);
            checkOut();
            selectPayment();
        }
        System.out.print("Please enter your coupon code: ");
        String inputCoupon = scan.next();
        
        if(discountCodes[0].equals(inputCoupon)){
            System.out.println("Congrats, your order is 50% off!");
            int tempPriceTotal = priceTotal;
            tempPriceTotal = tempPriceTotal / 2;
            priceTotal = tempPriceTotal;
            isCoupon = true;
        } else if(discountCodes[1].equals(inputCoupon)){
            System.out.println("Congrats, your entire order is free of charge!");
            priceTotal = 0;
            isCoupon = true;
        } else {
            System.out.println("Invalid Coupon!");
            threadSleep(4000);
            addCoupon();
        }
        threadSleep(4000);
        checkOut();
        selectPayment();
    }

    static void checkOutGCash() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nGCash Payment:");
        System.out.println("Please send the exact amount of " + priceTotal + " Pesos to '0912 345 6789'");
        System.out.print("Enter the reference number in the receipt to verify transaction: ");
        long refNum = Long.parseLong(scan.nextLine());
        System.out.println("Verifying transcation...");
        threadSleep(4000);
        System.out.println("Transaction verified");
    }

    static void checkOutMaya() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nPayMaya Payment:");
        System.out.println("Please send the exact amount of " + priceTotal + " Pesos to '0912 345 6789'");
        System.out.print("Enter the reference number in the receipt to verify transaction: ");
        long refNum = Long.parseLong(scan.nextLine());
        System.out.println("Verifying transcation...");
        threadSleep(4000);
        System.out.println("Transaction verified");
    }

    static void checkOutCash() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nCash Payment:");
        System.out.print("Enter cash amount: ");
        int cash = scan.nextInt();
        if(cash < priceTotal){
            System.out.println("Please pay the correct amount!");
            threadSleep(5000);
            checkOut();
            selectPayment();
        }
        int change = cash - priceTotal;
        System.out.println("Change: " + change);
    }

    public static void main(String[] args) throws Exception {
        clearScreen();
        getName();
    }
}
