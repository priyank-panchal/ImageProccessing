package com.image.service;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@NoArgsConstructor
@Data
public class ImageData {
    public List<String> gstnumbers;
    public List<String> sgst;
    public List<String> cgst;
    public List<String> igst;
    public List<String> partyName;
    public List<String> invoiceNumber;
    public List<String> invoicedate ;
    public List<String> total;
    public List<String> TDS;
}
