import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Deque;
/**
 * StockTracker is a basic application to help track stock purchases and sales.
 * It lets users buy and sell shares, and calculates capital gains.
 * 
 * 
 * @author Selena Persaud
 */
public class StockTracker {
    /** 
     * Keeps track of stock lots we've purchased.
     */
    private static Deque<StockLot> ledger;

    /** 
     * Tracks total money made (or lost) from selling stocks.
     */
    private static double totalRealizedCapitalGain;

    private static Scanner scanner;

    public static void main(String[] args) {
        ledger = new ArrayDeque<>();
        totalRealizedCapitalGain = 0.0;
        scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    buyShares();
                    break;
                case 2:
                    sellShares();
                    break;
                case 3:
                    displayTotalCapitalGain();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting Stock Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the main menu options for the user.
     * Prrints out the choices.
     */
    private static void displayMenu() {
        System.out.println("Stock Tracker Menu");
        System.out.println("1. Buy Shares");
        System.out.println("2. Sell Shares");
        System.out.println("3. Total Realized Capital Gain");
        System.out.println("4. Quit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Gets the user's menu choice.
     * 
     * @return The menu choice as an intege or -1 if input is invalid
     */
    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Sorry, that didn't work! Try again.");
            scanner.nextLine();
            return -1;
        }
    }

    /**
     * Handles the process of buying shares.
     * Adds a new stock lot to the ledger.
     * 
     */
    private static void buyShares() {
        System.out.print("Enter number of shares to buy: ");
        int shares = scanner.nextInt();

        System.out.print("Enter purchase price per share: $");
        double price = scanner.nextDouble();

        StockLot newLot = new StockLot(shares, price);
        ledger.addLast(newLot);

        System.out.printf("Purchased %d shares at $%.2f per share.%n", shares, price);
    }

    /**
     * Handles selling shares and calculating capital gains.
     * Uses FIFO  method for selling shares.
     * 
     */
    private static void sellShares() {
        if (ledger.isEmpty()) {
            System.out.println("No shares available to sell.");
            return;
        }

        System.out.print("Enter number of shares to sell: ");
        int sharesToSell = scanner.nextInt();

        System.out.print("Enter selling price per share: $");
        double sellPrice = scanner.nextDouble();

        double totalCapitalGain = calculateCapitalGain(sharesToSell, sellPrice);

        System.out.printf("Sold %d shares at $%.2f per share.%n", sharesToSell, sellPrice);
        System.out.printf("Capital Gain: $%.2f%n", totalCapitalGain);

        totalRealizedCapitalGain += totalCapitalGain;
    }

    /**
     * Calculates capital gains when selling shares.
     * 
     * @param sharesToSell Number of shares being sold
     * @param sellPrice Price per share at time of sale
     * @return Total capital gain from the sale
     */
    private static double calculateCapitalGain(int sharesToSell, double sellPrice) {
        double totalCapitalGain = 0.0;
        int remainingToSell = sharesToSell;

        while (remainingToSell > 0 && !ledger.isEmpty()) {
            StockLot currentLot = ledger.peekFirst();
            int sharesInLot = currentLot.getShares();
            double buyPrice = currentLot.getBuyPrice();

            int sharesToTakeFromLot = Math.min(sharesInLot, remainingToSell);

            double capitalGainForLot = (sellPrice - buyPrice) * sharesToTakeFromLot;
            totalCapitalGain += capitalGainForLot;

            if (sharesToTakeFromLot == sharesInLot) {
                ledger.removeFirst();
            } else {
                currentLot.setShares(sharesInLot - sharesToTakeFromLot);
            }

            remainingToSell -= sharesToTakeFromLot;
        }

        if (remainingToSell > 0) {
            System.out.println("Warning: Not enough shares to complete full sale!");
        }

        return totalCapitalGain;
    }

    /**
     * Displays the total realized capital gain.
     * Shows how much money we've made (or lost) from stock sales.
     */
    private static void displayTotalCapitalGain() {
        System.out.printf("Total Realized Capital Gain: $%.2f%n", totalRealizedCapitalGain);
    }
}
