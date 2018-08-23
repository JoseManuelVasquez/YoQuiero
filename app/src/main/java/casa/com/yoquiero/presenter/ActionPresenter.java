package casa.com.yoquiero.presenter;

import casa.com.yoquiero.interactor.ActionInteractor;
import casa.com.yoquiero.interfaces.IAction;
import casa.com.yoquiero.view.ActionActivity;

public class ActionPresenter implements IAction.Presenter, IAction.OnDataListener {

    /* Action View & Action Interactor */
    private IAction.View view;
    private IAction.Interactor interactor;

    public ActionPresenter(IAction.View view)
    {
        this.view = view;
        interactor = new ActionInteractor();
    }

    /* Methods called by view */
    @Override
    public void getActionData(String title)
    {
        interactor.getActionData(this, ((ActionActivity)view).getApplicationContext(), title);
    }

    @Override
    public void startRecordingAudio(String title)
    {
        interactor.startRecordingAudio(this, ((ActionActivity)view), title);
        view.hidePlay();
        view.hideClear();
    }

    @Override
    public void stopRecordingAudio()
    {
        interactor.stopRecordingAudio(this, ((ActionActivity)view).getApplicationContext());
        view.showPlay();
        view.showClear();
    }

    @Override
    public void playAudio()
    {
        interactor.playAudio(this, ((ActionActivity)view).getApplicationContext());
        ((ActionActivity)view).playPressed = true;
    }

    @Override
    public void stopAudio()
    {
        interactor.stopAudio(this);
        ((ActionActivity)view).playPressed = false;
    }

    @Override
    public void requestPermissionTask(int requestCode, String[] permissions, int[] grantResults)
    {
        interactor.requestPermissionTask(this, requestCode, permissions, grantResults, ((ActionActivity)view));
    }

    @Override
    public void clearAudio()
    {
        interactor.clearAudio(this);
    }

    @Override
    public void deleteAction(String title)
    {
        interactor.deleteAction(this, ((ActionActivity)view).getApplicationContext(), title);
    }

    @Override
    public void modifyAction(String title)
    {
        interactor.modifyAction(this, ((ActionActivity)view).getApplicationContext(), title);
    }

    /* Methods called by interactor */
    @Override
    public void onDataTitle(String title)
    {
        view.showTitle(title);
    }

    @Override
    public void onDataImage(String urlImage) {
        view.showImage(urlImage);
    }

    @Override
    public void onDataDescription(String description) {
        view.showDescription(description);
    }

    @Override
    public void onDataAudio() {
        view.showAudioIcon();
    }

    @Override
    public void onAudioRecorded()
    {
        view.showPlay();
        view.showClear();
    }

    @Override
    public void onAudioPlayed()
    {
        if(((ActionActivity)view).playPressed)
            view.showStopAnimation();
        ((ActionActivity)view).playPressed = false;
    }

    @Override
    public void onAudioCleared()
    {
        view.hideClear();
        view.hidePlay();
    }

    @Override
    public void onActionDeleted()
    {
        ((ActionActivity)view).finish();
    }

    @Override
    public void onModifyActionRequest(String title, String urlImage, String description) {
        view.goToModify(title, urlImage, description);
    }
}
