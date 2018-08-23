package casa.com.yoquiero.interfaces;

import android.content.Context;

/**
 * Created by jose_ on 12/08/2018.
 */

public interface IAddAction {

    interface OnValidDataListener{
        void onErrorData();
    }

    interface View{
        void showDataError();
    }

    interface Presenter{
        void addActionData(String title, String urlImage, String description);
    }

    interface Interactor{
        void addActionData(String title, String urlImage, String description, IAddAction.OnValidDataListener listener, Context context);
    }
}
