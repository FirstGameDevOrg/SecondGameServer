package com.FirstGame.server.common.BO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class RemoteAddress implements Serializable {
    private String host;
    private int port;
}
