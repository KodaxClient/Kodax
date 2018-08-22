package me.kodingking.kodax;

import com.darkmagician6.eventapi.EventManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.swing.JOptionPane;
import jdk.nashorn.api.scripting.URLReader;
import me.kodingking.kodax.command.Command;
import me.kodingking.kodax.config.ConfigManager;
import me.kodingking.kodax.config.SaveVal;
import me.kodingking.kodax.controllers.HandlerController;
import me.kodingking.kodax.events.ShutdownEvent;
import me.kodingking.kodax.listeners.netty.NettyListener;
import me.kodingking.kodax.manifests.ClientManifest;
import me.kodingking.kodax.utils.Multithreading;
import me.kodingking.kodax.utils.Scheduler;
import me.kodingking.kodaxnetty.client.KodaxClient;
import me.kodingking.kodaxnetty.packet.AdminAnnouncePacket;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class Kodax {

  public static Kodax INSTANCE = new Kodax();
  public static EventManager EVENT_BUS = new EventManager();
  public static Scheduler SCHEDULER = new Scheduler();
  public static ConfigManager CONFIG;
  public static File FOLDER;
  public static ClientManifest FULL_MANIFEST;

  private Logger logger = LogManager.getLogger(getClientName());
  private HandlerController handlerController = new HandlerController();
  private GlobalSettings settings = new GlobalSettings();

  @SaveVal
  public boolean FIRST_LAUNCH = true;

  public void preStart() {
    logger.info("Starting Project XENON pre-initialisation...");

    SplashScreen.advanceProgress("Pre-loading Project XENON...");

    FOLDER = new File(Minecraft.getMinecraft().mcDataDir, "ProjectXENON");
    if (!FOLDER.exists()) {
      FOLDER.mkdirs();
    }
    CONFIG = new ConfigManager(new File(FOLDER, "config.json"));

    CONFIG.register(this);
    CONFIG.register(settings);

    logger.info("Registering events...");
    EVENT_BUS.register(INSTANCE);
    EVENT_BUS.register(SCHEDULER);
    EVENT_BUS.register(new MainListener());
//        EVENT_BUS.register(new OverlayRenderer());

    logger.info("Adding shutdown hook...");
    Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

    logger.info("Fetching client manifest...");

    try {
      FULL_MANIFEST = ClientManifest.fetch(new URL(
          "https://raw.githubusercontent.com/KodaxClient/Kodax-Repo/master/client_manifest.json"));
    } catch (MalformedURLException e) {
      e.printStackTrace();

      JOptionPane
          .showMessageDialog(null, "Manifest URL incorrect, Minecraft will now exit.", "FATAL",
              JOptionPane.ERROR_MESSAGE);

      System.exit(0);
      return;
    }

    logger.info("Registering handlers...");
    handlerController.registerAll();

    if (FIRST_LAUNCH) {
      FIRST_LAUNCH = false;
    }

    logger.info("Connecting to netty...");
    Multithreading.run(() -> {
      try {
        KodaxClient
            .init("104.128.228.219", 9575, () -> KodaxClient.registerListener(new NettyListener()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    try {
      if (Minecraft.getMinecraft().getSession() != null
          && Minecraft.getMinecraft().getSession().getProfile() != null
          && Minecraft.getMinecraft().getSession().getProfile().getId() != null) {
        JsonObject apiObj = new JsonParser().parse(new URLReader(new URL(
            "http://api.kodingking.com/kodax/endpoints/user.php?UUID=" + Minecraft.getMinecraft()
                .getSession().getProfile().getId().toString())))
            .getAsJsonObject();
        if (apiObj.has("admin") && apiObj.get("admin").getAsInt() == 1) {
          handlerController.getCommandHandler().registerCommand(new Command() {
            @Override
            public String getName() {
              return "kodax_admin";
            }

            @Override
            public String getUsage() {
              return "kodax_admin";
            }

            @Override
            public void onCommand(String[] args) {
              if (args.length == 0) {
                return;
              }

              String message = String.join(" ", args);
              KodaxClient.sendPacket(new AdminAnnouncePacket(
                  Minecraft.getMinecraft().getSession().getProfile().getId(), message));
            }
          });
        }
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    logger.info("Done.");
  }

  public void postStart() {
    logger.info("Starting " + getClientName() + " post-initialisation...");

    SplashScreen.advanceProgress("Post-loading " + getClientName() + "...");

    logger.info("Loading config...");
    CONFIG.load();

    handlerController.getKeybindHandler().loadPrevBinds();

    logger.info("Done.");

    Display.setTitle(getDisplayTitle());
  }

  public void shutdown() {
    logger.info("Shutting down " + getClientName() + "...");

    logger.info("Posting shutdown event...");
    EVENT_BUS.call(new ShutdownEvent());

    logger.info("Saving config...");
    CONFIG.save();

    logger.info("Done.");
  }

  public Logger getLogger() {
    return logger;
  }

  public String getClientName() {
    return Constants.CLIENT_NAME;
  }

  public String getClientVersion() {
    return Constants.CLIENT_VERSION;
  }

  public List<String> getClientAuthors() {
    return Constants.CLIENT_AUTHORS;
  }

  public String getDisplayTitle() {
    return Constants.CLIENT_NAME + " // " + Constants.CLIENT_VERSION;
  }

  public GlobalSettings getSettings() {
    return settings;
  }

  public HandlerController getHandlerController() {
    return handlerController;
  }
}
