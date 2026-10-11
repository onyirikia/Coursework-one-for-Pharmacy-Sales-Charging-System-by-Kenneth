/**
 * Chargeable.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * Contract for any record that can work out what a customer owes.
 */
public interface Chargeable {

    /**
     * Calculates the final amount payable in Uganda shillings
     * (after any discount has been applied).
     *
     * @return the amount payable
     */
    double calculateCharge();
}
