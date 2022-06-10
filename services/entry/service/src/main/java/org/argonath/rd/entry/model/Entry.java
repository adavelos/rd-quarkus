package org.argonath.rd.entry.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The `Entry` represents an instance of a reference data set (the 'entity').
 * It is uniquely identified by its 'key' within the specific entity.
 * The 'key' can be either a simple String value, or an encoded value of a specific format representing a complex structure.
 * <p>
 * There is an optional description and optional attributes which are encoded in the same format where the key is encoded.
 * <p>
 * The encoding format is a meta-data field which is stored in the specific key's meta-data structure or in the
 * specific entity's meta-data structure. The default encoding format is JSON.
 */
public class Entry {

    private String key;
    private String keyHash;
    private EntryKey.KeyType keyType;

    private String entity;
    private String description;
    private String attributes;
    private LocalDate applicableDate;
    private LocalDateTime modificationDate;
    private Action action;

    public Entry(String key, EntryKey.KeyType keyType, String entity, String description, String attributes, LocalDate applicableDate) {
        this.key = key;
        this.keyType = keyType;
        this.keyHash = EntryKey.keyHash(key);
        this.entity = entity;
        this.description = description;
        this.attributes = attributes;
        this.applicableDate = applicableDate;
        this.modificationDate = LocalDateTime.now();
        this.action = Action.U;
    }

    public Entry(String key, EntryKey.KeyType keyType, String keyHash, String entity, String description, String attributes, LocalDate applicableDate, LocalDateTime modificationDate, Action action) {
        this(key, keyType, entity, description, attributes, applicableDate);
        this.keyType = keyType;
        this.keyHash = keyHash;
        this.modificationDate = modificationDate;
        this.action = action;
    }

    public static Entry deleteEntry(Entry entry) {
        Entry delEntry = new Entry(
                entry.key,
                entry.keyType,
                entry.keyHash,
                entry.entity,
                entry.description,
                entry.attributes,
                entry.applicableDate,
                entry.modificationDate,
                Action.D);
        return delEntry;
    }

    public String getKey() {
        return key;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public EntryKey.KeyType getKeyType() {
        return keyType;
    }

    public String getEntity() {
        return entity;
    }

    public String getDescription() {
        return description;
    }

    public String getAttributes() {
        return attributes;
    }

    public LocalDate getApplicableDate() {
        return applicableDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public Action getAction() {
        return action;
    }

    public static LocalDate defaultApplicableDate() {
        return LocalDate.now()/*.plusDays(1)*/;
    }

    public static LocalDate defaultAtDate() {
        return LocalDate.now();
    }

}
