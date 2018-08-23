package casa.com.yoquiero.interfaces;

import android.app.Activity;
import android.content.Context;

public interface IAction {

    interface OnDataListener{
        void onDataTitle(String title);
        void onDataImage(String urlImage);
        void onDataDescription(String description);
        void onDataAudio();
        void onAudioRecorded();
        void onAudioPlayed();
        void onAudioCleared();
        void onActionDeleted();
        void onModifyActionRequest(String title, String urlImage, String description);
    }

    interface View{
        void goToModify(String title, String urlImage, String description);
        void showTitle(String title);
        void showImage(String urlImage);
        void showDescription(String description);
        void showAudioIcon();
        void showPlay();
        void showClear();
        void showStopAnimation();
        void hidePlay();
        void hideClear();
    }

    interface Presenter{
        void getActionData(String title);
        void startRecordingAudio(String title);
        void stopRecordingAudio();
        void playAudio();
        void stopAudio();
        void requestPermissionTask(int requestCode, String permissions[], int[] grantResults);
        void clearAudio();
        void deleteAction(String title);
        void modifyAction(String title);
    }

    interface Interactor{
        void getActionData(IAction.OnDataListener listener, Context context, String title);
        void startRecordingAudio(IAction.OnDataListener listener, Activity activity, String title);
        void stopRecordingAudio(IAction.OnDataListener listener, Context context);
        void playAudio(IAction.OnDataListener listener, Context context);
        void stopAudio(IAction.OnDataListener listener);
        void requestPermissionTask(IAction.OnDataListener listener, int requestCode, String permissions[], int[] grantResults, Activity activity);
        void clearAudio(IAction.OnDataListener listener);
        void deleteAction(IAction.OnDataListener listener, Context context, String title);
        void modifyAction(IAction.OnDataListener listener, Context context, String title);
    }
}
