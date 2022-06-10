package org.argonath.rd.entry.it.cmd;

import org.argonath.rd.entry.it.EntryTestJAXRS;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "create")
public class EntryTestCreate implements Runnable {

    @CommandLine.Option(names = {"-e", "--entity"}, required = true, description = "Entity Name")
    private String entity;

    @CommandLine.Option(names = {"-k", "--key"}, required = true, description = "Entry Key")
    private String key;

    @CommandLine.Option(names = {"-d", "--desc"}, required = true, description = "Entry Description")
    private String descrition;

    @Inject
    EntryTestJAXRS entryTest;

    @Override
    public void run() {
        entryTest.create(entity, key, descrition);
    }

}
