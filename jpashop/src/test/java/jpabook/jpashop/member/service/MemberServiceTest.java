package jpabook.jpashop.member.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository repository;

    @Test
    void joinTest() throws Exception {
        //given
        Member member = new Member();
        member.setName("SeungHo");

        //when
        Long join = memberService.join(member);
        Member findMember = repository.findOne(join);

        //then
        assertThat(member).isEqualTo(findMember);

    }

    @Test
    void duplicate() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("SeungHo");

        Member member2 = new Member();
        member2.setName("SeungHo");

        //when
        memberService.join(member1);

        //then
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalStateException.class);

    }
}