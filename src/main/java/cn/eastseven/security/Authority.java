package cn.eastseven.security;

import cn.eastseven.security.model.UserEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Deprecated
@Data
@Document(collection = "t_authority")
public class Authority {

    @Id
    private String id;

    @NotNull
    private AuthorityName name;

    @DBRef(lazy = true)
    private List<UserEntity> users;

}