package io.github.riiimc.riiilib.mixinconfig;

import com.sun.tools.attach.AgentLoadException;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import io.github.riiimc.riiilib.agent.AgentLoader;
import io.github.riiimc.riiilib.transformer.RiiiLibTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigSource;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

// TODO: Implement
public class EarlyLoadMixinConfig implements IMixinConfigPlugin {
    private static boolean registered = false;
    static {
        try {
            if (!registered)
            {
                AgentLoader.load();
                registered = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Agent", e);
        }
    }
    @Override
    public void onLoad(String s) {
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String s, String s1) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {
        RiiiLibTransformer.transform(ILaunchPluginService.Phase.AFTER, classNode);
    }
}
