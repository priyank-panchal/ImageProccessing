package com.image.service;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
            extractData();
        } catch (TesseractException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result != null) {
            return new ResponseEntity<Object>(imageData, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Content is empty", HttpStatus.BAD_REQUEST);
    }

    private List<String> fetchGST() {
        List<String> allGST = new ArrayList<>();
        try {
            Matcher gstnumber = Regex.gstNumber.matcher(result);
            while (gstnumber.find()) {
                allGST.add(gstnumber.group());
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        return allGST == null ? Collections.emptyList() : allGST;
    }

    private List<String> fetchCGST() {
        List<String> allcgst = new ArrayList<>();
        try {
            Matcher cgst = Regex.CGST.matcher(result);

            while (cgst.find()) {
                String value = cgst.group();
                value = value.replaceAll("[^0-9.]", "");
                allcgst.add(value);
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        return allcgst == null ? Collections.emptyList() : allcgst;
    }

    private List<String> fetchSGST() {
        List<String> allsgst = new ArrayList<>();
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
        return allsgst == null ? Collections.emptyList() : allsgst;
    }

    private List<String> fetchIGST() {
        List<String> alligst = new ArrayList<>();
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
        return alligst == null ? Collections.emptyList() : alligst;
    }

    private List<String> fetchinvoice() {
        List<String> allInvoice = new ArrayList<>();
        try {
            Matcher invoice = Regex.invoiceNo.matcher(result);

            while (invoice.find()) {
                String value = invoice.group();
                String[] splited = value.split(" ");
                for (int i = 0; i < splited.length; ++i) {
                    if (splited[i].chars().allMatch(Character::isDigit)) {
                        allInvoice.add(splited[i]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        return allInvoice == null ? Collections.emptyList() : allInvoice;
    }

    private List<String> fetchdate() {
        List<String> allDate = new ArrayList<>();
        try {
            Matcher date = Regex.date.matcher(result);
            Matcher date1 = Regex.date1.matcher(result);
            Matcher date2 = Regex.date2.matcher(result);
            Matcher date3 = Regex.date3.matcher(result);
            Matcher date4 = Regex.date4.matcher(result);

            while (date.find()) {
                String value = date.group();
                allDate.add(value.trim());
            }
            while (date2.find()) {
                String value = date.group();
                allDate.add(value.trim());
            }
            while (date4.find()) {
                String value = date.group();
                allDate.add(value.trim());
            }
            while (date3.find()) {
                String value = date.group();
                allDate.add(value.trim());
            }
            while (date1.find()) {
                allDate.add(date.group().trim());
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }
        return allDate == null ? Collections.emptyList() : allDate;
    }

    private List<String> fetchPartyName() {
        List<String> allPartyName = new ArrayList<>();
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
        return allPartyName == null ? Collections.emptyList() : allPartyName;
    }

    private List<String> fetchTotal() {
        List<String> allgrandtotal = new ArrayList<>();
        try {
            Matcher grandtotal = Regex.grandTotal.matcher(result);
            while (grandtotal.find()) {
                String value = grandtotal.group();
                value = value.replaceAll("[^0-9. ]", "");
                allgrandtotal.add(value.trim());
            }
        } catch (Exception e) {
            System.out.println("problem getting ");
        }

        return allgrandtotal == null ? Collections.emptyList() : allgrandtotal;
    }


    private void extractData() {
        imageData.setGstnumbers(fetchGST());
        imageData.setSgst(fetchSGST());
        imageData.setCgst(fetchCGST());
        imageData.setIgst(fetchIGST());
        imageData.setPartyName(fetchPartyName());
        imageData.setInvoiceNumber(fetchinvoice());
        imageData.setInvoicedate(fetchdate());
        List<String> totals = fetchTotal();
        imageData.setTotal(fetchTotal());

    }


}
