package com.anji.mirror.gateway.model;

import com.anji.mirror.common.model.GatewayResponse;

/**
 * 保存中间结果
 */
public class DeliverBox {

    private Boolean success;

    private GatewayResponse gatewayResponse;

    public boolean isFail() {
        return success == null || success.booleanValue()==false;
    }
    public boolean isSuccess() {
        return success != null && success.booleanValue();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public GatewayResponse getGatewayResponse() {
        return gatewayResponse;
    }

    public void setGatewayResponse(GatewayResponse gatewayResponse) {
        this.gatewayResponse = gatewayResponse;
    }

    public static DeliverBox fail(GatewayResponse gatewayResponse){
        DeliverBox deliverBox = new DeliverBox();
        deliverBox.setSuccess(false);
        deliverBox.setGatewayResponse(gatewayResponse);
        return deliverBox;
    }


    public static DeliverBox success(){
        DeliverBox deliverBox = new DeliverBox();
        deliverBox.setSuccess(true);
        return deliverBox;
    }
}
