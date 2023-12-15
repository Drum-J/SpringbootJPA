package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository repository;
    @Test
    @Transactional
    @Rollback(false)
    void memberRepositoryTest() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("Seungho");

        //when
        repository.save(member);
        /*
            Long id = member.getId();
            System.out.println("id = " + id);
        */
        Member findMember = repository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 찾을 수 없습니다."));
        //then
        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(member.getUsername()).isEqualTo(findMember.getUsername());
        assertThat(member).isEqualTo(findMember);
    }
}