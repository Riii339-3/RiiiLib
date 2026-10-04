package io.github.riiimc.riiilib.service;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class RiiiLibTransformationService implements ITransformationService {
    @Override
    public @NotNull String name() {
        return "riiilibservice";
    }

    @Override
    public void initialize(IEnvironment environment) {

    }

    @Override
    public void onLoad(IEnvironment env, Set<String> otherServices) throws IncompatibleEnvironmentException {
        System.out.println("Loading RiiiLib service");
        try {
            AgentLoader.load();
        } catch (Exception e) {
            System.err.println("Failed to load RiiiLib agent");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull List<? extends ITransformer<?>> transformers() {
        return List.of();
    }
}
