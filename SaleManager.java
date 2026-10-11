import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * SaleManager.java
 * Author: Kenneth Aleku | Reg No: VU-BIT-2511-0315-EVE
 *
 * Stores all sales in an ArrayList and provides add, find, remove, totals and
 * the sorted polymorphic report.
 */
public class SaleManager {

    /** Every ID starts with the initials KA and the last three digits 315. */
    private static final String ID_PREFIX = "KA315-";

    private final List<Sale> sales = new ArrayList<>();
    private int nextNumber = 1;

    /** @return the ID that the next new sale should use, e.g. KA315-001. */
    public String getNextId() {
        return String.format("%s%03d", ID_PREFIX, nextNumber);
    }

    public void addSale(Sale sale) throws InvalidSaleException {
        if (sale == null) {
            throw new InvalidSaleException("Cannot add an empty sale record.");
        }
        for (Sale existing : sales) {
            if (existing.getId().equalsIgnoreCase(sale.getId())) {
                throw new InvalidSaleException("A sale with ID " + sale.getId() + " already exists.");
            }
        }
        sales.add(sale);
        nextNumber++;
    }

    public Sale findSale(String id) throws SaleNotFoundException {
        if (id != null) {
            for (Sale sale : sales) {
                if (sale.getId().equalsIgnoreCase(id.trim())) {
                    return sale;
                }
            }
        }
        throw new SaleNotFoundException(id);
    }

    public void removeSale(String id) throws SaleNotFoundException {
        sales.remove(findSale(id));
    }

    public int getCount() {
        return sales.size();
    }

    /** Polymorphic: each element's own calculateCharge() is called. */
    public double getTotalCharges() {
        double total = 0;
        for (Chargeable item : sales) {
            total += item.calculateCharge();
        }
        return total;
    }

    public double getTotalDiscount() {
        double total = 0;
        for (Sale sale : sales) {
            total += sale.getDiscountAmount();
        }
        return total;
    }

    /** Digit A = 3, so the report is sorted by customer name (A-Z). */
    public List<Sale> getSortedReport() {
        List<Sale> sorted = new ArrayList<>(sales);
        Comparator<Sale> byName = Comparator.comparing(
                Sale::getCustomerName, String.CASE_INSENSITIVE_ORDER);
        sorted.sort(byName.thenComparing(Sale::getId));
        return sorted;
    }
}
