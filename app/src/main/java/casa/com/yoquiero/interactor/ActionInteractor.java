package casa.com.yoquiero.interactor;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import casa.com.yoquiero.constants.Constants;
import casa.com.yoquiero.interfaces.IAction;
import casa.com.yoquiero.model.Action;
import casa.com.yoquiero.model.YoQuieroDatabase;

public class ActionInteractor implements IAction.Interactor {

    /* Action Audio control */
    private String recordingName, recordingPath;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private File fileOutput;

    @Override
    public void getActionData(IAction.OnDataListener listener, Context context, String title)
    {
        if(!title.equals(""))
        {
            List<Action> actions = YoQuieroDatabase.getAppDatabase(context).actionDao().getAll();

            for(Action action: actions)
            {
                /* If we found the action, we stop searching */
                if(action.getAction().equals(title))
                {
                    listener.onDataTitle(action.getAction());
                    listener.onDataImage(action.getUrlImage());
                    listener.onDataDescription(action.getDescription());
                    listener.onDataAudio();

                    recordingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            Constants.DIR_NAME_RECORD;

                    recordingName = recordingPath + "/" + title + Constants.FILE_RECORD_EXTENSION;

                    fileOutput = new File(recordingName);
                    if(fileOutput.exists())
                    {
                        listener.onAudioRecorded();
                    }

                    return;
                }
            }
        }
    }

    @Override
    public void startRecordingAudio(IAction.OnDataListener listener, Activity activity, String title)
    {
        if(checkPermission(activity.getApplicationContext())) {

            recordingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                    Constants.DIR_NAME_RECORD;

            recordingName = recordingPath + "/" + title + Constants.FILE_RECORD_EXTENSION;

            fileOutput = new File(recordingPath);
            if(!fileOutput.exists())
            {
                fileOutput.mkdirs();
            }

            /* Recorder Ready */
            if(mediaRecorder == null)
            {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                mediaRecorder.setOutputFile(recordingName);
                // It depends of device
                //mediaRecorder.setAudioEncodingBitRate(320000);
                //mediaRecorder.setAudioSamplingRate(44100);
            }

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            }
            catch(IllegalStateException ex)
            {
                Log.d("IllegalStateError","Recorder failed - " + ex.getMessage());
            }
            catch (IOException ex)
            {
                Log.d("IOError","Recorder file ERROR - " + ex.getMessage());
            }

        }
        else
        {
            requestPermission(activity);
        }
    }

    @Override
    public void stopRecordingAudio(IAction.OnDataListener listener, Context context)
    {
        if(mediaRecorder != null)
        {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    @Override
    public void playAudio(final IAction.OnDataListener listener, Context context)
    {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(recordingName);
            mediaPlayer.prepare();
        }
        catch (IOException e) {

        }

        /* How long the audio is */
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(recordingName);
        final String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        mmr.release();

        /* Start Recording */
        mediaPlayer.start();

        /* After playing recording */
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onAudioPlayed();
            }
        }, Integer.parseInt(duration));

    }

    @Override
    public void stopAudio(IAction.OnDataListener listener)
    {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        listener.onAudioPlayed();
    }

    /* Permission methods */
    @Override
    public void requestPermissionTask(IAction.OnDataListener listener, int requestCode, String[] permissions, int[] grantResults, Activity activity)
    {
        switch(requestCode)
        {
            case Constants.REQUEST_PERMISSION_CODE:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission)
                    {
                        //Do something
                    }
                    else
                    {
                        //Do something
                    }
                }
                break;
        }
    }

    @Override
    public void clearAudio(IAction.OnDataListener listener)
    {
        fileOutput = new File(recordingName);
        if(fileOutput.exists())
        {
            fileOutput.delete();
        }
        listener.onAudioCleared();
    }

    @Override
    public void deleteAction(IAction.OnDataListener listener, Context context, String title)
    {
        /* Delete this action from DB */
        Action action = new Action();
        action.setAction(title);
        YoQuieroDatabase.getAppDatabase(context).actionDao().deleteAction(action);

        /* If there is a related audio */
        fileOutput = new File(recordingName);
        if(fileOutput.exists())
        {
            fileOutput.delete();
        }

        listener.onActionDeleted();
    }

    @Override
    public void modifyAction(IAction.OnDataListener listener, Context context, String title)
    {
        /* Searching from DB */
        List<Action> actions = YoQuieroDatabase.getAppDatabase(context).actionDao().getAll();

        for(Action action: actions)
        {
            if(action.getAction().equals(title))
            {
                listener.onModifyActionRequest(action.getAction(), action.getUrlImage(), action.getDescription());
                return;
            }
        }
    }

    /* Private Methods used by public methods */
    private boolean checkPermission(Context context)
    {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(Activity activity)
    {
        ActivityCompat.requestPermissions(activity,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.RECORD_AUDIO}, Constants.REQUEST_PERMISSION_CODE);
    }

}
