package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "category_item", // 중간 연결테이블. 실무에서는 다대다를 사용하지 말자!
            joinColumns = @JoinColumn(name = "category)id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    //자기 자신과 매핑해서 계층구조 만들기. (댓글, 대댓글에 활용할 수 있다)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<Category> child = new ArrayList<>();


    // == 연관관계 편의 메서드 == //
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
