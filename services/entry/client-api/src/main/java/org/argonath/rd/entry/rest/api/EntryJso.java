package org.argonath.rd.entry.rest.api;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class EntryJso {
    @NotEmpty
    private String key;

    @NotNull
    private KeyType keyType;

    @NotEmpty
    private String entity;

    @NotEmpty
    private String description;

    private String attributes;

    private LocalDate applicableDate;

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

    public KeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
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

    public LocalDate getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(LocalDate applicableDate) {
        this.applicableDate = applicableDate;
    }

    public enum KeyType {SIMPLE, COMPOSITE}

    @Override
    public String toString() {
        return "EntryJso{" +
                "key='" + key + '\'' +
                ", keyType=" + keyType +
                ", entity='" + entity + '\'' +
                ", description='" + description + '\'' +
                ", attributes='" + attributes + '\'' +
                ", applicableDate=" + applicableDate +
                '}';
    }
}
