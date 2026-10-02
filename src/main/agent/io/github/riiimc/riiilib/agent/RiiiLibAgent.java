package io.github.riiimc.riiilib.agent;

import java.lang.instrument.Instrumentation;

public class RiiiLibAgent {

    public static void agentmain(
            String agentArgs,
            Instrumentation instrumentation
    ) {
        System.out.println("[RiiiLib] Agent loaded!");
    }
}