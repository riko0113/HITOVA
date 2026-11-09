package bean;

import java.io.Serializable;

public class NotificationReceiver implements Serializable {

    // アカウントID
    private Long accountId;

    // 画面に表示する名前
    private String name;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
