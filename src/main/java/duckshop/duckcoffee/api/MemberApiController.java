package duckshop.duckcoffee.api;

import duckshop.duckcoffee.controller.MemberForm;
import duckshop.duckcoffee.domain.Address;
import duckshop.duckcoffee.domain.Member;
import duckshop.duckcoffee.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
//import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public MemberForm createForm() {
        MemberForm memberForm = new MemberForm();
        return memberForm;
    }

    @PostMapping("/members/new")
    public Long create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            throw new IllegalStateException();
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        return memberService.join(member);
    }

    @GetMapping("/members")
    public Result list() {
        List<Member> findMembers = memberService.findMembers();
        return new Result(findMembers);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
