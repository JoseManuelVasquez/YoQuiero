package casa.com.yoquiero.presenter;

import casa.com.yoquiero.interactor.WannaInteractor;
import casa.com.yoquiero.interfaces.IWanna;
import casa.com.yoquiero.view.WannaActivity;

public class WannaPresenter implements IWanna.Presenter, IWanna.OnValidDataListener {

    /* Wanna View and Interactor */
    private IWanna.View view;
    private IWanna.Interactor interactor;

    public WannaPresenter(IWanna.View view)
    {
        this.view = view;
        interactor = new WannaInteractor();
    }

    /* Presenter Methods */
    @Override
    public void showCurrentActions() {
        interactor.showCurrentActions(this, ((WannaActivity)view).getApplicationContext());
    }

    @Override
    public void addNewAction() {
        interactor.addNewAction(this);
    }

    @Override
    public void showAction(String title) {
        interactor.showAction(this, title);
    }

    /* Listener Methods */
    @Override
    public void onAddAction() {
        view.goToAddAction();
    }

    @Override
    public void onAddActionView(String title, String urlImage) {
        view.addActionView(title, urlImage);
    }

    @Override
    public void onShowAction(String title) {
        view.showAction(title);
    }

    @Override
    public void onNonEmptyAction() {
        view.hideEmptyJoke();
    }

    @Override
    public void onEmptyAction() {
        view.showEmptyJoke();
    }
}
