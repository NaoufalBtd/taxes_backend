package taxes.bean;

import lombok.Data;

@Data
public class SumInvoicesByMonth {
    private int month;
    private int year;
    private double sumTTC;
}