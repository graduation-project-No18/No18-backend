package graduation.project.no18.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graduation.project.no18.domain.Base.BaseEntity;
import graduation.project.no18.domain.recording.Recording;
import graduation.project.no18.global.oauth.principal.MemberPrincipal;
import graduation.project.no18.global.oauth.type.ProviderType;
import graduation.project.no18.global.oauth.type.RoleType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
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
    private String octave;

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

    private String getNickname(){
        return this.nickname;
    }

    private String getIntroduction(){
        return this.introduction;
    }

    private String getOctave(){
        return this.octave;
    }

    public MemberPrincipal toMemberPrincipal(){
        return new MemberPrincipal(
                this.accountId,
                this.email,
                this.password,
                this.providerType,
                this.roleType,
                Collections.singletonList(
                        new SimpleGrantedAuthority(RoleType.MEMBER.getCode()))
        );
    }

    public MemberDto toDto(){
        return new MemberDto(
                this.nickname,
                this.profileImg,
                this.introduction,
                this.octave
        );
    }

}
