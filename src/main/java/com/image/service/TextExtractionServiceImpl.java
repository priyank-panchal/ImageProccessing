package com.image.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;

@Service
public class TextExtractionServiceImpl implements TextExtractionService {
    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;
    @Value("${file.tessdata}")
    private String DATA_PATH;
    private String result = null;
    @Autowired
    ImageData imageData;

    @Override
    public ResponseEntity<Object> convertToText(MultipartFile file) {
        try {
            ITesseract instance = new Tesseract();
            instance.setDatapath(DATA_PATH);
            instance.setLanguage("eng");
            Files.copy(file.getInputStream(), Paths.get(FILE_DIRECTORY + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            File internalFile = new File(FILE_DIRECTORY + File.separator + file.getOriginalFilename());
            result = instance.doOCR(internalFile);
            result = result.replaceAll("[^A-Za-z0-9 -@%/,.\n]", " ");
            System.out.println(result);
            FileUtils.write(new File("Demo.txt"), result, "UTF-8");
            extractData();
        } catch (TesseractException e) {
            System.out.println("problem getting");
        } catch (IOException e) {
            System.out.println("problem getting");
        }
        if (result != null) {
            return new ResponseEntity<Object>(imageData, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Content is empty", HttpStatus.BAD_REQUEST);
    }

    private Set<String> fetchGST() {
        Set<String> allGST = new LinkedHashSet<String>();
        try {
            Matcher gstnumber = Regex.gstNumber.matcher(result);
            while (gstnumber.find()) {
                allGST.add(gstnumber.group());
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        allGST.removeAll(Arrays.asList("", null));
        return allGST == null ? Collections.emptySet() : allGST;
    }

    private Set<String> fetchCGST() {
        Set<String> allcgst = new LinkedHashSet<String>();

        try {
            Matcher cgst = Regex.CGST.matcher(result);

            while (cgst.find()) {
                String value = cgst.group();
                value = value.replaceAll("[^0-9.,]", "");
                allcgst.add(value);
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        allcgst.removeAll(Arrays.asList("", null));
        return allcgst == null ? Collections.emptySet() : allcgst;
    }

    private Set<String> fetchSGST() {
        Set<String> allsgst = new LinkedHashSet<String>();
        try {
            Matcher sgst = Regex.SGST.matcher(result);
            while (sgst.find()) {
                String value = sgst.group();
                value = value.replaceAll("[^0-9.]", "");
                allsgst.add(value);
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        allsgst.removeAll(Arrays.asList("", null));
        return allsgst == null ? Collections.emptySet() : allsgst;
    }

    private Set<String> fetchIGST() {
        Set<String> alligst = new LinkedHashSet<String>();
        try {
            Matcher igst = Regex.IGST.matcher(result);

            while (igst.find()) {
                String value = igst.group();
                value = value.replaceAll("[^0-9.]", "");
                alligst.add(value);
            }

        } catch (Exception e) {
            System.out.println("problem getting ");
        }

        alligst.removeAll(Arrays.asList("", null));
        return alligst == null ? Collections.emptySet() : alligst;
    }

    private Set<String> fetchinvoice() {
        Set<String> allInvoice = new LinkedHashSet<String>();
        try {
            Matcher invoice = Regex.invoiceNo.matcher(result);
            Matcher invoice1 = Regex.invoiceNo1.matcher(result);
            Matcher invoice2 = Regex.invoiceNo2.matcher(result);
            while (invoice.find()) {
                String value = invoice.group();
                String[] splited = value.split(" ");
                for (int i = 0; i < splited.length; ++i) {
                    if(splited[i].trim().length() > 1) {
                        allInvoice.add(splited[i].trim());
                        break;
                    }
                }
            }
            while (invoice1.find()) {
                allInvoice.add(invoice1.group());
            }
            while (invoice2.find()) {
                allInvoice.add(invoice2.group());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        allInvoice.removeAll(Arrays.asList("", null));
        return allInvoice == null ? Collections.emptySet() : allInvoice;
    }

    private Set<String> fetchdate() {
        Set<String> allDate = new LinkedHashSet<String>();
        try {
            Matcher date = Regex.date.matcher(result);
            Matcher date2 = Regex.date2.matcher(result);
            Matcher date3 = Regex.date3.matcher(result);
            Matcher date4 = Regex.date4.matcher(result);
            Matcher date5 = Regex.date5.matcher(result);
            Matcher date6 = Regex.date6.matcher(result);
            while (date.find()) {
                allDate.add(date.group().trim());
            }
            while (date2.find()) {
                allDate.add(date2.group().trim());
            }
            while (date3.find()) {
                allDate.add(date3.group().trim());
            }
            while (date4.find()) {
                allDate.add(date4.group().trim());
            }
            while (date5.find()) {
                allDate.add(date5.group().trim());
            }
            while (date6.find()) {
                allDate.add(date6.group().trim());
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        allDate.removeAll(Arrays.asList("", null));
        return allDate == null ? Collections.emptySet() : allDate;
    }

    private Set<String> fetchPartyName() {

        Set<String> allPartyName = new LinkedHashSet<>();
        try {
            Matcher partyName = Regex.partyName.matcher(result);
            while (partyName.find()) {
                String value = partyName.group();
                value = value.replaceAll("[^A-za-z ]", "");
                allPartyName.add(value.trim());
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        allPartyName.removeAll(Arrays.asList("", null));
        return allPartyName == null ? Collections.emptySet() : allPartyName;
    }

    private Set<String> fetchTotal() {
        Set<String> allgrandtotal = new LinkedHashSet<>();
        try {
            Matcher grandtotal = Regex.grandTotal.matcher(result);
            while (grandtotal.find()) {
                String value = grandtotal.group();
                System.out.println(value);
                value = value.replaceAll("[^0-9., ]", "").trim();
                System.out.println(value);
                String splitedValues[] = value.split(" ");
                for (int i = 0; i < splitedValues.length; ++i) {
                    if (splitedValues[i].length() > 4) {
                        allgrandtotal.add(splitedValues[i].trim());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        allgrandtotal.removeAll(Arrays.asList("", null));
        return allgrandtotal == null ? Collections.emptySet() : allgrandtotal;
    }
    private Set<String> fetchTDS(){
        Set<String> alltds = new LinkedHashSet<>();
        try {
            Matcher tds = Regex.TDS.matcher(result);
            while (tds.find()) {
                alltds.add(tds.group().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        alltds.removeAll(Arrays.asList("", null));
        return alltds == null ? Collections.emptySet() : alltds;

    }


    private void extractData() {
        imageData.setGstnumbers(fetchGST());
        imageData.setSgst(fetchSGST());
        imageData.setCgst(fetchCGST());
        imageData.setIgst(fetchIGST());
        imageData.setPartyName(fetchPartyName());
        imageData.setInvoiceNumber(fetchinvoice());
        imageData.setInvoicedate(fetchdate());
        imageData.setTotal(fetchTotal());
        imageData.setTds(fetchTDS());

    }


}
