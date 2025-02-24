package com.image.service;

import com.image.entity.RegexInformation;
import com.image.repository.RegexInformationRepository;
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
import java.util.regex.Pattern;

@Service
public class TextExtractionServiceImpl implements TextExtractionService {
    @Autowired
    private RegexInformationRepository regexInformationRepository;
    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;
    @Value("${file.tessdata}")
    private String DATA_PATH;
    private String result = null;
    @Autowired
    ImageData imageData;

    @Override
    public RegexInformation addRegexInformation(RegexInformation regexInformation) {
        return regexInformationRepository.save(regexInformation);
    }

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
            if (result == null) {
                Map<String, String> resp = new HashMap<>();
                resp.put("error", "Content is Empty");
                return new ResponseEntity<Object>(resp, HttpStatus.BAD_REQUEST);
            }
            FileUtils.write(new File("Demo.txt"), result, "UTF-8");
            extractData();
        } catch (TesseractException e) {
            System.out.println("problem getting");
        } catch (IOException e) {
            System.out.println("problem getting");
        }
        return new ResponseEntity<Object>(imageData, HttpStatus.OK);
    }

    private Set<String> fetchGST() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("gstnumber");
        Set<String> allGST = new LinkedHashSet<>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern gstPattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher gstnumber = gstPattern.matcher(result);
                while (gstnumber.find()) {
                    allGST.add(gstnumber.group());
                }
            } catch (Exception e) {
                System.out.println("problem getting ");
            }
            allGST.removeAll(Arrays.asList("", null));
        }
        return allGST == null ? Collections.emptySet() : allGST;
    }

    private Set<String> fetchCGST() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("cgst");
        Set<String> allcgst = new LinkedHashSet<String>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern cgstPattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher cgst = cgstPattern.matcher(result);
                while (cgst.find()) {
                    String value = cgst.group();
                    value = value.replaceAll("[^0-9.,]", "");
                    if(value.length() > 2)
                         allcgst.add(value);
                }
            } catch (Exception e) {
                System.out.println("problem getting ");
            }
        }
        allcgst.removeAll(Arrays.asList("", null));
        return allcgst == null ? Collections.emptySet() : allcgst;
    }

    private Set<String> fetchSGST() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("sgst");
        Set<String> allsgst = new LinkedHashSet<String>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern sgstPattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher sgst = sgstPattern.matcher(result);
                while (sgst.find()) {
                    String value = sgst.group();
                    value = value.replaceAll("[^0-9,.]", "");
                    if(value.length() > 2)
                        allsgst.add(value);
                }
            } catch (Exception e) {
                System.out.println("problem getting ");
            }
        }
        allsgst.removeAll(Arrays.asList("", null));
        return allsgst == null ? Collections.emptySet() : allsgst;
    }

    private Set<String> fetchIGST() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("igst");
        Set<String> alligst = new LinkedHashSet<String>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern igstPattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher igst = igstPattern.matcher(result);
                while (igst.find()) {
                    String value = igst.group();
                    value = value.replaceAll("[^0-9,.]", "");
                    if(value.length() > 2)
                          alligst.add(value);
                }
            } catch (Exception e) {
                System.out.println("problem getting ");
            }
        }
        alligst.removeAll(Arrays.asList("", null));
        return alligst == null ? Collections.emptySet() : alligst;
    }

    private Set<String> fetchinvoice() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("invoiceno");
        Set<String> allInvoice = new LinkedHashSet<String>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern invoicePattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher invoice = invoicePattern.matcher(result);
                while (invoice.find()) {
                    String value = invoice.group();
                    String[] splited = value.split(" ");
                    for (int i = 0; i < splited.length; ++i) {
                        if (splited[i].chars().anyMatch(Character::isDigit)) {
                            allInvoice.add(splited[i].trim());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allInvoice.removeAll(Arrays.asList("", null));
        return allInvoice == null ? Collections.emptySet() : allInvoice;

    }

    private Set<String> fetchdate() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("date");
        Set<String> allDate = new LinkedHashSet<String>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern datePattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher date = Regex.date.matcher(result);
                while (date.find()) {
                    allDate.add(date.group().trim());
                }
            } catch (Exception e) {
                System.out.println("problem getting ");
            }
        }
        allDate.removeAll(Arrays.asList("", null));
        return allDate == null ? Collections.emptySet() : allDate;
    }

    private Set<String> fetchPartyName() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("partyname");
        Set<String> allPartyName = new LinkedHashSet<>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern partynamePattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher partyName = partynamePattern.matcher(result);
                while (partyName.find()) {
                    String value = partyName.group();
                    allPartyName.add(value.replaceAll("[^A-za-z ]", "").trim());
                }
            } catch (Exception e) {
                System.out.println("problem getting ");
            }
        }
        allPartyName.removeAll(Arrays.asList("", null));
        return allPartyName == null ? Collections.emptySet() : allPartyName;
    }

    private Set<String> fetchTotal() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("total");
        Set<String> allgrandtotal = new LinkedHashSet<>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern grandtotalPattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher grandtotal = grandtotalPattern.matcher(result);
                while (grandtotal.find()) {
                    String value = grandtotal.group();
                    value = value.replaceAll("[^0-9., ]", "").trim();
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
        }
        allgrandtotal.removeAll(Arrays.asList("", null));
        return allgrandtotal == null ? Collections.emptySet() : allgrandtotal;
    }

    private Set<String> fetchTDS() {
        RegexInformation[] byname = regexInformationRepository.findByPatternNameIgnoreCase("tds");
        Set<String> alltds = new LinkedHashSet<>();
        for (RegexInformation regexInformation : byname) {
            try {
                Pattern tdsPattern = Pattern.compile(regexInformation.getRexPattern(), Pattern.CASE_INSENSITIVE);
                Matcher tds = tdsPattern.matcher(result);
                while (tds.find()) {
                    alltds.add(tds.group().trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
