package org.argonath.rd.model;

public class EntryBuilder {
    private String key;
    private String entity;
    private String description;
    private String attributes;

    public static EntryBuilder create(String key, String entity) {
        EntryBuilder inst = new EntryBuilder();
        inst.key = key;
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

    public Entry createEntry() {
        return new Entry(key, entity, description, attributes);
    }
}