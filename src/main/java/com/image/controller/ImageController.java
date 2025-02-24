package com.image.controller;
import com.image.entity.RegexInformation;
import com.image.service.TextExtractionService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class ImageController {
   @Autowired
   TextExtractionService textExtractionService;

    @PostMapping("/getContent/")
    public ResponseEntity<Object> extractInformationFromImage(@RequestParam("image") MultipartFile file) throws TesseractException, IOException {
            return textExtractionService.convertToText(file);
    }

    @PostMapping("/addRegex")
    public RegexInformation addInformation(@Valid @RequestBody RegexInformation regexInformation) {
        return textExtractionService.addRegexInformation(regexInformation);
    }
}

