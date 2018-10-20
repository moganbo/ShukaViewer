package moganbo.github.io.shukaviewer.constants;

import com.os.operando.garum.annotations.DefaultBoolean;
import com.os.operando.garum.annotations.Pref;
import com.os.operando.garum.annotations.PrefKey;
import com.os.operando.garum.models.PrefModel;

@Pref(name = "app_flags")
public class AppFlags extends PrefModel {
    @PrefKey
    public boolean showedSignInDialog;
    @PrefKey
    public boolean showedMuseumDialog;
    @PrefKey
    public boolean showedBookStandDialog;

}
