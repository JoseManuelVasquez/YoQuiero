package casa.com.yoquiero.presenter;

import casa.com.yoquiero.interactor.AddActionInteractor;
import casa.com.yoquiero.interfaces.IAddAction;
import casa.com.yoquiero.view.AddActionActivity;

public class AddActionPresenter implements IAddAction.Presenter, IAddAction.OnValidDataListener {

    /* AddAction View and Interactor */
    private IAddAction.View view;
    private IAddAction.Interactor interactor;

    public AddActionPresenter(IAddAction.View view)
    {
        this.view = view;
        interactor = new AddActionInteractor();
    }

    /* Methods called by View */
    @Override
    public void addActionData(String title, String urlImage, String description) {
        interactor.addActionData(title, urlImage, description, this, ((AddActionActivity)view).getApplicationContext());
    }

    /* Methods called my Interactor */
    @Override
    public void onErrorData() {
        view.showDataError();
    }
}
