package graduation.project.no18.domain.recording;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graduation.project.no18.domain.member.Member;
import graduation.project.no18.domain.recommendation.Recommendation;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
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

    @OneToMany(mappedBy = "recording")
    private List<Recommendation> recommendationList = new ArrayList<>();

    //private String octave;

    //private String s3Link;
}
