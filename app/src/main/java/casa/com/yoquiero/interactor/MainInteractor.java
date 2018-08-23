package casa.com.yoquiero.interactor;

import casa.com.yoquiero.interfaces.IMain;

/**
 * Created by jose_ on 13/08/2018.
 */

public class MainInteractor implements IMain.Interactor {
    @Override
    public void goToWanna(IMain.OnSuccessChargeListener listener) {
        // It's just an example, MVP is not necessary in this case
        listener.onSuccess();
    }
}
