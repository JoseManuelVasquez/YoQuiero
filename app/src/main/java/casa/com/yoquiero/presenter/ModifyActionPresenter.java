package casa.com.yoquiero.presenter;

import casa.com.yoquiero.interactor.ModifyActionInteractor;
import casa.com.yoquiero.interfaces.IModifyAction;
import casa.com.yoquiero.view.ModifyActionActivity;

public class ModifyActionPresenter implements IModifyAction.Presenter, IModifyAction.OnModifyListener {

    /* View and Interactor */
    private IModifyAction.View view;
    private IModifyAction.Interactor interactor;

    public ModifyActionPresenter(IModifyAction.View view)
    {
        this.view = view;
        interactor = new ModifyActionInteractor();
    }

    @Override
    public void modifyActionData(String previousTitle, String title, String urlImage, String description) {
        interactor.modifyActionData(previousTitle, title, urlImage, description, this, ((ModifyActionActivity)view).getApplicationContext());
    }

    @Override
    public void onModifiedData() {
        //Do Something
    }

    @Override
    public void onErrorData() {
        view.showDataError();
    }
}
