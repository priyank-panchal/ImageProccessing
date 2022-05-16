package com.image.service;

import java.util.regex.Pattern;

public class Regex {
    static Pattern gstNumber = Pattern.compile("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}", Pattern.CASE_INSENSITIVE);
    static Pattern SGST = Pattern.compile("(?<=SGST\\([^A-z]{1,10}\\) |S GST|SGST\\s?(?!\\([^A-z]{1,10}\\))).+", Pattern.CASE_INSENSITIVE);
    static Pattern CGST = Pattern.compile("(?<=CGST\\([^A-z]{1,10}\\) |C GST|CGST\\s?(?!\\([^A-z]{1,10}\\))).+", Pattern.CASE_INSENSITIVE);
    static Pattern partyName = Pattern.compile("(?<=Party (?!Name)|Party Name|M\\/S|Billed To).+", Pattern.CASE_INSENSITIVE);
    static Pattern partyName_ = Pattern.compile("(?<=Bill To\\n|Buyer To Party\\n|Buyer To\\r\\n|Party Name\\r\\n|M\\/S\\r\\n|Billed To\\r\\n)[^\\r\\n]+",Pattern.CASE_INSENSITIVE);
    static Pattern invoiceNo = Pattern.compile("(?<=Invoice No|Bill No).+", Pattern.CASE_INSENSITIVE);
    static Pattern invoiceNo_ = Pattern.compile("[0-9]{1,5}\\/[0-9]{1,3}-[0-9]{1,3}",Pattern.CASE_INSENSITIVE);
    static Pattern invoiceNo__ = Pattern.compile("[A-z]{1,5}\\/[0-9]{1,5}-[0-9]{1,5}\\/[0-9]{1,5}",Pattern.CASE_INSENSITIVE);
    static Pattern IGST = Pattern.compile("(?<=IGST Payable|IGST\\s?(?!\\([^A-z]{1,10}\\))|Total Amount GST|Taxable Amount|GST\\(18\\%\\)|G S T \\[ Ra t e - 18.00\\% \\]|IGST\\s?\\([^A-z]{1,10}\\)).+", Pattern.CASE_INSENSITIVE);
    static Pattern date = Pattern.compile("([0-9]{1,2})-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-([0-9]{2,4})", Pattern.CASE_INSENSITIVE);
    static Pattern date_ = Pattern.compile("\\d{1,2}\\/\\d{1,2}\\/\\d{2,4}");
    static Pattern date__ = Pattern.compile("\\d{1,2}-\\d{1,2}-\\d{2,4}");
    static Pattern date___= Pattern.compile("\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}");
    static Pattern date____ = Pattern.compile("([0-9]{1,2}) (JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC) ([0-9]{2,4})",Pattern.CASE_INSENSITIVE);
    static Pattern date_____ = Pattern.compile("([0-9]{1,2})\\/(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\/([0-9]{2,4})",Pattern.CASE_INSENSITIVE);
    static Pattern grandTotal = Pattern.compile("(?<=Grand Total|Amount Total|Amount(\\s|:)(?!Total)).+", Pattern.CASE_INSENSITIVE);
    static Pattern grandTotal_ = Pattern.compile("(?<!GST Total|sub total|weight total|Total Net Weight|Total Gross Weight|T  otal Amount GST)(?<=Total).+",Pattern.CASE_INSENSITIVE);
    static Pattern TDS = Pattern.compile("(?<=TDS).+", Pattern.CASE_INSENSITIVE);
}
