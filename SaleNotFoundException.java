/**
 * SaleNotFoundException.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * Thrown when a sale with the requested ID does not exist in the pharmacy records.
 */
public class SaleNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public SaleNotFoundException(String saleId) {
        super("No sale record was found with ID: " + saleId);
    }
}
