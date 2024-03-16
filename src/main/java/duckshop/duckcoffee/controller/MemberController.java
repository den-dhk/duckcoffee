//package duckshop.duckcoffee.controller;
//
//import duckshop.duckcoffee.domain.Address;
//import duckshop.duckcoffee.domain.Member;
//import duckshop.duckcoffee.service.MemberService;
//import jakarta.validation.Valid;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class MemberController {
//
//    private final MemberService memberService;
//
//    @GetMapping(value = "/members/new")
//    public String createForm(Model model) {
//        model.addAttribute("memberForm", new MemberForm());
//        return "members/createMemberForm";
//    }
//
//    @PostMapping("/members/new")
//    public String create(@Valid MemberForm form, BindingResult result) {
//
//        if (result.hasErrors()) {
//            return "members/createMemberForm";  //에러가 있더라도 form 데이터는 그대로 다시 가져감
//        }
//
//        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
//
//        Member member = new Member();
//        member.setName(form.getName());
//        member.setAddress(address);
//
//        memberService.join(member);
//        return "redirect:/";
//    }
//
//    @GetMapping("/members")
//    public String list(Model model) {
//        List<Member> members = memberService.findMembers();
//        model.addAttribute("members", members);
////        model.addAttribute("members", memberService.findMembers());
//        return "members/memberList";
//    }
//}
//
