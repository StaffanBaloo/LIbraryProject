package member;


import java.util.ArrayList;
import java.util.Optional;

public class MemberService {

    MemberRepository memberRepository = new MemberRepository();

    public ArrayList<Member> getAllMembers(){
            return memberRepository.getAllMembers();
    }

    public ArrayList<Member> getAllMembersByStatus(String status){
        return memberRepository.getAllMembersByStatus(status);
    }

    public boolean exists(int memberId) {
        return memberRepository.exists(memberId);
    }

    public Optional<Member> getById(int memberId){
        Optional<Member> maybeMember = memberRepository.getById(memberId);
        if(maybeMember.isPresent()) return maybeMember;
        else return Optional.empty();
    }

    public void addMember(Member member) {
        memberRepository.addMember(member);
    }

    public void save(Member member){
        memberRepository.save(member);
    }

    public Optional<Member> getByEmail(String email){
        Optional<Member> maybeMember = memberRepository.getByEmail(email);
        if(maybeMember.isPresent()) return maybeMember;
        else return Optional.empty();
    }

}
