package weslei.com.br.applojavirtual;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by weslei on 10/01/2017.
 */

/**
 * Neste Classe extendemos da classe Application para poder dizer o comportamento
 * de nossa aplicação antes mesmo que qualquer activity seja chamada. Application é a classe pai de todas as Actititys
  */
public class DevMediaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
    }
}
