package org.argonath.rd.entry.it.cmd;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import org.argonath.rd.entry.it.EntryTestJAXRS;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@TopCommand
@Command(name = "entryTest", description = "Entry API Test",
        subcommands = {EntryTestGet.class, EntryTestList.class, EntryTestCreate.class, EntryTestCleanup.class, EntryTestRandomGenerator.class})
public class EntryTestMain implements Runnable {

    @Inject
    EntryTestJAXRS entryClient;

    @Override
    public void run() {
        //cleanup();
    }

}
