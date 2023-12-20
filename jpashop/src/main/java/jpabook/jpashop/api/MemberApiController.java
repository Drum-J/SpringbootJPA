package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/v1")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/v2")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();

        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }

    @Data @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data @AllArgsConstructor
    static class MemberDto {
        private String name;
    }



    @PostMapping("/v1")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/v2")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/v2/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class CreateMemberRequest { // 요청 DTO
        @NotEmpty
        private String name;
    }
    @Data
    static class CreateMemberResponse { // 응답 DTO
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest { // 업데이트 요청 DTO
        private String name;
    }
    @Data @AllArgsConstructor
    static class UpdateMemberResponse { // 업데이트 응답 DTO
        private Long id;
        private String name;
    }
}
