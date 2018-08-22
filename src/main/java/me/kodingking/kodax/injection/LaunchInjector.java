package me.kodingking.kodax.injection;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaunchInjector implements ITweaker {

    private List<String> newArgs;

//    private boolean usingOptiFine;

    public LaunchInjector() {
//      usingOptiFine = Launch.classLoader.getTransformers().stream().anyMatch(iClassTransformer -> iClassTransformer.getClass().getName().equalsIgnoreCase("optifine.OptiFineTweaker"));
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        newArgs = new ArrayList<>(args);
        if (gameDir != null)
            newArgs.addAll(Arrays.asList("--gameDir", gameDir.getAbsolutePath()));
        if (assetsDir != null)
            newArgs.addAll(Arrays.asList("--assetsDir", assetsDir.getAbsolutePath()));
        if (profile != null)
            newArgs.addAll(Arrays.asList("--version", profile));
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
//        System.out.println("Using Optifine: " + optifine);
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.kodax.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
//        if (usingOptiFine)
//            return new String[0];
        return newArgs.toArray(new String[0]);
    }

}
