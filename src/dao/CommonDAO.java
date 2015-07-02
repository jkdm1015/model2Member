package dao;

import java.util.List;

public interface CommonDAO {
	
	public int insert(Object obj);	// 추가 CREATE 
	public int count();		// 전체 요소의 개수
	public Object getElementById(String id);		// ID 로 중복값 없이 추출
	public List<Object> getElementsByName(String name);	// NAME 으로 중복값 허용하며 추출
	public List<Object> list();		// 전체 목록 추출
	public int update(Object obj);	// 수정
	public int delete(String id);	// 삭제
	
}
