package com.anji.plus.gaea;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * 盖亚配置信息
 * @author lr
 * @since 2021-01-11
 */
@ConfigurationProperties(prefix = "spring.gaea")
@RefreshScope
public class GaeaProperties {

    /**
     * 加密私钥，用于加密信息
     */
    private String secret = "anji-plus";

    /**
     * 激活配置
     */
    private Enabled enabled;

    /**
     * 订阅了哪些组件
     */
    private String subscribes;

    /**
     * 白名单，跳过
     */
    private List<String> whileList;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(String subscribes) {
        this.subscribes = subscribes;
    }

    public Enabled getEnabled() {
        return enabled;
    }

    public void setEnabled(Enabled enabled) {
        this.enabled = enabled;
    }

    public List<String> getWhileList() {
        return whileList;
    }

    public void setWhileList(List<String> whileList) {
        this.whileList = whileList;
    }

    /**
     * 激活模块
     */
    public class Enabled {

        /**
         * 导出
         */
        private boolean export;

        /**
         * 推送
         */
        private boolean push;


        /**
         * 系统设置
         */
        private boolean system;

        /**
         * 权限
         */
        private boolean security;

        /**
         * 帮助中心
         */
        private boolean helper;

        public boolean isExport() {
            return export;
        }

        public void setExport(boolean export) {
            this.export = export;
        }

        public boolean isPush() {
            return push;
        }

        public void setPush(boolean push) {
            this.push = push;
        }

        public boolean isSystem() {
            return system;
        }

        public void setSystem(boolean system) {
            this.system = system;
        }

        public boolean isSecurity() {
            return security;
        }

        public void setSecurity(boolean security) {
            this.security = security;
        }

        public boolean isHelper() {
            return helper;
        }

        public void setHelper(boolean helper) {
            this.helper = helper;
        }
    }
}
