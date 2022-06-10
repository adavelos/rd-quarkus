package org.argonath.rd.entry.data;

import org.argonath.rd.entry.model.Action;
import org.argonath.rd.entry.model.Entry;
import org.argonath.rd.entry.model.EntryKey;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "ENTRY")
@Entity(name = "Entry")
public class EntryJpo {

    @Id
    @SequenceGenerator(name = "entrySequenceGenerator", sequenceName = "ENTRY_SEQUENCE", allocationSize = 10)
    @GeneratedValue(generator = "entrySequenceGenerator")
    @Column(name = "SID")
    private Integer id;

    @Column(name = "KEY")
    private String key;

    @Column(name = "KEY_HASH")
    private String keyHash;

    @Column(name = "KEY_TYPE")
    @Enumerated(EnumType.STRING)
    private EntryKey.KeyType keyType;

    @Column(name = "ENTITY")
    private String entity;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ATTRIBUTES")
    private String attributes;

    @Column(name = "APPL_DATE")
    private LocalDate applicableDate;

    @Column(name = "MODIF_DATE")
    private LocalDateTime modificationDate;

    @Column(name = "ACTION")
    private Action action;

    public EntryJpo(Entry entry) {
        this.key = entry.getKey();
        this.keyHash = entry.getKeyHash();
        this.keyType = entry.getKeyType();
        this.entity = entry.getEntity();
        this.description = entry.getDescription();
        this.attributes = entry.getAttributes();
        this.applicableDate = entry.getApplicableDate();
        this.modificationDate = entry.getModificationDate();
        this.action = entry.getAction();
    }

    // Used by JPA
    public EntryJpo() {
    }

    public String getKey() {
        return key;
    }

    public Entry model() {
        return new Entry(key, keyType, keyHash, entity, description, attributes, applicableDate, modificationDate, action);
    }

    public static List<Entry> model(List<EntryJpo> entryJpoList) {
        return entryJpoList.stream()
                .map(e -> e.model())
                .collect(Collectors.toList());
    }

}
