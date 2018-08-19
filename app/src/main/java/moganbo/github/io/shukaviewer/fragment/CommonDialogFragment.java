package moganbo.github.io.shukaviewer.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import moganbo.github.io.shukaviewer.application.AppController;

public class CommonDialogFragment extends DialogFragment {
    private final static String TITLE = "title";
    private final static String MESSAGE = "message";
    private final static String POSITIVE_LABEL = "positive_label";
    private final static String NEGATIVE_LABEL = "negative_label";
    private final static String CANCELABLE = "cancelable";
    private final static String PARAMS = "params";
    private final static String DIALOG_TYPE = "dialog_type";
    private final static String POSITIVE_CLOSEABLE = "positive_closeable";
    private final static String NEGATIVE_CLOSEABLE = "negative_closeable";

    //positiveButtonのリスナー
    private static DialogInterface.OnClickListener positiveListener;

    //negativeButtonのリスナー
    private static DialogInterface.OnClickListener negativeListener;

    //列挙型クラス
    public static class CommonDialog {
        //共用ダイアログの種類の列挙型
        public enum DialogType {
            NONE(-1),
            SINGLE_BUTTON(0),
            DOUBLE_BUTTONS(1);

            private final int id;

            DialogType(final int id) {
                this.id = id;
            }

            /**
             * 指定されたenumのidを返す
             *
             * @return enumのid
             */
            public int getId() {
                return this.id;
            }
        }

        /**
         * 指定されたidのenumを返す
         *
         * @param id 指定されているid
         * @return enum
         */
        public static DialogType getDialogType(final int id) {
            DialogType[] dialogTypes = DialogType.values();
            for (DialogType dialogType : dialogTypes) {
                if (dialogType.getId() == id) {
                    return dialogType;
                }
            }
            return DialogType.NONE;
        }

        //コンストラクタのタイプの列挙型
        public enum ConstructorType {
            ACTIVITY(0),
            FRAGMENT(1);

            private final int id;

            /**
             * 指定されたenumのidを返す
             *
             * @param id enumのid
             */
            ConstructorType(final int id) {
                this.id = id;
            }

            public int getId() {
                return this.id;
            }
        }

        /**
         * 指定されたidのenumを返す
         *
         * @param id 指定されているid
         * @return enum
         */
        public static ConstructorType getConstructorType(final int id) {
            ConstructorType[] constructorTypes = ConstructorType.values();
            for (ConstructorType constructorType : constructorTypes) {
                if (constructorType.getId() == id) {
                    return constructorType;
                }
            }
            return null;
        }
    }

    /**
     * dialogを生産
     */
    public static class Builder {
        //親Activity
        final FragmentActivity activity;

        /**
         * コンストラクタ Activityから生産する場合
         *
         * @param activity 親フラグメント
         */
        public <A extends FragmentActivity> Builder(@NonNull final A activity) {
            this.activity = activity;
        }

        /**
         * タイトル
         */
        private String title;

        /**
         * メッセージ
         */
        private String message;

        /**
         * .肯定ボタンのラベル
         */
        private String positiveLabel;

        /**
         * 否定ボタンのラベル
         */
        private String negativeLabel;

        /**
         * リスナーに受け渡す任意のパラメータ
         */
        private Bundle params;

        /**
         * タグ
         */
        private String tag = "default";

        /**
         * dialogをキャンセル可能かどうか
         */
        private boolean cancelable = true;

        /**
         * ダイアログのタイプ 未設定の場合は「SINGLE_BUTTON」
         */
        private int type = 0;

        /**
         * ダイアログがクリックイベントで閉じることができるか
         */
        private boolean positiveCloseable = true;
        private boolean negativeCloseable = true;

        /**
         * タイトルを設定する
         *
         * @param title 文字列
         * @return ビルダー
         */
        public Builder title(@NonNull final String title) {
            this.title = title;
            return this;
        }

        /**
         * タイトルを設定する(リソース参照)
         *
         * @param title リソースid
         * @return Builder ビルダー
         */
        public Builder title(@StringRes final int title) {
            return title(AppController.getAppContext().getString(title));
        }

        /**
         * メッセージを設定する
         *
         * @param message 文字列
         * @return ビルダー
         */
        public Builder message(@NonNull final String message) {
            this.message = message;
            return this;
        }

        /**
         * メッセージを設定する(リソース参照)
         *
         * @param message リソースid
         * @return Builder ビルダー
         */
        public Builder message(@StringRes final int message) {
            return message(AppController.getAppContext().getString(message));
        }

        /**
         * 肯定ボタンを設定する
         *
         * @param positiveLabel 文字列
         * @return ビルダー
         */
        public Builder positive(@NonNull final String positiveLabel, DialogInterface.OnClickListener listener) {
            this.positiveLabel = positiveLabel;
            positiveListener = listener;
            return this;
        }

        /**
         * 肯定ボタンを設定する
         *
         * @param positiveLabel リソースid
         * @return ビルダー
         */
        public Builder positive(@StringRes final int positiveLabel, DialogInterface.OnClickListener listener) {
            return positive(AppController.getAppContext().getString(positiveLabel), listener);
        }

        /**
         * 否定ボタンを設定する
         *
         * @param negativeLabel 文字列
         * @return ビルダー
         */
        public Builder negative(@NonNull final String negativeLabel, DialogInterface.OnClickListener listener) {
            this.negativeLabel = negativeLabel;
            negativeListener = listener;
            return this;
        }

        /**
         * 否定ボタンを設定する
         *
         * @param negativeLabel リソースid
         * @return ビルダー
         */
        public Builder negative(@StringRes final int negativeLabel, DialogInterface.OnClickListener listener) {
            return negative(AppController.getAppContext().getString(negativeLabel), listener);
        }

        /**
         * dialogのタグを設定する
         *
         * @param tag 文字列
         * @return ビルダー
         */
        public Builder tag(final String tag) {
            if (tag != null) {
                this.tag = tag;
            }
            return this;
        }

        /**
         * リスナに渡すパラメータを設定
         *
         * @param params リスナに受け渡すパラメータ
         * @return ビルダー
         */
        public Builder params(final Bundle params) {
            this.params = new Bundle(params);
            return this;
        }

        /**
         * ダイアログのタイプをセット
         *
         * @param type ダイアログのタイプ
         * @return ビルダー
         */
        public Builder type(final CommonDialog.DialogType type) {
            switch (type) {
                //ボタンが一つ
                case SINGLE_BUTTON:
                    this.type = 0;
                    break;
                //ボタンが二つ
                case DOUBLE_BUTTONS:
                    this.type = 1;
                    break;
            }
            return this;
        }

        /**
         * ダイアログの各ボタンを押したときに閉じるようにするかの設定
         *
         * @param positive ポジティブボタン 真で閉じる
         * @param negative ネガティブボタン 真で閉じる
         * @return ビルダー
         */
        public Builder closeable(final boolean positive, final boolean negative) {
            positiveCloseable = positive;
            negativeCloseable = negative;
            return this;
        }

        /**
         * dialogを設定した情報をもとに表示する
         */
        public void show() {
            final Bundle args = new Bundle();
            //バンドルに表示内容やダイアログの設定をセットする
            args.putString(TITLE, title);
            args.putString(MESSAGE, message);
            args.putString(POSITIVE_LABEL, positiveLabel);
            args.putString(NEGATIVE_LABEL, negativeLabel);
            args.putBoolean(CANCELABLE, cancelable);
            args.putInt(DIALOG_TYPE, type);
            args.putBoolean(POSITIVE_CLOSEABLE, positiveCloseable);
            args.putBoolean(NEGATIVE_CLOSEABLE, negativeCloseable);
            if (params != null) {
                args.putBundle(PARAMS, params);
            }

            final CommonDialogFragment f = new CommonDialogFragment();
            f.setArguments(args);
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.add(f, tag);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() == null || getActivity() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        final String title = getArguments().getString(TITLE);
        final String message = getArguments().getString(MESSAGE);
        final String positiveLabel = getArguments().getString(POSITIVE_LABEL);
        final String negativeLabel = getArguments().getString(NEGATIVE_LABEL);
        //仕様としてキャンセルを無効化する
        setCancelable(false);
        final int dialogType = getArguments().getInt(DIALOG_TYPE);
        final boolean positiveCloseable = getArguments().getBoolean(POSITIVE_CLOSEABLE);
        final boolean negativeCloseable = getArguments().getBoolean(NEGATIVE_CLOSEABLE);

        //ビルダーを作成しセットしていく
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //各モードによって処理を分岐
        switch (CommonDialog.getDialogType(dialogType)) {
            //ボタンが一つ
            case SINGLE_BUTTON: {
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (positiveCloseable) {
                                    dismiss();
                                }
                                positiveListener.onClick(dialog, which);
                            }
                        });
                break;
            }
            //ボタンが二つ(横並び)
            case DOUBLE_BUTTONS: {
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (positiveCloseable) {
                                    dismiss();
                                }
                                if (positiveListener != null) {
                                    positiveListener.onClick(dialog, which);
                                }
                            }
                        })
                        .setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (negativeCloseable) {
                                    dismiss();
                                }
                                if (negativeListener != null) {
                                    negativeListener.onClick(dialog, which);
                                }
                            }
                        });
                break;
            }
        }
        return builder.create();
    }
}
