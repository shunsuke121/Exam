package bean;

import java.io.Serializable;

public class User implements Serializable {
    // 認証済みフラグ（boolean true:認証成功）
    private boolean isAuthenticated;

    // ゲッター・セッター
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
}