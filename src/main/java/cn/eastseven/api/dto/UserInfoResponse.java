package cn.eastseven.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author eastseven
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse implements Serializable {

    private String avatar;

    private String introduction;

    private String name;

    private Set<String> roles;
}
