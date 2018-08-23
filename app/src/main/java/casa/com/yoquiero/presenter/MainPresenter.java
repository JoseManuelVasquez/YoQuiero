package casa.com.yoquiero.presenter;

import casa.com.yoquiero.interactor.MainInteractor;
import casa.com.yoquiero.interfaces.IMain;

/**
 * Created by jose_ on 10/08/2018.
 */

public class MainPresenter implements IMain.Presenter, IMain.OnSuccessChargeListener{

    /* Main View and Main Interactor */
    private IMain.View view;
    private IMain.Interactor interactor;

    public MainPresenter(IMain.View view)
    {
        this.view = view;
        interactor = new MainInteractor();
    }

    /* Methods Called by View */
    public void goToWanna()
    {
        interactor.goToWanna(this);
    }

    /* Methods Called by Interactor */
    @Override
    public void onSuccess() {
        view.goToWanna();
    }

    @Override
    public void onFailure() {
        //
    }
}
