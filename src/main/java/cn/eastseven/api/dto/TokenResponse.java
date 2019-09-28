package cn.eastseven.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author eastseven
 */
@Data
@Builder
public class TokenResponse implements Serializable {

    private String token;
}
