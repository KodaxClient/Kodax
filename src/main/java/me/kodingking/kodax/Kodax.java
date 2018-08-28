package me.kodingking.kodax;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.List;
import java.util.UUID;
import me.kodingking.kodax.api.KodaxApi;
import me.kodingking.kodax.command.Command;
import me.kodingking.kodax.config.ConfigManager;
import me.kodingking.kodax.config.SaveVal;
import me.kodingking.kodax.controllers.HandlerController;
import me.kodingking.kodax.event.EventBus;
import me.kodingking.kodax.event.events.ShutdownEvent;
import me.kodingking.kodax.manifests.ClientManifest;
import me.kodingking.kodax.netty.NettyEventListener;
import me.kodingking.kodax.netty.NettyListener;
import me.kodingking.kodax.utils.HttpUtils;
import me.kodingking.kodax.utils.Multithreading;
import me.kodingking.kodax.utils.Scheduler;
import me.kodingking.netty.client.MinecraftSettings;
import me.kodingking.netty.client.NettyClient;
import me.kodingking.netty.packet.impl.projects.kodax.CPacketAnnounce;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class Kodax {

  public static Kodax INSTANCE = new Kodax();
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
    logger.info("Starting Kodax pre-initialisation...");

    SplashScreen.advanceProgress("Pre-loading Kodax...");

    FOLDER = new File(Minecraft.getMinecraft().mcDataDir, "Kodax");
    if (!FOLDER.exists()) {
      FOLDER.mkdirs();
    }
    CONFIG = new ConfigManager(new File(FOLDER, "config.json"));

    CONFIG.register(this);
    CONFIG.register(settings);

    logger.info("Registering events...");
    EventBus.register(INSTANCE);
    EventBus.register(SCHEDULER);
    EventBus.register(new MainListener());
//        EVENT_BUS.register(new OverlayRenderer());

    logger.info("Adding shutdown hook...");
    Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

    logger.info("Fetching client manifest...");

    FULL_MANIFEST = ClientManifest.fetch(
        "https://raw.githubusercontent.com/KodaxClient/Kodax-Repo/master/client_manifest.json");

    logger.info("Registering handlers...");
    handlerController.registerAll();

    if (FIRST_LAUNCH) {
      FIRST_LAUNCH = false;
    }

    logger.info("Connecting to netty...");
    Multithreading.run(() -> {
      String accessToken = Minecraft.getMinecraft().getSession().getToken();
      String username = Minecraft.getMinecraft().getSession().getUsername();
      UUID uuid = Minecraft.getMinecraft().getSession().getProfile().getId();

      try {
        NettyClient.init(me.kodingking.netty.Constants.SERVER_IP,
            me.kodingking.netty.Constants.SERVER_PORT,
            new MinecraftSettings(accessToken, username, uuid), true, () -> {
              EventBus.register(new NettyEventListener());
              NettyClient.registerPacketHandler(new NettyListener());
            });
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    if (Minecraft.getMinecraft().getSession() != null
        && Minecraft.getMinecraft().getSession().getProfile() != null
        && Minecraft.getMinecraft().getSession().getProfile().getId() != null) {
      try {
        String json = HttpUtils.get(
            "http://api.kodingking.com/kodax/endpoints/user.php?UUID=" + Minecraft
                .getMinecraft()
                .getSession().getProfile().getId().toString());
        if (!json.isEmpty()) {
          JsonObject apiObj = new JsonParser().parse(json)
              .getAsJsonObject();
          if (apiObj.has("admin") && apiObj.get("admin").getAsInt() == 1) {
            logger.info(
                "Detected admin user with UUID: " + Minecraft.getMinecraft().getSession()
                    .getProfile()
                    .getId().toString());

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
                NettyClient.sendPacket(new CPacketAnnounce(message));
              }
            });
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    logger.info("Starting API thread...");
    KodaxApi.init();

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
    EventBus.call(new ShutdownEvent());

    logger.info("Saving config...");
    CONFIG.save();

    logger.info("Shutting down netty...");
    NettyClient.shutdown();

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
