/**
 * Sale.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * Abstract base class for every pharmacy sale. It holds the fields shared by
 * all sales and leaves calculateCharge() abstract, so each kind of sale
 * supplies its own pricing rule.
 *
 * Discount rule (personal scenario): A customer aged 60 or over receives a
 * P% discount, where P = B + 5 = 1 + 5 = 6%, on the total of the sale.
 */
public abstract class Sale implements Chargeable {

    /** Discount rate for senior customers: P = B + 5 = 6%. */
    public static final double DISCOUNT_RATE = 0.06;

    /** Minimum age that qualifies a customer for the discount. */
    public static final int DISCOUNT_AGE = 60;

    private static final int MAX_AGE = 120;

    private String id;
    private String customerName;
    private int customerAge;
    private String medicineName;
    private int quantity;
    private double unitPrice;

    protected Sale(String id, String customerName, int customerAge,
                   String medicineName, int quantity, double unitPrice)
            throws InvalidSaleException {
        setId(id);
        setCustomerName(customerName);
        setCustomerAge(customerAge);
        setMedicineName(medicineName);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    // ---------------------------------------------------------------- getters

    public String getId() { return id; }

    public String getCustomerName() { return customerName; }

    public int getCustomerAge() { return customerAge; }

    public String getMedicineName() { return medicineName; }

    public int getQuantity() { return quantity; }

    public double getUnitPrice() { return unitPrice; }

    // ---------------------------------------------------------------- setters (validated)

    public void setId(String id) throws InvalidSaleException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidSaleException("Sale ID cannot be blank.");
        }
        this.id = id.trim().toUpperCase();
    }

    public void setCustomerName(String customerName) throws InvalidSaleException {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidSaleException("Customer name cannot be blank.");
        }
        this.customerName = customerName.trim();
    }

    public void setCustomerAge(int customerAge) throws InvalidSaleException {
        if (customerAge < 0 || customerAge > MAX_AGE) {
            throw new InvalidSaleException(
                    "Customer age must be between 0 and " + MAX_AGE + ".");
        }
        this.customerAge = customerAge;
    }

    public void setMedicineName(String medicineName) throws InvalidSaleException {
        if (medicineName == null || medicineName.trim().isEmpty()) {
            throw new InvalidSaleException("Medicine name cannot be blank.");
        }
        this.medicineName = medicineName.trim();
    }

    public void setQuantity(int quantity) throws InvalidSaleException {
        if (quantity <= 0) {
            throw new InvalidSaleException("Quantity must be at least 1.");
        }
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) throws InvalidSaleException {
        if (Double.isNaN(unitPrice) || Double.isInfinite(unitPrice) || unitPrice <= 0) {
            throw new InvalidSaleException("Unit price must be greater than zero.");
        }
        this.unitPrice = unitPrice;
    }

    // ---------------------------------------------------------------- discount logic

    /** @return true when the customer is 60 or older and so earns the discount. */
    public boolean isDiscountApplicable() {
        return customerAge >= DISCOUNT_AGE;
    }

    /** Reduces an amount by the P% discount when the customer is eligible. */
    protected double applyDiscount(double amount) {
        if (isDiscountApplicable()) {
            return Math.round(amount * (1.0 - DISCOUNT_RATE));
        }
        return amount;
    }

    /** @return the amount of discount given on this sale (0 if not eligible). */
    public double getDiscountAmount() {
        return getGrossAmount() - calculateCharge();
    }

    // ---------------------------------------------------------------- abstract behaviour

    /** @return the total before any discount is taken off. */
    public abstract double getGrossAmount();

    /** @return a short label for the type of sale, used in reports. */
    public abstract String getType();

    @Override
    public abstract double calculateCharge();

    @Override
    public String toString() {
        return String.format("%-11s | %-16s | %-20s | %3d | %-14s | %3d | %10s | %-3s | %10s",
                id, getType(), customerName, customerAge, medicineName, quantity,
                String.format("%,.0f", unitPrice),
                isDiscountApplicable() ? "Yes" : "No",
                String.format("%,.0f", calculateCharge()));
    }
}
