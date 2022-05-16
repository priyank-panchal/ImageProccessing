package com.image.service;

import com.image.entity.RegexInformation;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TextExtractionService {
     public ResponseEntity<Object> convertToText(MultipartFile file) throws IOException, TesseractException;
     public RegexInformation addRegexInformation(RegexInformation regexInformation);

}

