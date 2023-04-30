package graduation.project.no18.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graduation.project.no18.domain.Base.BaseEntity;
import graduation.project.no18.domain.recording.Recording;
import graduation.project.no18.global.oauth.type.ProviderType;
import graduation.project.no18.global.oauth.type.RoleType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
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
    @Column(name = "id")
    private String accountId;
    private String email;
    @Column(length = 20)
    private String password;
    @Column(length = 8)
    private String nickname;
    @Column(columnDefinition = "TEXT")
    private String profileImg;
    @Column(columnDefinition = "TEXT")
    private String introduction;
    private ProviderType providerType;
    private RoleType roleType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Recording> recordingList = new ArrayList<>();

    @Builder
    public Member(String accountId, String email, String password, String profileImg, String nickname,
                  String introduction, ProviderType providerType, RoleType roleType){
        this.accountId = accountId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.introduction = introduction;
        setProviderType(providerType);
        setRoleType(roleType);
    }
    private void setProviderType(ProviderType providerType){
        this.providerType = providerType;
    }
    private void setRoleType(RoleType roleType){
        this.roleType = roleType;
    }

    public String getAccountId(){return accountId;}
    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
    public ProviderType getProviderType(){
        return providerType;
    }
    public RoleType getRoleType(){
        return roleType;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void changeIntroduction(String introduction){
        this.introduction = introduction;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public boolean checkPassword(String password){
       return this.password.equals(password);
    }

    public boolean checkProviderType(ProviderType providerType) {
        return this.providerType.equals(providerType);
    }

}
