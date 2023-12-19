package jpabook.jpashop.order.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.item.repository.ItemRepository;
import jpabook.jpashop.member.repository.MemberRepository;
import jpabook.jpashop.order.repository.OrderRepository;
import jpabook.jpashop.order.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        repository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = repository.findOne(orderId);

        //주문 취소(비즈니스 로직 호출)
        order.cancel();

        //주문 취소 후 repository의 호출이 없다. 이 부분이 JPA의 엄청난 강점이다.
        //엔티티 안에 데이터를 바꾸면 JPA가 알아서 Dirty Check 이후 DB에 업데이트 쿼리를 알아서 날려준다.


    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return repository.findAllByCriteria(orderSearch);
    }
}
