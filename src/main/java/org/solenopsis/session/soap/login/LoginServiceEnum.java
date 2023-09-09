package org.solenopsis.session.soap.login;

import org.solenopsis.session.soap.api.ApiService;
import org.solenopsis.session.soap.api.ApiServiceEnum;

/**
 *
 * @author sfloess
 */
public enum LoginServiceEnum {
    ENTERPRISE_LOGIN(ApiServiceEnum.ENTERPRISE_SERVICE),
    PARTNER_LOGIN(ApiServiceEnum.PARTNER_SERVICE),
    TOOLING_LOGIN(ApiServiceEnum.TOOLING_SERVICE);

    private final ApiServiceEnum apiServiceEnum;

    private LoginServiceEnum(final ApiServiceEnum apiServiceEnum) {
        this.apiServiceEnum = apiServiceEnum;
    }

    public ApiService getApiService() {
        return apiServiceEnum.getService();
    }
}
