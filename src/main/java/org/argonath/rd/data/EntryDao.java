package org.argonath.rd.data;

import org.argonath.rd.model.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@Dependent
public class EntryDao {

    @Inject
    private EntityManager em;

    public void createEntry(Entry entry) {
        EntryJpo entryJpo = new EntryJpo(entry);
        EntryJpo check = getEntryJpo(entry.getEntity(), entry.getKey());
        if (check != null) {
            throw new IllegalArgumentException("Entry already exists");
        }
        em.persist(entryJpo);
    }

    public void updateEntry(String entity, String key, Entry entry) {
        assert entity.equals(entry.getEntity());
        assert key.equals(entry.getKey());
        EntryJpo entryJpo = getEntryJpo(entity, key);
        entryJpo.update(entry);
        em.merge(entryJpo);
    }

    public Entry get(String entity, String key) {
        EntryJpo entryJpo = getEntryJpo(entity, key);
        return entryJpo != null ? entryJpo.model() : null;
    }

    public List<Entry> list(String entity) {
        List<EntryJpo> entryJpo = em.createQuery("select e from Entry e where e.entity = :entity", EntryJpo.class)
                .setParameter("entity", entity)
                .getResultList();
        return EntryJpo.model(entryJpo);
    }

    public void deleteEntry(String entity, String key) {
        EntryJpo entryJpo = getEntryJpo(entity, key);
        if (entryJpo == null) {
            throw new IllegalArgumentException(String.format("Non existent entry with {%s,%s}", entity, key));
        }
        em.remove(entryJpo);
    }

    private EntryJpo getEntryJpo(String entity, String key) {
        try {
            EntryJpo entryJpo = em.createQuery("select e from Entry e where e.entity = :entity and e.key = :key", EntryJpo.class)
                    .setParameter("entity", entity)
                    .setParameter("key", key)
                    .getSingleResult();
            return entryJpo;
        } catch (NonUniqueResultException e) {
            throw new RuntimeException("Multiple Results where Single Result expected");
        } catch (NoResultException e) {
            return null;
        }
    }
}
