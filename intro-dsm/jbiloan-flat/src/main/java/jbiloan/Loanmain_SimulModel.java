/*
 * The model for a loan simulation.
 */
package jbiloan;

import static jbiloan.Loanutil_MyBundle.translate;

import javax.swing.table.AbstractTableModel;

/**
 * The model for a loan simulation
 *
 * @author jean-blas imbert
 */
public class Loanmain_SimulModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    /**
     * The loan item which is used to generate the simulation
     */
    private Loanmain_LoanItem item = null;

    /**
     * Constructor
     *
     * @param pItem loan item which is used to generate the simulation
     */
    public Loanmain_SimulModel(final Loanmain_LoanItem pItem) {
        item = pItem;
    }

    /**
     * Number of rows (= number of months, hence depends on the duration : 12 * duration)
     *
     * @return the number of rows of this model
     */
    @Override
    public int getRowCount() {
        return item.getDuree().intValue() * 12 + 1;
    }

    /**
     * Columns are MONTH, AMOUNT STILL OWED, INTERESTS PAID, AMOUNT PAID for this month, INSURANCE, TOTAL
     *
     * @return the number of columns of the model
     */
    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public boolean isCellEditable(int pRow, int pCol) {
        return false;
    }

    @Override
    public String getColumnName(int pCol) {
        switch (pCol) {
            case 0:
                return translate("mois");
            case 1:
                return translate("montantDu");
            case 2:
                return translate("interetsPayes");
            case 3:
                return translate("montantPaye");
            case 4:
                return translate("insurance");
            case 5:
                return translate("total");
            default:
                return super.getColumnName(pCol);
        }
    }

    /**
     * Recursive. Columns are :
     * <ol>
     * <li>Month
     * <li>Amount still owed
     * <li>Paid interests
     * <li>Paid amount for this month
     * </ol>
     *
     * @param pRow the row value
     * @param pCol the column value
     * @return the cell value
     */
    @Override
    public Object getValueAt(int pRow, int pCol) {
        switch (pCol) {
            case 0:
                return pRow;
            case 1:
                if (pRow == 0) {
                    return item.getAmount().doubleValue();
                }
                return (Double) getValueAt(pRow - 1, 1) * (1D + item.getTaux() / 1200D) - Loanmain_CalcLoanItem.computeMensHorsAss(item);
            case 2:
                if (pRow == 0) {
                    return 0D;
                }
                return (Double) getValueAt(pRow - 1, 1) * (item.getTaux() / 1200D);
            case 3:
                if (pRow == 0) {
                    return 0D;
                }
                return (Double) getValueAt(pRow - 1, 1) - (Double) getValueAt(pRow, 1);
            case 4:
                if (pRow == 0 || item.getInsurance().equals(0F)) {
                    return 0D;
                }
                return Loanmain_CalcLoanItem.computeMensAss(item) * pRow;
            case 5:
                if (pRow == 0) {
                    return 0D;
                }
                return item.getMensualite().doubleValue() * pRow;
        }
        return 0D;
    }

    /**
     * Save the model full content into csv format
     *
     * @return the csv formatted string
     */
    public String toCsv() {
        final String lSep = ";";
        final String lNewLine = "\r\n";
        StringBuilder lBuilder = new StringBuilder();
        for (int lCol = 0; lCol < getColumnCount(); lCol++) {
            lBuilder.append(getColumnName(lCol)).append(lSep);
        }
        lBuilder.append(lNewLine);
        for (int lRow = 0; lRow < getRowCount(); lRow++) {
            for (int lCol = 0; lCol < getColumnCount(); lCol++) {
                lBuilder.append(getValueAt(lRow, lCol)).append(lSep);
            }
            lBuilder.append(lNewLine);
        }
        return lBuilder.toString();
    }
}
