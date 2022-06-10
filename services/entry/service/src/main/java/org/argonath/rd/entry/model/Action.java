package org.argonath.rd.entry.model;

/**
 * Describes the action applied on the current data record. In case of a deleted (tombstone) record, the action is 'D'.
 */
public enum Action {
    U, /* update */
    D  /* delete */
}
