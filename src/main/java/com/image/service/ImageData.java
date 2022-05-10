package com.image.service;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@NoArgsConstructor
@Getter
@Setter
public class ImageData {
    public Set<String> gstnumbers;
    public Set<String> sgst;
    public Set<String> cgst;
    public Set<String> igst;
    public Set<String> partyName;
    public Set<String> invoiceNumber;
    public Set<String> invoicedate ;
    public Set<String> total;
    public Set<String> tds;
}
