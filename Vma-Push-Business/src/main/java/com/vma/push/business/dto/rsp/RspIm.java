package com.vma.push.business.dto.rsp;

import lombok.Data;

@Data
public class RspIm {

    private String privateKey;

    private String publicKey;

    private String skdAppId;

    private String managerId;
}
