package com.anji.plus.gaea.bean;



import com.anji.plus.gaea.code.ResponseCode;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 响应体封装
 * @author lr
 * @since 2021-01-12
 */
public final class ResponseBean implements Serializable {

    public ResponseBean() {

    }

    /**
     * 构建线程池
     */
    private transient ThreadPoolExecutor executor = new ThreadPoolExecutor(2, Runtime.getRuntime().availableProcessors(),
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1),
            r -> new Thread(r,"ResponseBean.then.executor"));
    /**
     * 响应编码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应参数
     */
    private Object[] args;

    /**
     * 响应数据
     */
    private Object data;

    private ResponseBean(Builder builder) {
        this.code = builder.code;
        this.args = builder.args;
        this.message = builder.message;
        this.data = builder.data;
    }

    /**
     * 后续异步处理
     * 请求参数param
     *
     * @param consumer
     * @return
     */
    public ResponseBean thenAsync(Consumer<Object> consumer, Object param) {
        //异步执行
        executor.execute(() -> {
            consumer.accept(param);
        });
        return this;
    }

    /**
     * 后续同步处理
     * 请求参数param
     *
     * @param consumer
     * @return
     */
    public ResponseBean then(Consumer<Object> consumer, Object param) {
        //同步执行
        consumer.accept(param);
        return this;
    }

    /**
     * 用于创建ResponseBean实例
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code = ResponseCode.SUCCESS_CODE;
        private Object data;
        private String message;
        private Object[] args;

        private Builder() {

        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder args(Object[] args) {
            this.args = args;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ResponseBean build() {
            return new ResponseBean(this);
        }
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

	public String getMessage() {
		return message;
	}

    public void setCode(String code) {
        this.code = code;
    }
}
