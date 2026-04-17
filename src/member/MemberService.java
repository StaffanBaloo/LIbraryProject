package member;


import java.util.ArrayList;

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

    public Member getById(int memberId){
        return memberRepository.getById(memberId);

    }

    public void addMember(Member member) {
        memberRepository.addMember(member);
    }

    public void save(Member member){
        memberRepository.save(member);
    }

    public Member getByEmail(String email){
        return memberRepository.getByEmail(email);
    }

}
