package service;

import java.util.List;

import bean.MemberBean;

/*
@ Date : 2015.06
@ Author : ITB-1
@ Story : 회원관리 인터페이스
*/
public interface MemberService {
    
    public int join(MemberBean bean);	/*insert : 회원가입*/
    public int count();	/* count : 회원수*/
    public MemberBean memberDetail(String id);/* getElementById : 회원상세정보*/
    public List<Object> searchByKeyword(String keyword);/* getElementsByName : 검색어로 회원 검색*/
    public List<Object> memberList();		/* list : 회원전체 목록*/
    public int updateMember(MemberBean bean);	/* update : 회원정보 수정*/
    public int deleteMember(String id);		/* delete : 회원탈퇴*/
    public String login(String id, String password);
}