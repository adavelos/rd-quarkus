package org.argonath.rd.entry.rest.api;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

public class VersionRequestJso {

    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate at;

    public LocalDate getAt() {
        return at;
    }

    public void setAt(LocalDate at) {
        this.at = at;
    }

}
