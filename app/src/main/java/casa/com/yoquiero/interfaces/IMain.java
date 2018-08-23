package casa.com.yoquiero.interfaces;

/**
 * Created by jose_ on 12/08/2018.
 */

public interface IMain {

    interface OnSuccessChargeListener{
        void onSuccess();
        void onFailure();
    }

    interface View{
        void goToWanna();
    }

    interface Presenter{
        void goToWanna();
    }

    interface Interactor{
        void goToWanna(IMain.OnSuccessChargeListener listener);
    }
}
