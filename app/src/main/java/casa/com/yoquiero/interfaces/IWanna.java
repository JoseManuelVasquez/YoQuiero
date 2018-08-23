package casa.com.yoquiero.interfaces;

import android.content.Context;

/**
 * Created by jose_ on 12/08/2018.
 */

public interface IWanna {

    interface OnValidDataListener{
        void onAddAction();
        void onAddActionView(String title, String urlImage);
        void onShowAction(String title);
        void onNonEmptyAction();
        void onEmptyAction();
    }

    interface View{
        void goToAddAction();
        void showAction(String title);
        void addActionView(String title, String urlImage);
        void showEmptyJoke();
        void hideEmptyJoke();
    }

    interface Presenter{
        void showCurrentActions();
        void addNewAction();
        void showAction(String title);
    }

    interface Interactor{
        void showCurrentActions(IWanna.OnValidDataListener listener, Context context);
        void addNewAction(IWanna.OnValidDataListener listener);
        void showAction(IWanna.OnValidDataListener listener, String title);
    }
}
