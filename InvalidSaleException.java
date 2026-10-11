/**
 * InvalidSaleException.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * Thrown when a pharmacy sale is created or modified with an invalid value
 * (blank name, negative quantity, impossible age, and so on).
 */
public class InvalidSaleException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidSaleException(String message) {
        super(message);
    }
}
