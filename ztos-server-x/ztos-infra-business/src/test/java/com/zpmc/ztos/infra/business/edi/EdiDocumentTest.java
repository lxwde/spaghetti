package com.zpmc.ztos.infra.business.edi;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.zpmc.ztos.infra.business.edi.repository.EdiDocumentRepository;
import org.apache.commons.io.IOUtils;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class EdiDocumentTest {
    @Autowired
    private EdiDocumentRepository ediDocumentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    @Test
    public void test() throws IOException {
        EdiDocument ediDocument = new EdiDocument();
        ediDocument.setAuthor("aaa");
        ediDocument.setDocType("bbb");

        ediDocument.setContent(new Binary(IOUtils.toByteArray(this.getClass().getResourceAsStream("img.png"))));
        ediDocumentRepository.save(ediDocument);

        List<EdiDocument> allDocuments = ediDocumentRepository.findAll();
        System.out.println(allDocuments);

        EdiDocument ediDocument1 = mongoTemplate.findOne(
                Query.query(Criteria.where("author").is("aaa")), EdiDocument.class);
        InputStream inputStream = new ByteArrayInputStream(ediDocument1.getContent().getData());
        System.out.println(inputStream);

        ObjectId id = gridFsTemplate.store(inputStream, "big-file.png");
        System.out.println(id);

        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        System.out.println(file.getFilename());
        GridFsResource resource = gridFsOperations.getResource("big-file.png");
        System.out.println(IOUtils.toByteArray(resource.getInputStream()));
    }
}