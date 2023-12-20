package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter //실무에서는 세터는 필요한 부분에만 열어두는것이 좋다.
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장 타입. Address의 @Embeddable과 둘 중 하나만 사용해도 되지만 둘다 표시해줬다.
    private Address address;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL) // 나는 Order 엔티티에 있는 member 필드에 의해서 매핑 된거야!
    private List<Order> orders = new ArrayList<>(); // 강의에서는 양방향으로 했지만 다대일 단방향을 더 추천한다고 한다.

    // 업데이트 메소드 사용
    public void updateName(String name) {
        this.name = name;
    }
}
