package graduation.project.no18.member;

import graduation.project.no18.enums.ProviderType;
import graduation.project.no18.enums.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.security.Provider;
import java.util.UUID;

@Entity
@Getter
public class Member {
    @Id @Column(name = "member_id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private String email;
    private String password;
    private String profileImg;
    private String introduction;
    private ProviderType providerType;
    private RoleType roleType;


}
