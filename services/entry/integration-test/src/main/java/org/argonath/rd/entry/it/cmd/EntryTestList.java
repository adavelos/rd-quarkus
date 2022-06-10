package org.argonath.rd.entry.it.cmd;

import org.argonath.rd.entry.it.EntryTestJAXRS;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "list")
public class EntryTestList implements Runnable {

    @CommandLine.Parameters(index = "0", paramLabel = "ENTITY", description = "Entity Name")
    private String entity;


    @Inject
    EntryTestJAXRS entryTest;

    @Override
    public void run() {
        entryTest.list(entity);
    }

}
