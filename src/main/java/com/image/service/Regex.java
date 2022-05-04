package com.image.service;
import java.util.regex.Pattern;
public class Regex {

    static Pattern gstNumber = Pattern.compile("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}",Pattern.CASE_INSENSITIVE);
    static Pattern SGST = Pattern.compile("(?<=SGST Payable | SGST|SGST Payable).+");
    static Pattern CGST = Pattern.compile("(?<=CGST Payable | CGST|CGST Payable).+");
    static Pattern partyName = Pattern.compile("(?<=Party | Party Name | Party name|M/S |Billed To|).+",Pattern.CASE_INSENSITIVE);
    static Pattern invoiceNo = Pattern.compile("(?<=Invoice | Invoice No. | Invoice No).+", Pattern.CASE_INSENSITIVE);
    static Pattern IGST = Pattern.compile("(?<=IGST Payable | IGST).+");
    static Pattern date = Pattern.compile("([0-9]{1,2})-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-([0-9]{4})",Pattern.CASE_INSENSITIVE);
    static Pattern date1 = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((?:19|20)[0-9][0-9])");
    static Pattern date2 = Pattern.compile("(0?[1-9]/[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])");
    static Pattern date3 = Pattern.compile("((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");
    static Pattern date4 = Pattern.compile("((?:19|20)[0-9][0-9])\\\\.(0?[1-9]|1[012])\\\\.(0?[1-9]|[12][0-9]|3[01])$");
    static Pattern grandTotal = Pattern.compile("(?<=Grand Total|Total|Total Amount |Amount).+" ,Pattern.CASE_INSENSITIVE);
    static Pattern TDS = Pattern.compile("(?<=TDS |tds).+",Pattern.CASE_INSENSITIVE);



}
