package com.zpmc.ztos.infra.business.edi.service;

import com.zpmc.ztos.infra.business.edi.EdiDocument;
import org.springframework.stereotype.Service;

@Service
public class EdiDocumentService {

    public EdiDocument createAndUpdateDocument(String fileName) {
        // TODO: 16M
        return new EdiDocument();
    }

}
