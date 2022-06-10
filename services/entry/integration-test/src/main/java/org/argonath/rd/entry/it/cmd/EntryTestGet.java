package org.argonath.rd.entry.it.cmd;

import org.argonath.rd.entry.it.EntryTestHttpClient;
import org.argonath.rd.entry.it.EntryTestJAXRS;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "get")
public class EntryTestGet implements Runnable {

    @CommandLine.Parameters(index = "0", paramLabel = "ENTITY", description = "Entity Name")
    private String entity;

    @CommandLine.Parameters(index = "1", paramLabel = "KEY", description = "Entry Key")
    private String key;

    @Inject
    EntryTestHttpClient entryTest;

    @Override
    public void run() {
        entryTest.getEntry(entity, key);
    }

}
