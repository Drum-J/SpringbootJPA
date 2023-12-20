package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("승호");
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book", 10000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("승호링");
            em.persist(member);

            Book book1 = createBook("Spring1 Book", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("Spring2 Book", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Member createMember(String name) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address("대구","구암서로","41"));
            return member;
        }

        private static Book createBook(String bookName, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(bookName);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

    }
}

