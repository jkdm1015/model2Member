package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.BangBean;
import bean.ThemeBean;
import util.DBmanager;

public class ThemeDaoImpl implements CommonDAO{
	Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    ThemeBean theme = new ThemeBean();
    
    private static ThemeDaoImpl themeDAO = new ThemeDaoImpl();
    
    private ThemeDaoImpl() {
        // 단위 테스트가 끝나고 프로젝트가 완성되면 걷어 낼 부분
        conn = DBmanager.getConnection();
    }
 
    public static ThemeDaoImpl getInstance() {
        return themeDAO;
    }
    
    public Connection getConnection() throws Exception {
        Connection conn = null;
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
        conn = ds.getConnection();
        return conn;
    }

	@Override
	public int insert(Object obj) {
		int result = 0;
		try {
            conn.setAutoCommit(false);
            
            if (theme.getParentId() == 0) { 
                // 답글이 아닌 경우 그룹번호를 새롭게 구한다.
                stmt = conn.createStatement(); 
                rs = stmt.executeQuery(
                    "select max(GROUP_ID) from THEME_MESSAGE"); 
                int maxGroupId = 0; 
                if (rs.next()) {
                    maxGroupId = rs.getInt(1); 
                }
                maxGroupId++;
                
                theme.setGroupId(maxGroupId);
                theme.setOrderNo(0);
            } else {
                // 특정 글의 답글인 경우,
                // 같은 그룹 번호 내에서의 출력 순서를 구한다.  
                pstmt = conn.prepareStatement( 
                "select max(ORDER_NO) from THEME_MESSAGE "+ 
                "where PARENT_ID = ? or THEME_MESSAGE_ID = ?"); 
                pstmt.setInt(1, theme.getParentId());
                pstmt.setInt(2, theme.getParentId());
                rs = pstmt.executeQuery();
                int maxOrder = 0;
                if (rs.next()) {
                    maxOrder = rs.getInt(1);
                }
                maxOrder ++;
                theme.setOrderNo(maxOrder); 
            }
            
            // 특정 글의 답변 글인 경우 같은 그룹 내에서
            // 순서 번호를 변경한다.
            if (theme.getOrderNo() > 0) {
                pstmt = conn.prepareStatement(
                "update THEME_MESSAGE set ORDER_NO = ORDER_NO + 1 "+
                "where GROUP_ID = ? and ORDER_NO >= ?");
                pstmt.setInt(1, theme.getGroupId()); 
                pstmt.setInt(2, theme.getOrderNo()); 
                pstmt.executeUpdate();
            }
            // 새로운 글의 번호를 구한다.
            theme.setId(0);
            // 글을 삽입한다.
            pstmt = conn.prepareStatement( 
            "insert into THEME_MESSAGE values (?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, theme.getId());
            pstmt.setInt(2, theme.getGroupId());
            pstmt.setInt(3, theme.getOrderNo());
            pstmt.setInt(4, theme.getLevel()); 
            pstmt.setInt(5, theme.getParentId());
            //pstmt.setTimestamp(6, theme.getRegister());
            pstmt.setString(7, theme.getName());
            pstmt.setString(8, theme.getEmail());
            pstmt.setString(9, theme.getImage());
            pstmt.setString(10, theme.getPassword());
            pstmt.setString(11, theme.getTitle()); 
            pstmt.executeUpdate(); 
            
            pstmt = conn.prepareStatement( 
            "insert into THEME_CONTENT values (?,?)");
            pstmt.setInt(1, theme.getId());
            pstmt.setCharacterStream(2,null);
            pstmt.executeUpdate(); 
            
            conn.commit();
        } catch(Exception ex) {
  
            	ex.printStackTrace();
        } finally { 
            
        }
		return result;
	}

	@Override
	public int count() {
		int result = 0;
		try {
            StringBuffer query = new StringBuffer(200); 
            query.append("select count(*) from THEME_MESSAGE ");
            if (whereCond != null && whereCond.size() > 0) {
                query.append("where "); 
                for (int i = 0 ; i < whereCond.size() ; i++) {
                    query.append(whereCond.get(i)); 
                    if (i < whereCond.size() -1 ) { 
                        query.append(" or ");
                    }
                }
            }
            pstmt = conn.prepareStatement(query.toString());
            
            Iterator keyIter = valueMap.keySet().iterator();
            while(keyIter.hasNext()) {
                Integer key = (Integer)keyIter.next();
                Object obj = valueMap.get(key); 
                if (obj instanceof String) {
                    pstmt.setString(key.intValue(), (String)obj);
                } else if (obj instanceof Integer) {
                    pstmt.setInt(key.intValue(), ((Integer)obj).intValue());
                } else if (obj instanceof Timestamp) {
                    pstmt.setTimestamp(key.intValue(), (Timestamp)obj); 
                }
            }
            
            rs = pstmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch(Exception ex) {
        	ex.printStackTrace();
        } finally { 
            if (rs != null) try { rs.close(); } catch(Exception ex) {} 
            if (pstmt != null) try { pstmt.close(); } catch(Exception ex) {} 
            if (conn != null) try { conn.close(); } catch(Exception ex) {} 
        }
		return result;
	}

	@Override
	public Object getElementById(String id) {
		Object obj = null;
		
		return obj;
	}

	@Override
	public List<Object> getElementsByName(String name) {
		List<Object> list = new ArrayList<Object>();
		
		return list;
	}

	@Override
	public List<Object> list() {
		List<Object> list = new ArrayList<Object>();
		
		return list;
	}

	@Override
	public int update(Object obj) {
		int result = 0;
        PreparedStatement pstmtUpdateMessage = null;
        PreparedStatement pstmtUpdateContent = null;
        
        try {
            pstmt = conn.prepareStatement( 
                "update THEME_MESSAGE set NAME=?,EMAIL=?,IMAGE=?,TITLE=? "+ 
                "where THEME_MESSAGE_ID=?");
            pstmt = conn.prepareStatement( 
                "update THEME_CONTENT set CONTENT=? "+
                "where THEME_MESSAGE_ID=?"); 
            
            pstmt.setString(1, theme.getName());
            pstmt.setString(2, theme.getEmail());
            pstmt.setString(3, theme.getImage());
            pstmt.setString(4, theme.getTitle());
            pstmt.setInt(5, theme.getId());
            pstmt.executeUpdate(); 
            
            pstmt.setString(1,null);
            pstmt.setInt(2, theme.getId());
            pstmt.executeUpdate(); 
            
            conn.commit();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally { 
           
        }
		return result;
	}

	@Override
	public int delete(String id) {
		int result = 0;
		
		return result;
	}

}
