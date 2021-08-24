package com.zpmc.ztos.infra.business.edi;

import com.zpmc.ztos.infra.business.edi.repository.EdiDocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class EdiDocumentTest {
    @Autowired
    private EdiDocumentRepository ediDocumentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test() {
        EdiDocument ediDocument = new EdiDocument();
        ediDocument.setAuthor("aaa");
        ediDocument.setDocType("bbb");
        ediDocument.setContent("ccc");
        ediDocumentRepository.save(ediDocument);

        List<EdiDocument> allDocuments = ediDocumentRepository.findAll();
        System.out.println(allDocuments);

        EdiDocument ediDocument1 = mongoTemplate.findOne(
                Query.query(Criteria.where("author").is("aaa")), EdiDocument.class);

        System.out.println(ediDocument1);
    }
}