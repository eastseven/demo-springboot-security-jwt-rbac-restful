package cn.eastseven.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author eastseven
 */
@Data
public class UsernamePasswordRequest implements Serializable {

    private String username;

    private String password;
}
