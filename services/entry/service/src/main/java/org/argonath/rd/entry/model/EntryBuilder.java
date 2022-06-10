package org.argonath.rd.entry.model;

import org.argonath.rd.entry.util.Assert;

import java.time.LocalDate;

public class EntryBuilder {
    private String key;
    private EntryKey.KeyType keyType;
    private String entity;
    private String description;
    private String attributes;
    private LocalDate applicableDate;

    public static EntryBuilder create(String key, EntryKey.KeyType keyType, String entity) {
        Assert.notNull(key, "Key is 'null'");
        Assert.notNull(keyType, "Key Type is 'null'");
        Assert.notNull(entity, "Entity is 'null'");
        EntryBuilder inst = new EntryBuilder();
        inst.key = key;
        inst.keyType = keyType;
        inst.entity = entity;
        return inst;
    }

    public EntryBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public EntryBuilder withAttributes(String attributes) {
        this.attributes = attributes;
        return this;
    }

    public EntryBuilder withApplicableDate(LocalDate applicableDate) {
        this.applicableDate = applicableDate;
        return this;
    }

    public Entry createEntry() {
        if (applicableDate == null) {
            applicableDate = Entry.defaultApplicableDate();
        }
        return new Entry(key, keyType, entity, description, attributes, applicableDate);
    }

}