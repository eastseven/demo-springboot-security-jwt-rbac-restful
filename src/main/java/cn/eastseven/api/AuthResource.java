package cn.eastseven.api;

import cn.eastseven.security.AuthenticationException;
import cn.eastseven.security.JwtAuthenticationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author d7
 */
@Api(value = "auth", tags = "身份验证", description = "接口")
public interface AuthResource {

    @ApiOperation("生成Token")
    ResponseEntity<?> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException;

    @ApiOperation("刷新Token")
    ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request);

}
