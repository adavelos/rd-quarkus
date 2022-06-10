package org.argonath.rd.entry.it.cmd;

import org.argonath.rd.entry.it.EntryTestJAXRS;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "random")
public class EntryTestRandomGenerator implements Runnable {

    @CommandLine.Option(names = {"-e", "--entity"}, required = true, description = "Entity Name")
    private String entity;

    @CommandLine.Option(names = {"-k", "--keyPattern"}, required = true, description = "Key Pattern")
    private String keyPattern;

    @CommandLine.Option(names = {"-d", "--descPattern"}, required = true, description = "Description Pattern")
    private String descPattern;

    @CommandLine.Option(names = {"-c", "--count"}, required = true, description = "Entry Count")
    private int count;

    @Inject
    EntryTestJAXRS entryTest;

    @Override
    public void run() {
        entryTest.randomGenearte(entity, keyPattern, descPattern, count);
    }

}
