package org.argonath.rd.model;

/**
 * The `Entry` represents an instance of a reference data set (the 'entity').
 * It is uniquely identified by its 'key' within the specific entity.
 * The 'key' can be either a simple String value, or an encoded value of a specific format representing a complex structure.
 *
 * There is an optional description and optional attributes which are encoded in the same format where the key is encoded.
 *
 * The encoding format is a meta-data field which is stored in the specific key's meta-data structure or in the
 * specific entity's meta-data structure. The default encoding format is JSON.
 *
 */
public class Entry {
    private String key;
    private String entity;
    private String description;
    private String attributes;

    public Entry(String key, String entity, String description, String attributes) {
        this.key = key;
        this.entity = entity;
        this.description = description;
        this.attributes = attributes;
    }

    public String getKey() {
        return key;
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


}
