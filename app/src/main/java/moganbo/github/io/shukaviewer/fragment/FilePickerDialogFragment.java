package moganbo.github.io.shukaviewer.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import moganbo.github.io.shukaviewer.utils.FileInformation;
import moganbo.github.io.shukaviewer.utils.LogUtil;
import moganbo.github.io.shukaviewer.utils.OnFileSelectedListener;
import moganbo.github.io.shukaviewer.utils.StringUtil;

public class FilePickerDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    private static final String ROOT_DIRECTORY = "rootDirectory";
    private static final String INITIAL_DIRECTORY = "initialDirectory";
    private static final String PREVIOUS = "previous";
    private static final String CANCEL_LABEL = "cancelLabel";
    private static final String PARAMS = "params";
    private static final String LISTENER = "listener";
    private static final String DIRECTORY = "directory";
    private static final String CANCELABLE = "cancelable";
    private static final String EXTENSION_FILTER = "extensionFilter";

    private ArrayAdapter<String> adapter;
    private List<FileInformation> fileInformationList = new ArrayList<>();

    public static class Builder {
        final FragmentActivity activity;

        public <A extends FragmentActivity> Builder(@NonNull final A activity) {
            this.activity = activity;
        }

        private String rootDirectory = File.separator;
        private String initialDirectory = File.separator;
        private String previous = "..";
        private String cancelLabel = "cancel";
        private Bundle params;
        private String tag = "dialog";
        private boolean cancelable = true;
        private OnFileSelectedListener listener;
        private String extensionFilter;

        public Builder rootDirectory(@NonNull final String rootDirectory) {
            this.rootDirectory = rootDirectory;
            return this;
        }

        public Builder initialDirectory(@NonNull final String initialDirectory) {
            this.initialDirectory = initialDirectory;
            return this;
        }

        public Builder previous(@NonNull final String previous) {
            this.previous = previous;
            return this;
        }

        public Builder cancelLabel(@NonNull final String cancelLabel) {
            this.cancelLabel = cancelLabel;
            return this;
        }

        public Builder params(@NonNull final Bundle params) {
            this.params = params;
            return this;
        }

        public Builder tag(@NonNull final String tag) {
            this.tag = tag;
            return this;
        }

        public Builder canselable(final boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder listener(@NonNull final OnFileSelectedListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder extensionFilter(@NonNull final String extensionFilter) {
            this.extensionFilter = extensionFilter;
            return this;
        }

        public void show() {
            final Bundle args = new Bundle();
            args.putString(ROOT_DIRECTORY, rootDirectory);
            args.putString(INITIAL_DIRECTORY, initialDirectory);
            args.putString(PREVIOUS, previous);
            args.putString(CANCEL_LABEL, cancelLabel);
            args.putBoolean(CANCELABLE, cancelable);
            args.putSerializable(LISTENER, listener);
            args.putString(EXTENSION_FILTER, extensionFilter);
            if (params != null) {
                args.putBundle(PARAMS, params);
            }
            final FilePickerDialogFragment f = new FilePickerDialogFragment();
            f.setArguments(args);
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.add(f, tag);
            ft.commitAllowingStateLoss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null || getActivity() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        Activity activity = this.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ListView listView = new ListView(activity);
        this.adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1);
        listView.setAdapter(this.adapter);

        this.initializeArguments();

        this.updateView();

        listView.setOnItemClickListener(this);
        builder.setView(listView);

        Bundle bundle = this.getArguments();
        builder.setTitle(bundle.getString(DIRECTORY) + File.separator);
        builder.setNegativeButton(bundle.getString(CANCEL_LABEL), new CancelListener());
        builder.setCancelable(bundle.getBoolean(CANCELABLE));

        return builder.create();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Dialog dialog = this.getDialog();
        Bundle bundle = this.getArguments();
        if (dialog == null || bundle == null) {
            return;
        }
        String directory = bundle.getString(DIRECTORY);

        if (position == 0) {
            String rootDirectory = bundle.getString(ROOT_DIRECTORY);
            LogUtil.d("position 0 directory:" + directory +
                    " rootDirectory:" + rootDirectory);
            if (directory == null || rootDirectory == null) {
                return;
            }
            if (directory.length() > rootDirectory.length()) {
                directory = directory.substring(0,
                        directory.lastIndexOf(File.separator));
                bundle.putString(DIRECTORY, directory);

                dialog.setTitle(directory + File.separator);
                this.updateView();
            }
        } else {
            LogUtil.d("position other directory:" + directory);
            directory = directory + File.separator +
                    this.fileInformationList.get(position - 1).getFile().getName();
            File file = new File(directory);
            if (file.isDirectory()) {
                bundle.putString(DIRECTORY, directory);
                dialog.setTitle(directory + File.separator);
                this.updateView();
            } else {
                OnFileSelectedListener listener =
                        (OnFileSelectedListener) bundle.getSerializable(LISTENER);
                if (listener != null) {
                    listener.onFileSelected(directory);
                }

                this.dismiss();
            }
        }
    }

    private void initializeArguments() {
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            return;
        }
        if (bundle.getString(ROOT_DIRECTORY) == null) {
            bundle.putString(ROOT_DIRECTORY, File.separator);
        }
        if (bundle.getString(INITIAL_DIRECTORY) == null) {
            bundle.putString(INITIAL_DIRECTORY, bundle.getString(ROOT_DIRECTORY));
        }
        if (bundle.getString(DIRECTORY) == null) {
            bundle.putString(DIRECTORY, bundle.getString(INITIAL_DIRECTORY));
        }
        if (bundle.getString(PREVIOUS) == null) {
            bundle.putString(PREVIOUS, "..");
        }
    }

    private void updateView() {
        if (this.getArguments() == null) {
            return;
        }
        this.adapter.clear();

        Bundle bundle = this.getArguments();
        this.adapter.add(bundle.getString(PREVIOUS));

        String directory = bundle.getString(DIRECTORY);
        LogUtil.d("directory:" + directory);
        if (directory == null || directory.equals("")) {
            directory = File.separator;
        }

        this.fileInformationList.clear();
        File[] files = new File(directory).listFiles();

        if (files != null) {
            LogUtil.d("files length:" + files.length);
            for (File file : files) {
                String extensionFilter = bundle.getString(EXTENSION_FILTER);
                LogUtil.d("is not directory name:" + file.getName() +
                        " extensionFilter:" + extensionFilter);
                if (StringUtil.isNullOrEmpty(extensionFilter)) {
                    this.fileInformationList.add(new FileInformation(file));
                } else {
                    String extension = StringUtil.getFileNameExtension(file.getName());
                    if (extensionFilter.contains(extension)) {
                        this.fileInformationList.add(new FileInformation(file));
                    }
                }
            }

            Collections.sort(this.fileInformationList);

            for (FileInformation fileInformation : fileInformationList) {
                File file = fileInformation.getFile();
                if (file.isDirectory()) {
                    LogUtil.d("is directory name:" + file.getName());
                    this.adapter.add(file.getName() + File.separator);
                } else {
                    this.adapter.add(file.getName());
                }
            }
        }
    }


    private class CancelListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (FilePickerDialogFragment.this.getArguments() == null) {
                return;
            }
            OnFileSelectedListener listener = (OnFileSelectedListener) FilePickerDialogFragment
                    .this.getArguments().getSerializable(LISTENER);
            if (listener != null) {
                listener.onFileSelectCanceled();
            }
        }
    }

}
