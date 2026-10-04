package io.github.riiimc.riiilib.service;

import cpw.mods.jarhandling.JarContents;
import net.neoforged.neoforgespi.locating.*;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public class RiiiLibDependencyLocator implements IDependencyLocator {
    @Override
    public void scanMods(List<IModFile> loadedMods, IDiscoveryPipeline pipeline) {
        URL mainModURL = this.getClass().getClassLoader().getResource("META-INF/riiilib/mod.jar");

        if (mainModURL == null) {
            throw new Error();
        } else {
            try {
                //LOGGER.info("[EnumExtenderJS] try load mainMod form:{}", Path.of(mainModURL.toURI()).toAbsolutePath());
                pipeline.addJarContent(JarContents.of(Path.of(mainModURL.toURI())), ModFileDiscoveryAttributes.DEFAULT, IncompatibleFileReporting.WARN_ALWAYS);
            } catch (URISyntaxException e) {
                //LOGGER.error(e.toString());
                throw new RuntimeException(e);
            }
        }
    }
}
