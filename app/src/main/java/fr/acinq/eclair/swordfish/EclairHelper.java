package fr.acinq.eclair.swordfish;

import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorRef;
import akka.actor.Props;
import fr.acinq.eclair.Setup;
import fr.acinq.eclair.channel.ChannelEvent;
import fr.acinq.eclair.payment.PaymentEvent;
import fr.acinq.eclair.router.NetworkEvent;

public class EclairHelper {
  private static final String TAG = "EclairHelper";
  private static EclairHelper mInstance = null;
  private Setup setup;
  private ActorRef guiUpdater;

  private EclairHelper() {}

  private EclairHelper(File f) {
    Log.i("Eclair Helper", "Accessing Eclair Setup with datadir in " + f.getAbsolutePath());
    File data = new File(f, "eclair-wallet-data");
    System.setProperty("eclair.node-alias", "sw-ripley");
    try {
      Setup setup = new Setup(data, "system");
      this.setup = setup;
      this.guiUpdater = this.setup.system().actorOf(Props.create(EclairEventService.class));
      setup.system().eventStream().subscribe(guiUpdater, ChannelEvent.class);
      setup.system().eventStream().subscribe(guiUpdater, PaymentEvent.class);
      setup.system().eventStream().subscribe(guiUpdater, NetworkEvent.class);
      setup.boostrap();
    } catch (Exception e) {
      Log.e(TAG, "Failed to start eclair", e);
    }
  }

  public static boolean hasInstance() {
    return mInstance != null;
  }

  public static EclairHelper getInstance(File f) {
    if (mInstance == null) {
      Class clazz = EclairHelper.class;
      synchronized (clazz) {
        mInstance = new EclairHelper(f);
      }
    }
    return mInstance;
  }

  public Setup getSetup() {
    return this.setup;
  }
}
