package org.argonath.rd.data;

import org.argonath.rd.model.Entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "ENTRY")
@Entity(name = "Entry")
public class EntryJpo {

    @Id
    @Column(name = "KEY")
    private String key;

    @Column(name = "ENTITY")
    private String entity;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ATTRIBUTES")
    private String attributes;

    public EntryJpo(Entry entry) {
        this.key = entry.getKey();
        this.entity = entry.getEntity();
        this.description = entry.getDescription();
        this.attributes = entry.getAttributes();
    }

    public void update(Entry entry) {
        this.description = entry.getDescription();
        this.attributes = entry.getAttributes();
    }

    // Used by JPA
    public EntryJpo() {
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

    public Entry model() {
        return new Entry(key, entity, description, attributes);
    }

    public static List<Entry> model(List<EntryJpo> entryJpoList) {
        return entryJpoList.stream()
                .map(e -> e.model())
                .collect(Collectors.toList());
    }

}
