package casa.com.yoquiero.interactor;

import android.content.Context;

import java.util.List;

import casa.com.yoquiero.interfaces.IWanna;
import casa.com.yoquiero.model.Action;
import casa.com.yoquiero.model.YoQuieroDatabase;


public class WannaInteractor implements IWanna.Interactor {
    @Override
    public void showCurrentActions(IWanna.OnValidDataListener listener, Context context) {
        List<Action> actions = YoQuieroDatabase.getAppDatabase(context).actionDao().getAll();

        if(actions.isEmpty())
            listener.onEmptyAction();
        else
            listener.onNonEmptyAction();

        for(Action action: actions)
        {
            listener.onAddActionView(action.getAction(), action.getUrlImage());
        }
    }

    @Override
    public void addNewAction(IWanna.OnValidDataListener listener) {
        listener.onAddAction();
    }

    @Override
    public void showAction(IWanna.OnValidDataListener listener, String title) {
        listener.onShowAction(title);
    }
}
