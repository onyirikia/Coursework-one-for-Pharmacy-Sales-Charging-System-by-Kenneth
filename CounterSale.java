/**
 * CounterSale.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * An over-the-counter sale that needs no prescription.
 * Charge rule: (quantity x unit price) + 1,000 packaging
 * (less 6% for customers aged 60+).
 */
public class CounterSale extends Sale {

    private static final double PACKAGING_FEE = 1000;

    public CounterSale(String id, String customerName, int customerAge,
                       String medicineName, int quantity, double unitPrice)
            throws InvalidSaleException {
        super(id, customerName, customerAge, medicineName, quantity, unitPrice);
    }

    @Override
    public double getGrossAmount() {
        return getQuantity() * getUnitPrice() + PACKAGING_FEE;
    }

    @Override
    public double calculateCharge() {
        return applyDiscount(getGrossAmount());
    }

    @Override
    public String getType() {
        return "Counter";
    }
}
