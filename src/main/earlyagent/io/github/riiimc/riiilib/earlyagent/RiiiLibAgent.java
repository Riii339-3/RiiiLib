package io.github.riiimc.riiilib.earlyagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class RiiiLibAgent {

    public static void agentmain(
            String agentArgs,
            Instrumentation instrumentation
    ) {
        instrumentation.addTransformer(
                new RiiiLibTransformer(),
                true
        );
        System.out.println("[RiiiLib] EarlyAgent loaded!");
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if ("net.minecraft.world.entity.LivingEntity".equals(clazz.getName())
                    && instrumentation.isModifiableClass(clazz)) {
                try {
                    instrumentation.retransformClasses(clazz);
                    System.out.println("[RiiiLib] LivingEntity retransformed!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}