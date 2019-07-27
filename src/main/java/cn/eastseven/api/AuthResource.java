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
@Api(value = "auth", tags = "身份验证")
public interface AuthResource {

    /**
     * 生成Token
     *
     * @param authenticationRequest 请求
     * @return 结果
     * @throws AuthenticationException 异常
     */
    @ApiOperation("生成Token")
    ResponseEntity<?> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException;

    /**
     * 刷新Token
     *
     * @param request 请求
     * @return 结果
     */
    @ApiOperation("刷新Token")
    ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request);

}
