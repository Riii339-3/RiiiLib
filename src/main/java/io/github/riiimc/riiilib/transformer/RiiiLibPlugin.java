package io.github.riiimc.riiilib.transformer;

import cpw.mods.modlauncher.api.ITransformerActivity;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.util.EnumSet;

public class RiiiLibPlugin implements ILaunchPluginService {
    @Override
    public String name() {
        return "riiilib";
    }

    @Override
    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        if (classType.getClassName().startsWith("io/github/riiimc/riiilib/transformer")) {
            return EnumSet.noneOf(Phase.class);
        }
        return EnumSet.of(Phase.AFTER, Phase.BEFORE);
    }

    @Override
    public int processClassWithFlags(Phase phase, ClassNode classNode, Type classType, String reason) {
        if (classNode.name.startsWith("io/github/riiimc/riiilib/transformer"))
            return ComputeFlags.NO_REWRITE;
        if (!reason.equals(ITransformerActivity.CLASSLOADING_REASON))
            return ComputeFlags.NO_REWRITE;
        return RiiiLibTransformer.transform(phase, classNode);
    }
}
