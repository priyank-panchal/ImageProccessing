package com.image.service;

import java.util.regex.Pattern;

public class Regex {
    static Pattern gstNumber = Pattern.compile("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}", Pattern.CASE_INSENSITIVE);
    static Pattern SGST = Pattern.compile("(?<=SGST Payable|SGST(9%)|SGST|SGST Payable|S GST).+", Pattern.CASE_INSENSITIVE);
    static Pattern CGST = Pattern.compile("(?<=CGST Payable|CGST|CGST Payable|C GST).+", Pattern.CASE_INSENSITIVE);
    static Pattern partyName = Pattern.compile("(?<=Party|Party Name|Party name|M/S|Billed To).+", Pattern.CASE_INSENSITIVE);
    static Pattern partyName1 = Pattern.compile("(?<=Bill To\\n|Buyer To Party\\n|Buyer To\\r\\n)[^\\r\\n]+",Pattern.CASE_INSENSITIVE);
    static Pattern invoiceNo = Pattern.compile("(?<=Invoice No[.| ]|Bill No[.| ]).+ ", Pattern.CASE_INSENSITIVE);
    static Pattern invoiceNo1 = Pattern.compile("[A-z0-9]{1,5}[\\/| ][0-9]{1,5}-[0-9]{1,5}");
    static Pattern invoiceNo2 = Pattern.compile("[A-z0-9]{1,5}\\/[0-9]{1,5}");
    static Pattern IGST = Pattern.compile("(?<=IGST Payable|IGST|Total Amount GST|Taxable Amount|GST \\(18\\%\\) |G S T \\[ Ra t e - 18.00\\% \\]).+", Pattern.CASE_INSENSITIVE);
    static Pattern date = Pattern.compile("([0-9]{1,2})-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-([0-9]{2,4})", Pattern.CASE_INSENSITIVE);
    static Pattern date2 = Pattern.compile("\\d{1,2}\\/\\d{1,2}\\/\\d{2,4}");
    static Pattern date3 = Pattern.compile("\\d{1,2}-\\d{1,2}-\\d{2,4}");
    static Pattern date4 = Pattern.compile("\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}");
    static Pattern date5 = Pattern.compile("([0-9]{1,2}) (JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC) ([0-9]{2,4})",Pattern.CASE_INSENSITIVE);
    static Pattern date6 = Pattern.compile("([0-9]{1,2})\\/(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\/([0-9]{2,4})",Pattern.CASE_INSENSITIVE);
    static Pattern grandTotal = Pattern.compile("(?<=Grand Total|Amount Total|Total|Amount).+", Pattern.CASE_INSENSITIVE);
    static Pattern TDS = Pattern.compile("(?<=TDS).+", Pattern.CASE_INSENSITIVE);

}
