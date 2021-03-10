package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author WCNGS@QQ.COM
 * @ClassName Req$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 17:23$
 * @Version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Req implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String desc;

    private byte[] attachment;


}
