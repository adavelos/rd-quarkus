package org.argonath.rd.entry.data;

import org.argonath.rd.entry.model.Entry;
import org.argonath.rd.entry.model.EntryKey;
import org.argonath.rd.entry.model.VersionRequest;
import org.jboss.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Dependent
public class EntryDao {
    private static final Logger LOGGER = Logger.getLogger(EntryDao.class.getName());

    @Inject
    EntityManager em;

    @Transactional(Transactional.TxType.REQUIRED)
    public void createEntry(Entry entry) {
        EntryJpo entryJpo = new EntryJpo(entry);
        em.persist(entryJpo);
    }

    public Entry get(String entity, String key, VersionRequest versionRequest) {
        if (versionRequest == null || versionRequest.getAt() == null) {
            versionRequest = VersionRequest.DEFAULT;
        }
        EntryJpo entryJpo = getEntryJpo(entity, key, versionRequest);
        return entryJpo != null ? entryJpo.model() : null;
    }

    public List<Entry> list(String entity, VersionRequest versionRequest) {
        if (versionRequest == null || versionRequest.getAt() == null) {
            versionRequest = VersionRequest.DEFAULT;
        }
        LocalDate at = versionRequest.getAt();
        List<EntryJpo> entryJpoList = em.createQuery("" +
                        "select e from Entry e " +
                        "where e.entity = :entity and e.applicableDate <= :at " +
                        "order by e.applicableDate, e.modificationDate ", EntryJpo.class)
                .setParameter("entity", entity)
                .setParameter("at", at)
                .getResultList();
        return EntryJpo.model(entryJpoList);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteEntry(String entity, String key, VersionRequest versionRequest) {
        if (versionRequest == null || versionRequest.getAt() == null) {
            versionRequest = VersionRequest.DEFAULT;
        }
        EntryJpo entryJpo = getEntryJpo(entity, key, versionRequest);

        if (entryJpo == null) {
            throw new IllegalArgumentException(String.format("Non existent {entity, key} = {%s,%s}", entity, key));
        }

        Entry delEntry = Entry.deleteEntry(entryJpo.model());
        EntryJpo delEntryJpo = new EntryJpo(delEntry);
        em.persist(delEntryJpo);

        // delete all entries after the specific applicable date
        deleteEntries(entity, key, versionRequest);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void cleanupEntity(String entity) {
        em.createQuery("delete from Entry e where e.entity = :entity")
                .setParameter("entity", entity)
                .executeUpdate();
    }

    private void deleteEntries(String entity, String key, VersionRequest versionRequest) {
        if (versionRequest == null || versionRequest.getAt() == null) {
            versionRequest = VersionRequest.DEFAULT;
        }
        LocalDate at = versionRequest.getAt();
        String keyHash = EntryKey.keyHash(key);
        List<EntryJpo> entryJpoList = em.createQuery("" +
                        "select e from Entry e " +
                        "  where e.entity = :entity and e.keyHash = :keyHash and e.applDate <= :at ", EntryJpo.class)
                .setParameter("entity", entity)
                .setParameter("keyHash", keyHash)
                .setParameter("at", at)
                .getResultList();

        // delete 1-1 all entries above the specified date
        entryJpoList.forEach(entryJpo -> em.remove(entryJpo));
    }

    private EntryJpo getEntryJpo(String entity, String key, VersionRequest versionRequest) {
        if (versionRequest == null || versionRequest.getAt() == null) {
            versionRequest = VersionRequest.DEFAULT;
        }
        LocalDate at = versionRequest.getAt();
        String keyHash = EntryKey.keyHash(key);
        EntryJpo entryJpo = em.createQuery("" +
                        "select e from Entry e " +
                        "  where e.entity = :entity and e.keyHash = :keyHash and e.applicableDate <= :at " +
                        "  order by e.applicableDate, e.modificationDate DESC ", EntryJpo.class)
                .setParameter("entity", entity)
                .setParameter("keyHash", keyHash)
                .setParameter("at", at)
                .setMaxResults(1)
                .getResultList().stream()
                .filter(rec -> rec.getKey().equals(key)) // handle hash collisions
                .findFirst().orElse(null);

        // since results are ordered by applicable date ascending and then by modification date descending, the first result
        // is the latest modified version of the closest applicable date

        return entryJpo;
    }

}
