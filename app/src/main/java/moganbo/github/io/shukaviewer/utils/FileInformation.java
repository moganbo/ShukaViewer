package moganbo.github.io.shukaviewer.utils;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.Locale;

public class FileInformation implements Comparable<FileInformation> {

    private File file;

    public FileInformation(File file){
        this.file = file;
    }

    @Override
    public int compareTo(@NonNull FileInformation o) {
        if (this.file.isDirectory() && !o.file.isDirectory()){
            return -1;
        }else if (!this.file.isDirectory() && o.file.isDirectory()){
            return 1;
        }else{
            return this.file.getName().toLowerCase(Locale.US).compareTo(o.file.getName().toLowerCase(Locale.US));
        }
    }

    public File getFile(){
        return this.file;
    }
}
