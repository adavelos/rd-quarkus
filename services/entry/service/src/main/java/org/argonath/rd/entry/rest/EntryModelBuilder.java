package org.argonath.rd.entry.rest;

import org.argonath.rd.entry.model.Entry;
import org.argonath.rd.entry.model.EntryBuilder;
import org.argonath.rd.entry.model.EntryKey;
import org.argonath.rd.entry.model.VersionRequest;
import org.argonath.rd.entry.rest.api.EntryJso;
import org.argonath.rd.entry.rest.api.VersionRequestJso;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntryModelBuilder {

    public static Entry convert(EntryJso entryJso) {
        Entry ret = EntryBuilder.create(entryJso.getKey(), convert(entryJso.getKeyType()), entryJso.getEntity())
                .withDescription(entryJso.getDescription())
                .withAttributes(entryJso.getAttributes())
                .withApplicableDate(entryJso.getApplicableDate())
                .createEntry();
        return ret;
    }

    public static EntryJso convert(Entry entry) {
        if (entry == null) {
            return null;
        }
        EntryJso ret = new EntryJso();
        ret.setKey(entry.getKey());
        ret.setEntity(entry.getEntity());
        ret.setApplicableDate(entry.getApplicableDate());
        ret.setAttributes(entry.getAttributes());
        ret.setDescription(entry.getDescription());
        ret.setKeyType(convert(entry.getKeyType()));
        return ret;
    }

    public static List<EntryJso> convert(List<Entry> entryList) {
        if (entryList == null) {
            return Collections.emptyList();
        }
        return entryList.stream()
                .map(entry -> convert(entry))
                .collect(Collectors.toList());
    }

    public static EntryKey.KeyType convert(EntryJso.KeyType keyTypeJso) {
        if (keyTypeJso == null) {
            return null;
        }
        switch (keyTypeJso) {
            case SIMPLE:
                return EntryKey.KeyType.SIMPLE;
            case COMPOSITE:
                return EntryKey.KeyType.COMPOSITE;
            default:
                return null;
        }
    }

    public static EntryJso.KeyType convert(EntryKey.KeyType keyType) {
        if (keyType == null) {
            return null;
        }
        switch (keyType) {
            case SIMPLE:
                return EntryJso.KeyType.SIMPLE;
            case COMPOSITE:
                return EntryJso.KeyType.COMPOSITE;
            default:
                return null;
        }
    }

    public static VersionRequest convert(VersionRequestJso versionRequestJso) {
        return new VersionRequest(versionRequestJso.getAt());
    }
}
