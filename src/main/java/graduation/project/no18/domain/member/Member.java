package graduation.project.no18.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graduation.project.no18.domain.Base.BaseEntity;
import graduation.project.no18.enums.ProviderType;
import graduation.project.no18.enums.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id @Column(name = "member_id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private String email;
    private String password;
    private String profileImg;
    private String introduction;
    private ProviderType providerType;
    private RoleType roleType;

    public Member(String email, String password, String profileImg, String introduction,
                  ProviderType providerType, RoleType roleType){
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.introduction = introduction;
        setProviderType(providerType);
        setRoleType(roleType);
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void changeIntroduction(String introduction){
        this.introduction = introduction;
    }

    private void setProviderType(ProviderType providerType){
        this.providerType = providerType;
    }

    private void setRoleType(RoleType roleType){
        this.roleType = roleType;
    }

    public boolean checkPassword(String password){
       return this.password == password ? true : false;
    }

}
