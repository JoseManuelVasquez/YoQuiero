package casa.com.yoquiero.interfaces;

import android.content.Context;

public interface IModifyAction {

    interface OnModifyListener{
        void onModifiedData();
        void onErrorData();
    }

    interface View{
        void showDataError();
    }

    interface Presenter{
        void modifyActionData(String previousTitle, String title, String urlImage, String description);
    }

    interface Interactor{
        void modifyActionData(String previousTitle, String title, String urlImage, String description, IModifyAction.OnModifyListener listener, Context context);
    }
}
