package com.example.qcassistant.domain.order;

import com.example.qcassistant.domain.item.document.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DocumentRepository {

    private Collection<Document> documents;

    public DocumentRepository(){
        this.documents = new ArrayList<>();
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public DocumentRepository setDocuments(List<Document> documents) {
        this.documents = documents;
        return this;
    }

    public void addDocument(Document document){
        this.documents.add(document);
    }
}
