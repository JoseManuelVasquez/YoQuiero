package casa.com.yoquiero.interactor;

import android.content.Context;

import java.util.List;

import casa.com.yoquiero.interfaces.IModifyAction;
import casa.com.yoquiero.model.Action;
import casa.com.yoquiero.model.YoQuieroDatabase;

public class ModifyActionInteractor implements IModifyAction.Interactor {

    @Override
    public void modifyActionData(String previousTitle, String title, String urlImage, String description, IModifyAction.OnModifyListener listener, Context context)
    {

        if (title.equals("") || urlImage.equals("") || description.equals("") || existsAction(title, context))
        {
            listener.onErrorData();
        }
        else
        {
            /* We delete the previous action */
            Action action = new Action();
            action.setAction(previousTitle);
            YoQuieroDatabase.getAppDatabase(context).actionDao().deleteAction(action);

            /* Replace this action from DB */
            action.setAction(title);
            action.setUrlImage(urlImage);
            action.setDescription(description);

            YoQuieroDatabase.getAppDatabase(context).actionDao().insertAction(action);
        }
    }

    /**
     * Method for looking an existing action by title
     * @return boolean
     */
    private boolean existsAction(String title, Context context)
    {
        List<Action> actions = YoQuieroDatabase.getAppDatabase(context).actionDao().getAll();
        for(Action action: actions)
        {
            if(action.getAction().equals(title))
                return true;
        }
        return false;
    }
}
