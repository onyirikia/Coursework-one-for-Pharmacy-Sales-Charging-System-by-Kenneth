import java.util.List;

/**
 * PharmacyApp.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * Menu-driven command-line interface for the pharmacy sales charging system.
 */
public class PharmacyApp {

    private static final String STUDENT_NAME = "Kenneth Aleku";
    private static final String REG_NUMBER = "VU-BIT-2511-0315-EVE";

    private final SaleManager manager = new SaleManager();
    private final InputHelper input = new InputHelper();

    public static void main(String[] args) {
        new PharmacyApp().run();
    }

    private void run() {
        System.out.println("==============================================");
        System.out.println(" PHARMACY SALES CHARGING SYSTEM");
        System.out.println(" Student : " + STUDENT_NAME);
        System.out.println(" Reg No  : " + REG_NUMBER);
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = input.readInt("Enter your choice (1-7): ", 1, 7);
            switch (choice) {
                case 1: addPrescriptionSale(); break;
                case 2: addCounterSale();      break;
                case 3: listReport();          break;
                case 4: findSale();            break;
                case 5: removeSale();          break;
                case 6: showSummary();         break;
                default:
                    System.out.println("Thank you for using the Pharmacy Sales Charging System. Goodbye!");
                    running = false;
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("------------------- MENU -------------------");
        System.out.println(" 1. Add a prescription sale");
        System.out.println(" 2. Add a counter sale");
        System.out.println(" 3. List sales report (sorted by customer name)");
        System.out.println(" 4. Find a sale by ID");
        System.out.println(" 5. Remove a sale");
        System.out.println(" 6. Show summary of charges and discounts");
        System.out.println(" 7. Exit");
        System.out.println("--------------------------------------------");
    }

    // ------------------------------------------------------------------ add

    private void addPrescriptionSale() {
        System.out.println("\n-- New prescription sale (ID " + manager.getNextId() + ") --");
        String name = input.readName("Customer name: ");
        int age = input.readInt("Customer age: ", 0, 120);
        String medicine = input.readText("Medicine name: ");
        int quantity = input.readInt("Quantity: ", 1, 100000);
        double price = input.readPositiveAmount("Unit price (UGX): ");
        String prescription = input.readText("Prescription number: ");
        try {
            Sale sale = new PrescriptionSale(manager.getNextId(), name, age, medicine,
                    quantity, price, prescription);
            manager.addSale(sale);
            printAdded(sale);
        } catch (InvalidSaleException e) {
            System.out.println("Sale not saved: " + e.getMessage());
        }
    }

    private void addCounterSale() {
        System.out.println("\n-- New counter sale (ID " + manager.getNextId() + ") --");
        String name = input.readName("Customer name: ");
        int age = input.readInt("Customer age: ", 0, 120);
        String medicine = input.readText("Medicine name: ");
        int quantity = input.readInt("Quantity: ", 1, 100000);
        double price = input.readPositiveAmount("Unit price (UGX): ");
        try {
            Sale sale = new CounterSale(manager.getNextId(), name, age, medicine, quantity, price);
            manager.addSale(sale);
            printAdded(sale);
        } catch (InvalidSaleException e) {
            System.out.println("Sale not saved: " + e.getMessage());
        }
    }

    private void printAdded(Sale sale) {
        System.out.println("Sale saved. ID: " + sale.getId());
        System.out.printf("Charge: UGX %,.0f%s%n", sale.calculateCharge(),
                sale.isDiscountApplicable()
                        ? String.format(" (includes %.0f%% senior discount of UGX %,.0f)",
                                Sale.DISCOUNT_RATE * 100, sale.getDiscountAmount())
                        : "");
    }

    // ------------------------------------------------------------------ report / find / remove

    private void listReport() {
        List<Sale> report = manager.getSortedReport();
        if (report.isEmpty()) {
            System.out.println("\nNo sales have been recorded yet.");
            return;
        }
        System.out.println("\nSALES REPORT (sorted by customer name)");
        printHeader();
        double reportTotal = 0;
        for (Sale sale : report) {
            Chargeable item = sale;                      // polymorphic: PrescriptionSale or CounterSale rule runs
            System.out.println(sale);
            reportTotal += item.calculateCharge();
        }
        System.out.println("-".repeat(HEADER.length()));
        System.out.printf("Total charges: UGX %,.0f%n", reportTotal);
    }

    private static final String HEADER = String.format(
            "%-11s | %-16s | %-20s | %3s | %-14s | %3s | %10s | %-3s | %10s",
            "ID", "Type", "Customer", "Age", "Medicine", "Qty", "Unit Price", "Dsc", "Charge");

    private void printHeader() {
        System.out.println(HEADER);
        System.out.println("-".repeat(HEADER.length()));
    }

    private void findSale() {
        String id = input.readText("Enter sale ID to find: ");
        try {
            Sale sale = manager.findSale(id);
            System.out.println("Sale found:");
            printHeader();
            System.out.println(sale);
        } catch (SaleNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeSale() {
        String id = input.readText("Enter sale ID to remove: ");
        try {
            Sale sale = manager.findSale(id);
            System.out.println("Sale to remove:");
            printHeader();
            System.out.println(sale);
            if (input.readYesNo("Are you sure you want to remove this sale? (Y/N): ")) {
                manager.removeSale(id);
                System.out.println("Sale " + sale.getId() + " has been removed.");
            } else {
                System.out.println("Removal cancelled. The sale was kept.");
            }
        } catch (SaleNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showSummary() {
        System.out.println("\n------------- SUMMARY -------------");
        System.out.println("Number of sales      : " + manager.getCount());
        System.out.printf("Total charges        : UGX %,.0f%n", manager.getTotalCharges());
        System.out.printf("Total discount given : UGX %,.0f%n", manager.getTotalDiscount());
        System.out.println("-----------------------------------");
    }
}
