package com.zpmc.ztos.infra.business.edi.repository;

import com.zpmc.ztos.infra.business.edi.EdiDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EdiDocumentRepository extends MongoRepository<EdiDocument, Long> {

}
