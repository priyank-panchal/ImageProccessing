package com.image.repository;

import com.image.entity.RegexInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface RegexInformationRepository extends JpaRepository<RegexInformation,Long> {
    public RegexInformation[] findByPatternNameIgnoreCase(String patternName);
}
