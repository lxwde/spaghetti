package com.zpmc.ztos.infra.business.edi;

import com.zpmc.ztos.infra.business.edi.repository.EdiDocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class EdiDocumentTest {
    @Autowired
    EdiDocumentRepository ediDocumentRepository;

    @Test
    public void test() {
        EdiDocument ediDocument = new EdiDocument();
        ediDocument.setAuthor("aaa");
        ediDocument.setDocType("bbb");
        ediDocument.setContent("ccc");
        ediDocumentRepository.save(ediDocument);
    }
}