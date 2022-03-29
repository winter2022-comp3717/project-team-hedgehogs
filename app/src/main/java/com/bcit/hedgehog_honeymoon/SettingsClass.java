package com.bcit.hedgehog_honeymoon;

import java.io.Serializable;

public class SettingsClass implements Serializable {
    private boolean isMusicOn;
    private boolean isSfxOn;

    SettingsClass(boolean isMusicOnBool, boolean isSfxOnBool) {
        isMusicOn = isMusicOnBool;
        isSfxOn = isSfxOnBool;
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

    public boolean isSfxOn() {
        return isSfxOn;
    }
}
