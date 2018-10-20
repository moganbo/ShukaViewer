package moganbo.github.io.shukaviewer.utils;

import java.io.Serializable;

public interface OnFileSelectedListener extends Serializable {

    public abstract void onFileSelected(String path);
    public abstract void onFileSelectCanceled();
}
