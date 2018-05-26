package davidv7.avastgui;

import android.graphics.drawable.Drawable;

public class AppList {
    private String appName;
    private Drawable appIcon;

    public AppList(){

    }
    public AppList(String appName, Drawable appIcon){
        this.appName = appName;
        this.appIcon = appIcon;

    }
    public String getAppName() {

        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }


}
