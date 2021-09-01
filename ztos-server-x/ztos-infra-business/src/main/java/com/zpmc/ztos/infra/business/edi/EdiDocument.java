package com.zpmc.ztos.infra.business.edi;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.UUID;

@Document(collection = "ediDocument")
public class EdiDocument {

    @Id
    private String id;
    private String author;
    private String docType;
    private String fileName;
    private String directory;
    private Binary content;
    private String fileId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Binary getContent() {
        return content;
    }

    public void setContent(Binary content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdiDocument that = (EdiDocument) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (docType != null ? !docType.equals(that.docType) : that.docType != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (docType != null ? docType.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EdiDocument{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", docType='" + docType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
