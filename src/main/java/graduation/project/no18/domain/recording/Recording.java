package graduation.project.no18.domain.recording;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graduation.project.no18.domain.member.Member;
import jakarta.persistence.*;


@Entity
public class Recording {

    @Id
    @Column(name = "recording_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;


}
