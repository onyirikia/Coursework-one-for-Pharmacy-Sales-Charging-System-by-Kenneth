/**
 * PrescriptionSale.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * A sale of medicine supplied against a doctor's prescription.
 * Charge rule: quantity x unit price (less 6% for customers aged 60+).
 */
public class PrescriptionSale extends Sale {

    private String prescriptionNumber;

    public PrescriptionSale(String id, String customerName, int customerAge,
                            String medicineName, int quantity, double unitPrice,
                            String prescriptionNumber) throws InvalidSaleException {
        super(id, customerName, customerAge, medicineName, quantity, unitPrice);
        setPrescriptionNumber(prescriptionNumber);
    }

    public String getPrescriptionNumber() { return prescriptionNumber; }

    public void setPrescriptionNumber(String prescriptionNumber) throws InvalidSaleException {
        if (prescriptionNumber == null || prescriptionNumber.trim().isEmpty()) {
            throw new InvalidSaleException("Prescription number cannot be blank.");
        }
        this.prescriptionNumber = prescriptionNumber.trim();
    }

    @Override
    public double getGrossAmount() {
        return getQuantity() * getUnitPrice();
    }

    @Override
    public double calculateCharge() {
        return applyDiscount(getGrossAmount());
    }

    @Override
    public String getType() {
        return "Prescription";
    }
}
