package cn.eastseven.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
@Data
@ApiModel("账号密码")
public class JwtAuthenticationRequest implements Serializable {

    @NotBlank(message = "username can't empty!")
    @ApiModelProperty(value = "登录账号", position = 1, example = "admin")
    private String username;

    @NotBlank(message = "password can't empty!")
    @ApiModelProperty(value = "登录密码", position = 2, example = "123456")
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

}
