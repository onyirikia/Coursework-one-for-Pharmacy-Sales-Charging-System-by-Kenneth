/**
 * ValidationDemo.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * Small stand-alone check showing that the constructors and setters of the
 * model classes reject invalid values by throwing InvalidSaleException.
 * Run with:  java ValidationDemo
 */
public class ValidationDemo {

    public static void main(String[] args) {
        System.out.println("Validation demo - " + "Kenneth Aleku, VU-BIT-2511-0315-EVE");

        attempt("Blank customer name",
                () -> new CounterSale("KA315-001", "   ", 30, "Panadol", 2, 2000));
        attempt("Negative quantity",
                () -> new CounterSale("KA315-001", "Alice", 30, "Panadol", -3, 2000));
        attempt("Zero unit price",
                () -> new PrescriptionSale("KA315-001", "Alice", 30, "Insulin", 1, 0, "RX-1"));
        attempt("Age of 200",
                () -> new CounterSale("KA315-001", "Alice", 200, "Panadol", 1, 2000));
        attempt("Blank prescription number",
                () -> new PrescriptionSale("KA315-001", "Alice", 30, "Insulin", 1, 5000, " "));
        attempt("Setter: negative quantity on a valid sale", () -> {
            Sale sale = new CounterSale("KA315-001", "Alice", 30, "Panadol", 2, 2000);
            sale.setQuantity(-1);
        });
    }

    @FunctionalInterface
    private interface Action {
        void run() throws InvalidSaleException;
    }

    private static void attempt(String label, Action action) {
        try {
            action.run();
            System.out.println(label + " -> ACCEPTED (unexpected)");
        } catch (InvalidSaleException e) {
            System.out.println(label + " -> InvalidSaleException: " + e.getMessage());
        }
    }
}
