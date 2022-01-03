package org.argonath.rd.rest.dto;

import org.argonath.rd.model.Entry;
import org.argonath.rd.model.EntryBuilder;

public class EntryJso {
    private String key;
    private String entity;
    private String description;
    private String attributes;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Entry entry() {
        return EntryBuilder.create(key, entity).withDescription(description).withAttributes(attributes).createEntry();
    }
}
