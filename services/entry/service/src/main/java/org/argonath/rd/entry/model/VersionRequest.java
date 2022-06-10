package org.argonath.rd.entry.model;

import java.time.LocalDate;

public class VersionRequest {

    public static final VersionRequest DEFAULT = new VersionRequest(LocalDate.now());

    private LocalDate at;

    public VersionRequest(LocalDate at) {
        this.at = at;
    }

    public VersionRequest() {
    }

    public LocalDate getAt() {
        return at;
    }

}
